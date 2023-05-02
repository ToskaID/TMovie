package com.example.bpjs.repository


import com.example.bpjs.Constant
import com.example.bpjs.network.ApiConfig

class MovieRepository {

    private val client = ApiConfig.getApiService()

    suspend fun  geMoviePopuler(page:Int) = client.geMoviePopuler(Constant.API_KEY, page)
    suspend fun  searchQuery(query:String,page:Int) = client.search(Constant.API_KEY, query,page)
    suspend fun getGenres() = client.getGenreMovie(Constant.API_KEY)
}