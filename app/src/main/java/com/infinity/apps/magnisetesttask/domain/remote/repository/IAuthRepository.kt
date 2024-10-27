package com.infinity.apps.magnisetesttask.domain.remote.repository

import com.infinity.apps.magnisetesttask.domain.model.auth.AuthResponse
import com.infinity.apps.magnisetesttask.domain.model.core.Response

interface IAuthRepository {

    suspend fun getAuthResponse (username: String, password: String): Response<AuthResponse>

}