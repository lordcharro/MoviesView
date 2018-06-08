package com.charroapps.moviesview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.ProgressDialog
import android.widget.Toast
import com.charroapps.moviesview.Models.Movie
import com.charroapps.moviesview.Network.GetDataService
import com.charroapps.moviesview.Network.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.v7.widget.LinearLayoutManager
import com.charroapps.moviesview.Adapters.MovieAdapter
import kotlinx.android.synthetic.main.activity_movie_list.*


class MovieList : AppCompatActivity() {

    var progressDoalog: ProgressDialog? = null
    lateinit var adapter : MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)


        var progressDoalog = ProgressDialog(this@MovieList);
        if(progressDoalog != null){
            progressDoalog.setMessage("Loading....");
            progressDoalog.show();
        }


        /*Create handle for the RetrofitInstance interface*/
        val service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)
        val call = service.getAllPhotos()


        call.enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                progressDoalog.dismiss()
                response.body()?.let { generateDataList(it) }
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                progressDoalog.dismiss()
                Toast.makeText(this@MovieList, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
            }
        })
    }


    /*Method to generate List of data using RecyclerView with custom adapter*/
    private fun generateDataList(movieList: List<Movie>) {

        adapter = MovieAdapter(this, movieList)
        val layoutManager = LinearLayoutManager(this@MovieList)
        movieRecView.setLayoutManager(layoutManager)
        movieRecView.setAdapter(adapter)
    }
}
