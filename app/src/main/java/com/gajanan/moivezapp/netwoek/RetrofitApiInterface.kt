package com.gajanan.moivezapp.netwoek

import com.gajanan.moivezapp.models.MovieDetailResponse
import com.gajanan.moivezapp.models.PopularMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitApiInterface {
    @GET(Endpoints.POPULAR_MOVIE)
    suspend fun getAllPopularMovie() : Response<PopularMovieResponse>

    @GET("${Endpoints.MOVIE}/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId:String
    ) : Response<MovieDetailResponse>
}