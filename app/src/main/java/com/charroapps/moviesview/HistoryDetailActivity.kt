package com.charroapps.moviesview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.charroapps.moviesview.Models.SavedMovie
import kotlinx.android.synthetic.main.activity_detail.*
import com.charroapps.moviesview.Utilities.EXTRA_MOVIE

class HistoryDetailActivity : AppCompatActivity() {

    lateinit var movie : SavedMovie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //Get the data from the last activity
        movie = intent.getParcelableExtra<SavedMovie>(EXTRA_MOVIE)

        //title, description and image from the parcel sent from the previous activity
        detail_title_txt.setText(movie.title)
        detail_description_txt.setText(movie.description)

    }
}