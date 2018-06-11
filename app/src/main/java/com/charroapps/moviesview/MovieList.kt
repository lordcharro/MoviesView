package com.charroapps.moviesview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.charroapps.moviesview.Models.Result
import com.charroapps.moviesview.Network.GetDataService
import com.charroapps.moviesview.Network.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.charroapps.moviesview.Adapters.MovieAdapter
import com.charroapps.moviesview.Models.Movies
import kotlinx.android.synthetic.main.activity_movie_list.*


class MovieList : AppCompatActivity() {

    lateinit var adapter : MovieAdapter
    lateinit var service : GetDataService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        /*Create handle for the RetrofitInstance interface*/
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)

    }


    override fun onResume() {
        super.onResume()


    }
    /*Method to generate List of data using RecyclerView with custom adapter*/
    private fun generateDataList(movieList: List<Movies>) {

        adapter = MovieAdapter(this, movieList)
        val layoutManager = LinearLayoutManager(this@MovieList)
        movieRecView.setLayoutManager(layoutManager)
        movieRecView.setAdapter(adapter)
    }

    fun onSearchClick(view : View){

        var searchTxt = movieSearch.text.toString()
        movieProgressbar.visibility = View.VISIBLE
        val call = service.getMovies("9101601cde657ddff84db932cb019d8a", searchTxt)

        call.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>?, response: Response<Result>?) {
                movieProgressbar.visibility = View.GONE
                var results: Result? = response?.body()
                results?.results?.let { generateDataList(it) }
            }

            override fun onFailure(call: Call<Result>?, t: Throwable?) {
                movieProgressbar.visibility = View.GONE
                Toast.makeText(this@MovieList, "Something went wrong...Please try again later!", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
