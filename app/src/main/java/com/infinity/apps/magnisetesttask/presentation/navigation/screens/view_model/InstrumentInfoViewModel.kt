package com.infinity.apps.magnisetesttask.presentation.navigation.screens.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infinity.apps.magnisetesttask.data.model.response.HistoricalPriceModel
import com.infinity.apps.magnisetesttask.data.model.response.InstrumentDataModel
import com.infinity.apps.magnisetesttask.data.model.response.InstrumentRealTimeDataModel
import com.infinity.apps.magnisetesttask.domain.local.manager.ITimeManager
import com.infinity.apps.magnisetesttask.domain.local.repository.IInstrumentDataCacheRepository
import com.infinity.apps.magnisetesttask.domain.local.repository.ITokenCacheRepository
import com.infinity.apps.magnisetesttask.domain.model.core.Response
import com.infinity.apps.magnisetesttask.domain.model.instrument.param.HistoricalQueryParams
import com.infinity.apps.magnisetesttask.domain.model.instrument.response.InstrumentDataResponse
import com.infinity.apps.magnisetesttask.domain.remote.repository.IAuthRepository
import com.infinity.apps.magnisetesttask.domain.remote.source.IHistoricalDataSource
import com.infinity.apps.magnisetesttask.domain.remote.source.IInstrumentsDataSource
import com.infinity.apps.magnisetesttask.domain.remote.source.IRealTimeDataSource
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.view_model.state.FilterState
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.view_model.state.InstrumentDataState
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.view_model.state.InstrumentHistoryState
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.view_model.state.RealTimeDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.plugins.websocket.WebSocketException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InstrumentInfoViewModel @Inject constructor(
    private val authRepository : IAuthRepository,
    private val historicalDataSource : IHistoricalDataSource,
    private val instrumentsDataSource : IInstrumentsDataSource,
    private val realTimeDataSource : IRealTimeDataSource,
    private val tokenCacheRepository: ITokenCacheRepository,
    private val instrumentDataCacheRepository : IInstrumentDataCacheRepository,
    private val timeManager : ITimeManager
) : ViewModel () {

    private val _filterState: MutableStateFlow<FilterState> =
        MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState

    private val _instrumentHistoryState: MutableStateFlow<InstrumentHistoryState> =
        MutableStateFlow(InstrumentHistoryState())
    val instrumentHistoryState: StateFlow<InstrumentHistoryState> = _instrumentHistoryState

    private val _instrumentDataState: MutableStateFlow<InstrumentDataState> =
        MutableStateFlow(InstrumentDataState())
    val instrumentDataState: StateFlow<InstrumentDataState> = _instrumentDataState

    private val _instrumentRealTimeDataState: MutableStateFlow<RealTimeDataState> =
        MutableStateFlow(RealTimeDataState())
    val instrumentRealTimeDataState: StateFlow<RealTimeDataState> =
        _instrumentRealTimeDataState

    companion object {
        private const val CACHE_EXPIRATION_TIME: Long =
            24 * 60 * 60 * 1000L
        private const val RETRIES = 3
    }

    private var sendingJob: Job? = null
    private var historyFetchingJob: Job? = null


    init {
        viewModelScope.launch(Dispatchers.IO) {
            login()
            getInstrumentData()
            connectToSocket ()
        }
    }

    fun onChangeFilter(filter: String) {
        onSubscriptionInterruption()
        _filterState.update {
            it.copy(filter)
        }
        _instrumentDataState.update {
            it.copy(currentInstrumentModel = null)
        }
    }

    fun onInstrumentChanged(instrumentDataModel: InstrumentDataModel) {
        onSubscriptionInterruption()
        _instrumentDataState.update {
            it.copy(currentInstrumentModel = instrumentDataModel)
        }
        historyFetchingJob?.cancel()
        historyFetchingJob = viewModelScope.launch(Dispatchers.IO) {
            _filterState.value.filter?.let { filter ->
                getHistoryData(instrumentId = instrumentDataModel.id, provider = filter)
            }
        }
    }

    fun onSubscribe(isSubscribed: Boolean, instrumentId: String, provider: String) {
        _instrumentRealTimeDataState.update {
            it.copy(isSubscribe = isSubscribed, realTimeData = null)
        }
        sendingJob?.cancel()
        sendingJob = viewModelScope.launch(Dispatchers.IO) {
            realTimeDataSource.sendMassage(subscribe = isSubscribed, instrumentId = instrumentId, provider = provider)
        }
    }

    private fun onSubscriptionInterruption() {
        if (_instrumentRealTimeDataState.value.isSubscribe) {
            viewModelScope.launch(Dispatchers.IO) {
                _instrumentDataState.value.currentInstrumentModel?.id?.let { instrumentId ->
                    _filterState.value.filter?.let { provider ->
                        realTimeDataSource.sendMassage(
                            subscribe = false,
                            instrumentId = instrumentId,
                            provider = provider
                        )
                    }
                }
            }

            _instrumentRealTimeDataState.update {
                it.copy(isSubscribe = false, realTimeData = null)
            }
        }
    }


    private suspend fun login(
        attempt: Int = 0,
        username: String = "r_test@fintatech.com",
        password: String = "kisfiz-vUnvy9-sopnyv"
    ) {
        if (attempt >= RETRIES) {
            // Handle error to user
            return
        }

        val result = authRepository.getAuthResponse(
            username = username,
            password = password
        )

        when (result) {
            is Response.Success -> {
                result.data?.accessToken?.let { token ->
                    tokenCacheRepository.saveAccessToken(token)
                    return
                }
            }

            else -> {
                delay(1000L)
                login(attempt + 1)
            }
        }
    }


    private suspend fun getHistoryData(
        provider: String,
        instrumentId: String,
        attempt: Int = 0
    ) {
        if (attempt >= RETRIES) {
            // Handle error to user
            return
        }

        val result = historicalDataSource.fetchHistoricalPrices(
            historicalQueryParams = HistoricalQueryParams(
                provider = provider, instrumentId = instrumentId
            )
        )

        when (result) {
            is Response.Success -> {
                val historicalData = result.data ?: return

                _instrumentHistoryState.update {
                    it.copy(
                        data = HistoricalPriceModel.fromHistoricalPrices(historicalData.getSortedPricesByTimestamp())
                    )
                }

                return
            }

            is Response.UnauthorizedError -> {
                login() // Rout to login screen | Use refresh token
                delay(1000L)
                getHistoryData(provider, instrumentId, attempt + 1)
            }

            else -> {
                delay(1000L)
                getHistoryData(provider, instrumentId, attempt + 1)
            }
        }
    }


    private suspend fun getInstrumentData(size: Int = 1000, attempt: Int = 0) {
        val key = "instrument_data"

        if (attempt >= RETRIES) {
            // Handle error to user
            return
        }

        val lastUpdateTime = timeManager.getLastUpdateTime(key)
        val currentTime = System.currentTimeMillis()

        if ((currentTime - lastUpdateTime) < CACHE_EXPIRATION_TIME) {
            val cachedData = instrumentDataCacheRepository.getCachedInstrumentData()
            if (cachedData.isNotEmpty()) {
                _instrumentDataState.update {
                    it.copy(
                        data = InstrumentDataModel.fromInstrumentDataList(
                            InstrumentDataResponse(
                                cachedData
                            )
                        )
                    )
                }
                return
            }
        }

        val result = instrumentsDataSource.getAllInstruments(size = size)

        when (result) {
            is Response.Success -> {
                val instrumentData = result.data ?: return

                instrumentDataCacheRepository.setCachedInstrumentData(instrumentData.data)
                timeManager.updateLastUpdateTime(key, currentTime)

                _instrumentDataState.update {
                    it.copy(
                        data = InstrumentDataModel.fromInstrumentDataList(instrumentData)
                    )
                }

                return
            }

            is Response.UnauthorizedError -> {
                login() // Rout to login screen | Use refresh token
                delay(1000L)
                getInstrumentData(size, attempt + 1)
            }

            else -> {
                delay(1000L)
                getInstrumentData(size, attempt + 1)
            }
        }
    }

    private suspend fun connectToSocket(
        attempt: Int = 0
    ) {
        if (attempt >= RETRIES) {
            // Handle error to user
            return
        }

        try {
            realTimeDataSource.connectToSocket()

            realTimeDataSource.observePriceUpdates()
                .onStart {
                    Log.d("WebSocket Test", "onStart")
                }
                .mapNotNull { marketPrice ->
                    _instrumentDataState.value.data?.find { it.id == marketPrice.instrumentId }?.symbol?.let { instrumentSymbol ->
                        InstrumentRealTimeDataModel.fromMarketPrice(
                            price = marketPrice.last.price,
                            instrumentSymbol = instrumentSymbol,
                            timestamp = marketPrice.last.timestamp
                        )
                    }
                }.mapNotNull {
                    RealTimeDataState(realTimeData = it)
                }
                .onEach {
                    Log.d("WebSocket Each", "onEach with transformed data: $it")
                }
                .catch { t ->
                    Log.e("WebSocket Test", "Error: $t")
                }
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), RealTimeDataState())
                .collect { newState ->
                    _instrumentRealTimeDataState.update {
                        it.copy(realTimeData = newState.realTimeData)
                    }
                }
        } catch (e: WebSocketException) {
            if (e.message?.contains("401") == true)
                login() // Rout to login screen | Use refresh token
            delay(1000L)
            connectToSocket(attempt + 1)
        } catch (e: Exception) {
            delay(1000L)
            connectToSocket(attempt + 1)
        } finally {
            withContext(NonCancellable) {
                realTimeDataSource.disconnectSocket()
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch(Dispatchers.IO) {
            realTimeDataSource.disconnectSocket()
        }
    }

}