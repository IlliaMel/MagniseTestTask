package com.infinity.apps.magnisetesttask.data.remote.interceptor

import com.infinity.apps.magnisetesttask.domain.local.repository.ITokenCacheRepository
import okhttp3.Interceptor
import retrofit2.Invocation
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val tokenCacheRepository: ITokenCacheRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val invocation = request.tag(Invocation::class.java) ?: return chain.proceed(request)
        val shouldAttachAuthHeader = invocation.method().annotations.any { it is Authenticated }

        return if (shouldAttachAuthHeader) {
            val token = tokenCacheRepository.getAccessToken()
            if (token != null && token.isNotEmpty()) {
                val authenticatedRequest = request.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(authenticatedRequest)
            } else {
                chain.proceed(request)
            }
        } else {
            chain.proceed(request)
        }
    }
}