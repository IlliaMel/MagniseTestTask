package com.infinity.apps.magnisetesttask.data.model.param

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SubscriptionMessage(
    val type: String,
    val id: String,
    val instrumentId: String,
    val provider: String,
    val subscribe: Boolean,
    val kinds: List<String>
)