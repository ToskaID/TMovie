package com.example.bpjs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.bpjs.model.GenreResponse
import retrofit2.HttpException
import com.example.bpjs.network.RequestState
import com.example.bpjs.repository.MovieRepository
import com.example.movie.model.MovieResponse

class MovieViewModel : ViewModel() {

    private val repo : MovieRepository = MovieRepository()
    private var page =1

        fun getPopulerMovie() : LiveData<RequestState<MovieResponse>> = liveData {
        emit(RequestState.Loading)
        try {
            val response = repo.geMoviePopuler(page)
            emit(RequestState.Success(response))
        }catch (e: HttpException){
          e.response()?.errorBody()?.string()?.let { RequestState.Error(it) }
              ?.let { emit(it) }
        }
    }

    fun getGenre() : LiveData<RequestState<GenreResponse>> = liveData {
        emit(RequestState.Loading)
        try {
            val response = repo.getGenres()
            emit(RequestState.Success(response))
        }catch (e: HttpException){
            e.response()?.errorBody()?.string()?.let { RequestState.Error(it) }
                ?.let { emit(it) }
        }
    }

}