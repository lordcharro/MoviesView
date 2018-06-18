package com.charroapps.moviesview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.charroapps.moviesview.Models.Movies
import com.charroapps.moviesview.Utilities.EXTRA_MOVIE
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import android.arch.persistence.room.Room
import com.charroapps.moviesview.Data.MovieDataBase
import com.charroapps.moviesview.Models.SavedMovie
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class DetailActivity : AppCompatActivity() {

    lateinit var movie : Movies
    lateinit var db : MovieDataBase
    var newMovie = SavedMovie(0,"","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //Get the data from the last activity
        movie = intent.getParcelableExtra<Movies>(EXTRA_MOVIE)

        //title, description and image from the parcel sent from the previous activity
        detail_title_txt.setText(movie.title)
        detail_description_txt.setText(movie.overview)

        var builder : Picasso.Builder =  Picasso.Builder(this@DetailActivity)
        builder.downloader(OkHttp3Downloader(this@DetailActivity))
        builder.build().load("https://image.tmdb.org/t/p/w500/" + movie.poster_path)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(detail_image)
    }

    override fun onResume() {
        super.onResume()


        //Get instance of the Database
        db = Room.databaseBuilder(applicationContext,
                MovieDataBase::class.java, "SavedMovie").build()

        newMovie.description = movie.overview
        newMovie.title = movie.title

        //Save the data to the database
        accessDatabase()

    }

    fun accessDatabase(){
        doAsync {
            db.SavedMovieDAO().insert(newMovie)
            var newElement = db.SavedMovieDAO().getAll()
            uiThread {
            }
        }
    }
}