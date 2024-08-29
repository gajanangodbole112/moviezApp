package com.gajanan.moivezapp.ui.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gajanan.moivezapp.models.MovieDetailResponse
import com.gajanan.moivezapp.models.PopularMovie
import com.gajanan.moivezapp.repository.MovieRepository
import com.gajanan.moivezapp.utils.ResultApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Movie(
    var popularMovieResponse: List<PopularMovie>? = null,
    var movieDetailResponse: MovieDetailResponse? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _movieState = mutableStateOf(Movie())
    val movieState = _movieState
    val getAllPopularMovie = repository.getAllPopularMovie().asLiveData()

    init {
        viewModelScope.launch {
            repository.getMovieDetails.collect {
                when (it) {
                    is ResultApi.Error -> {
                        Log.d("ViewM", "error - ${it.error?.message}")
                    }
                    is ResultApi.Loading -> {
                    }
                    is ResultApi.Success -> {
                        _movieState.value = _movieState.value.copy(
                            movieDetailResponse = it.data
                        )
                    }
                }
            }
        }
    }

    fun getMovieDetails(movieId: String) = viewModelScope.launch {
        repository.getMovieDetails(movieId = movieId)
    }
}