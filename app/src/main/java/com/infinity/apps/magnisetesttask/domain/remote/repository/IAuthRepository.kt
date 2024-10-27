package com.infinity.apps.magnisetesttask.domain.remote.repository

import com.infinity.apps.magnisetesttask.domain.model.TokenPair

interface IAuthRepository {

    suspend fun getToken (username: String, password: String): TokenPair

}