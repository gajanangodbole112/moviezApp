package com.gajanan.moivezapp.models

import androidx.room.Entity

@Entity(tableName = "movie_detail")
data class MovieDetailResponse(
    val id: Int,
    val backdrop_path: String,
    val genres: List<Genre>,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
)
@Entity
data class Genre(
    val id: Int,
    val name: String
)