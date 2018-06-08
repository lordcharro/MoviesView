package com.charroapps.moviesview.Network

import com.charroapps.moviesview.Models.Movie
import retrofit2.Call
import retrofit2.http.GET



interface GetDataService {

    @GET("/photos")
    fun getAllPhotos(): Call<List<Movie>>
}