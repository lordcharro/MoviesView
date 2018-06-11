package com.charroapps.moviesview.Network

import com.charroapps.moviesview.Models.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GetDataService {

    //https://api.themoviedb.org/3/search/movie?api_key=put_your_api_key&query=keyword
    @GET("/3/search/movie")
    fun getMovies(@Query("api_key") api_key : String,
                  @Query("query") movie : String): Call<Result>

    @GET("/3/search/movie?api_key=put_your_api_key&query=scary")
    fun getMovies2(): Call<Result>
}