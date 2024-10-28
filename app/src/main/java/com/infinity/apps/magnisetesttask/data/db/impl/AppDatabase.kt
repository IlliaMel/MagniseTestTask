package com.infinity.apps.magnisetesttask.data.db.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.infinity.apps.magnisetesttask.data.db.converter.Converters
import com.infinity.apps.magnisetesttask.data.db.dao.InstrumentDataDao
import com.infinity.apps.magnisetesttask.data.model.db.InstrumentDataEntity

@Database(
    entities = [InstrumentDataEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun instrumentDataDao(): InstrumentDataDao

}