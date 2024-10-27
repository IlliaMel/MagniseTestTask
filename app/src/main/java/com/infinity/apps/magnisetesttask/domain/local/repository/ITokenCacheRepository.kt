package com.infinity.apps.magnisetesttask.domain.local.repository


interface ITokenCacheRepository {

    fun getAccessToken (): String?

    fun saveAccessToken (token: String) : String?

}