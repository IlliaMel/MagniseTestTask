package com.infinity.apps.magnisetesttask.domain.model.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "refresh_token") val refreshToken: String?,
    @Json(name = "expires_in") val accessTokenExpiresIn: Int,
    @Json(name = "refresh_expires_in") val refreshTokenExpiresIn: Int
)