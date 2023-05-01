package com.example.bpjs.listener

import com.example.movie.model.MovieItem

interface OnMovieClickListener {

    fun onClickMovieListener(movies:MovieItem,genres:String)
}