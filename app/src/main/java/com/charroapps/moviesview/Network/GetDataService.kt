package com.charroapps.moviesview.Network

import com.charroapps.moviesview.Models.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface GetDataService {

    @GET("/3/search/movie")
    fun getMovies(@Query("api_key") api_key : String,
                  @Query("query") movie : String): Call<Result>

}