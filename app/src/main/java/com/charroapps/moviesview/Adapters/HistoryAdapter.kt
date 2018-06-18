package com.charroapps.moviesview.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.charroapps.moviesview.Models.SavedMovie
import com.charroapps.moviesview.R

class HistoryAdapter (val context : Context, val movies: List<SavedMovie>, val itemClick : (SavedMovie) -> Unit) : RecyclerView.Adapter<HistoryAdapter.HistoryHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false)
        return HistoryHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return movies.count()
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {

        holder?.bindMovie(movies[position], context)
    }

    inner class HistoryHolder(itemView: View?, val itemClick: (SavedMovie) -> Unit) : RecyclerView.ViewHolder(itemView) {

        val historyImage =itemView?.findViewById<ImageView>(R.id.movieImg)
        val historyName = itemView?.findViewById<TextView>(R.id.movieTitleTxt)


        fun bindMovie(movie: SavedMovie, context: Context){

            if (historyName != null) {
                historyName.setText(movies.get(adapterPosition).title)
            }

            //Get the itemClick for each row
            itemView.setOnClickListener { itemClick(movie) }
        }
    }
}