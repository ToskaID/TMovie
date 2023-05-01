package com.example.bpjs.repository


import com.example.bpjs.Constant
import com.example.bpjs.network.ApiConfig

class MovieRepository {

    private val client = ApiConfig.getApiService()

    suspend fun  geMoviePopuler(page:Int) = client.geMoviePopuler(Constant.API_KEY, page)
    suspend fun getGenres() = client.getGenreMovie(Constant.API_KEY)
}