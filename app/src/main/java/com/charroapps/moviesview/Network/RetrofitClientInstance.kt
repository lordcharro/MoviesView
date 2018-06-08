package com.charroapps.moviesview.Network


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClientInstance {


    companion object {
        fun getRetrofitInstance() : Retrofit{

                var retrofit  : Retrofit = Retrofit.Builder()
                        .baseUrl("https://jsonplaceholder.typicode.com").addConverterFactory(GsonConverterFactory.create())
                        .build()

            return retrofit
        }
    }

}