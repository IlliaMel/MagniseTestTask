package com.infinity.apps.magnisetesttask.domain.local.repository

interface ISecureCacheRepository {

    fun <T: Any> put(key: String, value: T)

    fun <T: Any> get(key: String, defaultValue: T): T

}