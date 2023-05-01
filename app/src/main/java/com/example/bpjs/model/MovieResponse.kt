package com.example.movie.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class MovieResponse(

	@field:SerializedName("page")
	val page: Int? = 0,

	@field:SerializedName("total_pages")
	val totalPages: Int? = 0,

//	@field:SerializedName("results")
//	val results: List<MovieItem>? = null,

	@field:SerializedName("results")
	val results: MutableList<MovieItem>,

	@field:SerializedName("total_results")
	val totalResults: Int? = 0
) : Parcelable