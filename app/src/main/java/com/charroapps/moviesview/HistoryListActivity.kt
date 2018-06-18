package com.charroapps.moviesview

import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.charroapps.moviesview.Adapters.HistoryAdapter
import com.charroapps.moviesview.Data.MovieDataBase
import com.charroapps.moviesview.Models.SavedMovie
import com.charroapps.moviesview.Utilities.EXTRA_MOVIE
import kotlinx.android.synthetic.main.activity_history_list.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HistoryListActivity : AppCompatActivity() {

    lateinit var adapter : HistoryAdapter
    lateinit var db : MovieDataBase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_list)

        //Get instance of the Database
        db = Room.databaseBuilder(applicationContext,
                MovieDataBase::class.java, "SavedMovie").build()

        doAsync {

            var allSavedMovies = db.SavedMovieDAO().getAll()
            uiThread {
                generateDataList(allSavedMovies)
            }
        }
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private fun generateDataList(movieList: List<SavedMovie>) {

        adapter = HistoryAdapter(this, movieList){movies ->
            //A item in the list was clicked, lets pass to the next activity and pass the info as a parcel
            val mdetailIntent = Intent(this@HistoryListActivity, HistoryDetailActivity::class.java)
            mdetailIntent.putExtra(EXTRA_MOVIE, movies)
            startActivity(mdetailIntent)
        }
        val layoutManager = LinearLayoutManager(this@HistoryListActivity)
        historyRecView.setLayoutManager(layoutManager)
        historyRecView.setAdapter(adapter)
    }
}