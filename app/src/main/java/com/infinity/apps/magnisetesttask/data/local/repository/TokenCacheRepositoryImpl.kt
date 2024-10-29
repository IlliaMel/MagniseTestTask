package com.infinity.apps.magnisetesttask.data.local.repository

import com.infinity.apps.magnisetesttask.domain.local.repository.ISecureCacheRepository
import com.infinity.apps.magnisetesttask.domain.local.repository.ITokenCacheRepository
import javax.inject.Inject

class TokenCacheRepositoryImpl @Inject constructor(
    private val secureCacheRepository : ISecureCacheRepository
) : ITokenCacheRepository {

    private val key: String = "access_token_key"

    override fun getAccessToken(): String? {
       return try {
           secureCacheRepository.get(key = key, defaultValue = "")
       } catch (e : Exception) {
           null
       }
    }

    override fun saveAccessToken(token: String): String? {
        return try {
            token.apply {
                secureCacheRepository.put(key = key, value = this)
            }
        } catch (e : Exception) {
            null
        }
    }

}