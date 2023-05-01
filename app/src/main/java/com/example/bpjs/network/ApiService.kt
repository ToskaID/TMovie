package com.example.bpjs.network

import com.example.bpjs.model.GenreResponse
import com.example.movie.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

//    @GET("movie/popular")
//    suspend fun geMoviePopuler(
//        @Query("api_key") key: String?,
//        @Query("page") page: Int?
//    ): MovieResponse

    @GET("movie/popular")
    suspend fun geMoviePopuler(
        @Query("api_key") key: String?,
        @Query("page") page: Int?
    ): Response<MovieResponse>

    @GET("genre/movie/list")
    suspend fun getGenreMovie(
        @Query("api_key") key: String?,
    ): GenreResponse


}