package com.infinity.apps.magnisetesttask.data.remote.source

import com.infinity.apps.magnisetesttask.data.model.param.SubscriptionMessage
import com.infinity.apps.magnisetesttask.domain.local.repository.ITokenCacheRepository
import com.infinity.apps.magnisetesttask.domain.model.instrument.response.MarketPrice
import com.infinity.apps.magnisetesttask.domain.remote.source.IRealTimeDataSource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSocketException
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
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Named


class RealTimeDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val tokenCacheRepository: ITokenCacheRepository,
    @Named("wssUri") private val wssUri: String
) : IRealTimeDataSource {

    private var session: WebSocketSession? = null

    override suspend fun connectToSocket() {
        val token = tokenCacheRepository.getAccessToken()
        require(!token.isNullOrEmpty()) { "Token must not be null or empty" }

        try {
            session = httpClient.webSocketSession {
                url("wss://$wssUri?token=$token")
            }
        } catch (e: WebSocketException) {
            throw (e)
        } catch (e: Exception) {
            throw (e)
        }
    }

    override suspend fun disconnectSocket() {
        session?.let {
            session?.close()
            session = null
        }
    }

    override suspend fun observePriceUpdates(): Flow<MarketPrice> {
        val currentSession = session ?: throw IllegalStateException("WebSocket disconnected.")
        return currentSession.incoming
            .consumeAsFlow()
            .filterIsInstance<Frame.Text>()
            .mapNotNull { frame ->
                parseMarketPrice(frame)
            }
    }

    override suspend fun sendMassage(subscribe : Boolean,instrumentId : String, provider : String) {
        val jsonMessage = Json.encodeToString(
            SubscriptionMessage.serializer(),
            SubscriptionMessage(
                type = "l1-subscription",
                id = "1",
                instrumentId = instrumentId,
                provider = provider,
                subscribe = subscribe,
                kinds = listOf("last")
            )
        )
        session?.outgoing?.send(Frame.Text(jsonMessage))
    }

    private fun parseMarketPrice(frame: Frame.Text): MarketPrice? {
        return try {
            Json.decodeFromString<MarketPrice>(frame.readText())
        } catch (e: Exception) {
            null
        }
    }
}