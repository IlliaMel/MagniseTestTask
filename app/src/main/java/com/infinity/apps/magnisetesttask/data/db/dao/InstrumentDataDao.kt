package com.infinity.apps.magnisetesttask.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.infinity.apps.magnisetesttask.data.model.db.InstrumentDataEntity

@Dao
interface InstrumentDataDao {
    @Query("SELECT * FROM instrument_data")
    suspend fun getCachedInstrumentData(): List<InstrumentDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setCachedInstrumentData(data: List<InstrumentDataEntity>)
}