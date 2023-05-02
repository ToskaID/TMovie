package com.example.bpjs.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import com.bumptech.glide.Glide
import com.example.bpjs.Constant
import com.example.bpjs.R
import com.example.bpjs.databinding.ActivityDetailBinding
import com.example.bpjs.databinding.ActivityMainBinding
import com.example.movie.model.MovieItem

class DetailActivity : AppCompatActivity() {
    companion object{
        const val movie = "movie"
        const val genres = "genres"

    }
    private var _bindng : ActivityDetailBinding? = null
    private val binding get() = _bindng!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bindng = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.parcelable<MovieItem>(movie)?.let { intent.getStringExtra(genres)?.let { genres -> setupData(it, genres) } }


    }

    private fun setupData(movie: MovieItem, genres:String){
        with(movie) {
            binding.apply {
                Glide.with(this@DetailActivity).load("${Constant.PHOTO_BASE_URL}${posterPath}")
                    .into(posterDetail)
                titleDetail.text = title
                releaseDateDetail.text = releaseDate
                ratingText.text = voteAverage.toString()
                ratingBar.rating = voteAverage?.div(2) ?: 0f
                genreDetail.text = genres.dropLast(2)
                overview.text = movie.overview
            }
//
        }
    }

    private inline fun<reified T: Parcelable> Intent.parcelable(key:String) : T? =when{
        Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }
}