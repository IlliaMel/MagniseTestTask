package com.infinity.apps.magnisetesttask.domain.local.repository


interface ITokenCacheRepository {

    fun getSpecialToken (key : String): String?

    fun saveSpecialToken (key : String, token: String) : String

}