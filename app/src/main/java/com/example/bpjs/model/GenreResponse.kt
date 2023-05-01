package com.example.bpjs.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GenreResponse(

	@field:SerializedName("genres")
	val genres: List<GenresItem>? = null
) : Parcelable