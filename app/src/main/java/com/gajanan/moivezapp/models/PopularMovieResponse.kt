package com.gajanan.moivezapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class PopularMovie(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val poster_path : String
)
data class PopularMovieResponse(
    val results : List<PopularMovie>
)