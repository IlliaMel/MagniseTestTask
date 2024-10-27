package com.infinity.apps.magnisetesttask.domain.local.manager

interface ITimeManager {

    fun getLastUpdateTime(key : String): Long

    fun updateLastUpdateTime(key : String, data : Long): Long

}