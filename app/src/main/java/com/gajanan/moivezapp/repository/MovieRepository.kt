package com.gajanan.moivezapp.repository

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.room.withTransaction
import com.gajanan.moivezapp.cache.database.MovieDatabase
import com.gajanan.moivezapp.cache.database.networkBoundResource
import com.gajanan.moivezapp.models.MovieDetailResponse
import com.gajanan.moivezapp.netwoek.RetrofitApiInterface
import com.gajanan.moivezapp.utils.ResultApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class MovieRepository
@Inject constructor(
    private val api: RetrofitApiInterface,
    private val db: MovieDatabase
) {
    companion object{
        const val TAG = "MovieRepository"
    }
    private val userDao = db.movieDao()
    private val _getMovieDetails = Channel<ResultApi<MovieDetailResponse>>()
    val getMovieDetails = _getMovieDetails.receiveAsFlow()

    suspend fun getMovieDetails(movieId:String){
        try {
            _getMovieDetails.send(ResultApi.Loading())
            val result = api.getMovieDetails(movieId = movieId)
            if (result.isSuccessful){
                Log.d(TAG, "detailResult -- ${result.body()}")
                _getMovieDetails.send(ResultApi.Success(result.body()))
            }
        }catch (e:Exception){
            _getMovieDetails.send(ResultApi.Error(e))
        }catch (e: NetworkErrorException){
            _getMovieDetails.send(ResultApi.Error(e))
        }
    }
    fun getAllPopularMovie() = networkBoundResource(
        query = {
            userDao.getAllPopularMovie()
        },
        fetch = {
            api.getAllPopularMovie()
        },
        saveFetchResult = { rockets ->
            db.withTransaction {
                userDao.deleteAllPopularMovie()
                Log.d(TAG, "saved : $rockets")
                userDao.insertPopularMovie(rockets.body()?.results ?: emptyList())
            }
        }
    )

}