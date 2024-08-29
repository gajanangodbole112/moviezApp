package com.gajanan.moivezapp.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gajanan.moivezapp.models.PopularMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovie(item: List<PopularMovie>)

    @Query("DELETE FROM movies")
    suspend fun deleteAllPopularMovie()

    @Query("SELECT * FROM movies")
    fun getAllPopularMovie() : Flow<List<PopularMovie>>
}