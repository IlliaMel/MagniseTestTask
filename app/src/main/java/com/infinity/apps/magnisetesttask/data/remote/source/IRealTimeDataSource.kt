package com.infinity.apps.magnisetesttask.data.remote.source

import android.app.GameState
import com.infinity.apps.magnisetesttask.data.api.InstrumentApi
import com.infinity.apps.magnisetesttask.domain.local.repository.ITokenCacheRepository
import com.infinity.apps.magnisetesttask.domain.model.core.Response
import com.infinity.apps.magnisetesttask.domain.model.instrument.response.InstrumentDataResponse
import com.infinity.apps.magnisetesttask.domain.model.instrument.response.MarketPrice
import com.infinity.apps.magnisetesttask.domain.remote.source.IInstrumentsDataSource
import com.infinity.apps.magnisetesttask.domain.remote.source.IRealTimeDataSource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class RealTimeDataSourceImpl @Inject constructor(
    private val httpClient : HttpClient,
    private val tokenCacheRepository: ITokenCacheRepository
) : IRealTimeDataSource {

    private val wssUri : String = "platform.fintacharts.com/api/streaming/ws/v1/realtime?"
    private var session: WebSocketSession? = null

    private suspend fun sendSubscriptionMessage(subscribe: Boolean, instrumentId: String) {
        val jsonMessage = buildString {
            append("{\n")
            append("  \"type\": \"l1-subscription\",\n")
            append("  \"id\": \"1\",\n")
            append("  \"instrumentId\": \"$instrumentId\",\n")
            append("  \"provider\": \"simulation\",\n")
            append("  \"subscribe\": $subscribe,\n")
            append("  \"kinds\": [\n")
            append("    \"last\"\n")
            append("  ]\n")
            append("}")
        }
        session?.outgoing?.send(Frame.Text(jsonMessage))
    }

    @Throws(IllegalStateException::class, IllegalArgumentException::class)
    override suspend fun connectToSocket(instrumentId: String) {
        val token = tokenCacheRepository.getAccessToken()
        require(!token.isNullOrEmpty()) { "Token must not be null or empty" }

        disconnectSocket()

        session = httpClient.webSocketSession {
            url("wss://${wssUri}token=$token")
        }

        sendSubscriptionMessage(true, instrumentId)
    }

    override suspend fun disconnectSocket() {
        //sendSubscriptionMessage(false)
        session?.close()
        session = null
    }

    @Throws(IllegalStateException::class)
    override suspend fun observePriceUpdates(): Flow<MarketPrice> {
        val currentSession = session ?: throw IllegalStateException("WebSocket disconnected.")

        return currentSession.incoming
            .consumeAsFlow()
            .filterIsInstance<Frame.Text>()
            .mapNotNull {
                try {
                    Json.decodeFromString<MarketPrice>(it.readText())
                } catch (e: Exception) {
                    null
                }
            }
            .onCompletion {
                if (session == null) {
                    println("Session disconnected, flow completed.")
                }
            }
    }
}