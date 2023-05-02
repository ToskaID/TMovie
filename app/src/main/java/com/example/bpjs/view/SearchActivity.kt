package com.example.bpjs.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bpjs.R
import com.example.bpjs.adapter.MovieAdapter
import com.example.bpjs.databinding.ActivitySearchBinding
import com.example.bpjs.listener.OnMovieClickListener
import com.example.bpjs.network.RequestState
import com.example.bpjs.viewmodel.MovieViewModel
import com.example.movie.model.MovieItem

class SearchActivity : AppCompatActivity() {
    companion object{
        const val query = "query"
    }
    private var _bindng :ActivitySearchBinding? = null
    private val binding get() = _bindng!!

    private var isSearchAgain = false
    private var adapter: MovieAdapter? = null
    private var layoutManager : RecyclerView.LayoutManager? = null
    private lateinit var viewModel: MovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bindng = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.search.setText(intent.getStringExtra(query))
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        if(!isSearchAgain) viewModel.seacrhQuery(binding.search.text.toString())

        binding.searchButton.setOnClickListener {
            val query = binding.search.text.toString()
            when {
                query.isEmpty() -> binding.search.error = "please insert keywoard"
                else->{
                    isSearchAgain  = true
                    viewModel.seacrhQuery(query)
                }
            }
        }
        getGenre()
        searchQuery()

        setupRecyclerView()

        adapter?.onClickListenerMovie(object : OnMovieClickListener {
            override fun onClickMovieListener(movies: MovieItem, genres: String) {
                val intent = Intent(this@SearchActivity, DetailActivity::class.java)
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
            rvMovie.addOnScrollListener(scrollListener)


        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(!recyclerView.canScrollVertically(1)){
                viewModel.seacrhQuery(binding.search.text.toString())
            }
        }
    }

    private fun searchQuery(){

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.searchResponse.observe(this){
            if (it != null){
                when(it){
                    is RequestState.Loading -> showLoading()
                    is RequestState.Success -> {
                        hideLoading()
//                        it.data?.results?.let { data -> adapter?.setMovies(data) }
                        it.data?.results?.let { data -> adapter?.differ?.submitList(data.toList()) }
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