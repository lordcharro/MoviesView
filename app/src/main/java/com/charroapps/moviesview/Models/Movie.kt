package com.charroapps.moviesview.Models

class Movie(val title : String, val image : String) {

    lateinit var thumbnailUrl : String

    fun myGetThumbnailUrl() : String {
        if(thumbnailUrl != null){
            return thumbnailUrl
        }else
            return "error in thumbnail"

    }

    fun getMyTitle(): String {
        return title
    }
}