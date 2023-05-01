package com.example.bpjs.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bpjs.adapter.MovieAdapter
import com.example.bpjs.databinding.ActivityMainBinding
import com.example.bpjs.listener.OnMovieClickListener
import com.example.bpjs.network.RequestState
import com.example.bpjs.viewmodel.MovieViewModel
import com.example.movie.model.MovieItem

class MainActivity : AppCompatActivity() {

    private var _bindng :ActivityMainBinding? = null
    private val binding get() = _bindng!!
    private var adapter:MovieAdapter? = null
    private var layoutManager : RecyclerView.LayoutManager? = null
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bindng = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPopularMovie()
        getGenre()
        setupRecyclerView()

        adapter?.onClickListenerMovie(object : OnMovieClickListener{
            override fun onClickMovieListener(movies: MovieItem, genres: String) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.movie, movies)
                intent.putExtra(DetailActivity.genres, genres)
                startActivity(intent)
            }

        })
    }

    private fun setupRecyclerView(){
        adapter = MovieAdapter()
        layoutManager = GridLayoutManager(this, 2)
        binding.apply {
            rvMovie.adapter = adapter
            rvMovie.layoutManager = layoutManager


        }
    }
    private fun getPopularMovie(){

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.getPopulerMovie().observe(this){
            if (it != null){
                when(it){
                    is RequestState.Loading -> showLoading()
                    is RequestState.Success -> {
                        hideLoading()
                        it.data?.results?.let { data -> adapter?.setMovies(data) }
                    }
                    is RequestState.Error -> {
                        hideLoading()
                        Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getGenre(){

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.getGenre().observe(this){
            if (it != null){
                when(it){
                    is RequestState.Loading -> {}
                    is RequestState.Success -> it.data?.genres?.let { data -> adapter?.setGenres(data) }

                    is RequestState.Error -> Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }


    private fun showLoading(){
        binding.loading.show()
    }


    private fun hideLoading(){
        binding.loading.hide()
    }



}