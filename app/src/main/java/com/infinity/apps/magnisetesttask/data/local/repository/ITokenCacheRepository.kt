package com.infinity.apps.magnisetesttask.data.local.repository


interface ITokenCacheRepository {

    fun getSpecialToken (key : String): String?

    fun saveSpecialToken (key : String, token: String) : String

}