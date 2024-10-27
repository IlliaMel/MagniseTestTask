package com.infinity.apps.magnisetesttask.data.remote.repository

import com.infinity.apps.magnisetesttask.data.api.AuthApi
import com.infinity.apps.magnisetesttask.domain.model.auth.AuthResponse
import com.infinity.apps.magnisetesttask.domain.model.core.Response
import com.infinity.apps.magnisetesttask.domain.remote.repository.IAuthRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authApi : AuthApi) : IAuthRepository {

    private val realm = "fintatech"

    override suspend fun getAuthResponse(
        username: String,
        password: String
    ): Response<AuthResponse> {
        return try {
            val response = authApi.login(
                realm = realm,
                username = username,
                password = password
            )

            Response.Success(response)
        } catch (e: HttpException) {
            Response.Error(message = e.message())
        } catch (e: IOException) {
            Response.Error(message = e.message.toString())
        }
    }

}