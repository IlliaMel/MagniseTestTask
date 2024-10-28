package com.infinity.apps.magnisetesttask.data.model.param

import kotlinx.serialization.Serializable


@Serializable
data class SubscriptionMessage(
    val type: String,
    val id: String,
    val instrumentId: String,
    val provider: String,
    val subscribe: Boolean,
    val kinds: List<String>
)