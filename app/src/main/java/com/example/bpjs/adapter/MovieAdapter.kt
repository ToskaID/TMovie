package com.example.bpjs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bpjs.Constant
import com.example.bpjs.databinding.ItemListMovieBinding
import com.example.bpjs.listener.OnMovieClickListener
import com.example.bpjs.model.GenresItem
import com.example.movie.model.MovieItem

class MovieAdapter:RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private val movieList = ArrayList<MovieItem>()
    private val genreList = ArrayList<GenresItem>()
    private lateinit var onClickMovieListener:OnMovieClickListener

    fun onClickListenerMovie(onClickListener:OnMovieClickListener){
        this.onClickMovieListener = onClickListener
    }

    fun setMovies(list:List<MovieItem>){
        this.movieList.clear()
        this.movieList.addAll(list)
        notifyDataSetChanged()
    }

    fun setGenres(list:List<GenresItem>){
        this.genreList.clear()
        this.genreList.addAll(list)
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding:ItemListMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bindng = ItemListMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(bindng)
    }

    override fun getItemCount(): Int  = movieList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(movieList[position]){
                binding.apply {
                    tvTitle.text = title
                    tvReleaseDate.text = movieList[position].releaseDate
                    ratingText.text = voteAverage.toString()
                    ratbarImg.rating = voteAverage?.div(2) ?: 0f

                    Glide.with(itemView).load("${Constant.PHOTO_BASE_URL}$posterPath").into(ivPoster)

                    val map = genreList.associate { it.id to it.name }
                    val genres = StringBuilder()

                    val genresId = ArrayList<Int>()
                    if (genreIds != null){
                        genresId.addAll(genreIds)
                        for(data in genreIds){
                            genres.append("${map[data]}, ")
                        }
                    }
                    tvGenre.text = genres.dropLast(2)
                    itemView.setOnClickListener { onClickMovieListener.onClickMovieListener(this@with,genres.toString()) }
                }
            }
        }
    }
}