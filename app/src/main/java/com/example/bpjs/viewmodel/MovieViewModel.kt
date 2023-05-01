package com.example.bpjs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.bpjs.model.GenreResponse
import retrofit2.HttpException
import com.example.bpjs.network.RequestState
import com.example.bpjs.repository.MovieRepository
import com.example.movie.model.MovieResponse
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

class MovieViewModel : ViewModel() {

    private val repo : MovieRepository = MovieRepository()
//    private var page =1
    private var popularPage =1
    private var _populerResponse = MutableLiveData<RequestState<MovieResponse?>>()
    var populerResponse:LiveData<RequestState<MovieResponse?>> = _populerResponse   
    private var populerMovieResponse:MovieResponse? = null


//        fun getPopulerMovie() : LiveData<RequestState<MovieResponse>> = liveData {
//        emit(RequestState.Loading)
//        try {
//            val response = repo.geMoviePopuler(page)
//            emit(RequestState.Success(response))
//        }catch (e: HttpException){
//          e.response()?.errorBody()?.string()?.let { RequestState.Error(it) }
//              ?.let { emit(it) }
//        }
//    }

    fun getPopulerMovie()  {
        viewModelScope.launch {
            _populerResponse.postValue(RequestState.Loading)
            val response = repo.geMoviePopuler(popularPage)
            _populerResponse.postValue(handlePopulerMovieResponse(response))
        }
    }

    private fun handlePopulerMovieResponse(response: Response<MovieResponse>): RequestState<MovieResponse?>? {

        return if(response.isSuccessful){
            response.body()?.let {
                popularPage++
                if (populerMovieResponse == null) populerMovieResponse = it else {
                    val oldMovies = populerMovieResponse?.results
                    val newMovies = it.results
                    oldMovies?.addAll(newMovies)
                }
            }

            RequestState.Success(populerMovieResponse ?: response.body())
        }else RequestState.Error(
            try{
                response.errorBody()?.string()?.let{
                    JSONObject(it).get("status_message")
                }
            }catch (e: JSONException){
                e.localizedMessage
            }as String
        )
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