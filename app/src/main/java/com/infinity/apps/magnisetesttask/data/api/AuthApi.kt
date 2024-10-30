package com.infinity.apps.magnisetesttask.data.api

import androidx.annotation.Keep
import com.infinity.apps.magnisetesttask.domain.model.auth.AuthResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

@Keep
interface AuthApi {

    @FormUrlEncoded
    @POST("identity/realms/{realm}/protocol/openid-connect/token")
    suspend fun login(
        @Path("realm") realm: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("client_id") clientId: String = "app-cli",
        @Field("grant_type") grantType: String = "password"
    ): AuthResponse

}
