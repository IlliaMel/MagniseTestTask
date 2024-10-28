package com.infinity.apps.magnisetesttask.data.local.manager

import com.infinity.apps.magnisetesttask.domain.local.manager.ITimeManager
import com.infinity.apps.magnisetesttask.domain.local.repository.ISecureCacheRepository
import javax.inject.Inject

class TimeManagerImpl @Inject constructor(
    private val secureCacheRepository: ISecureCacheRepository
) : ITimeManager {

    override fun getLastUpdateTime(key: String): Long {
        return try {
            secureCacheRepository.get(key = key, defaultValue = "0").toLong()
        } catch (e: Exception) {
            0L
        }
    }

    override fun updateLastUpdateTime(key: String, data: Long): Long {
        return try {
            secureCacheRepository.put(key = key, value = data.toString())
            data
        } catch (e: Exception) {
            0L
        }
    }
}