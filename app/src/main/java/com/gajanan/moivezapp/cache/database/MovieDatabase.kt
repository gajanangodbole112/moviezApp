package com.gajanan.moivezapp.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gajanan.moivezapp.models.PopularMovie

@Database(entities = [PopularMovie::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao() : MovieDao

}