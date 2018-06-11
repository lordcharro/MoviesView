package com.charroapps.moviesview.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.charroapps.moviesview.Models.Movies
import com.charroapps.moviesview.R
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class MovieAdapter (val context : Context, val movies: List<Movies>) : RecyclerView.Adapter<MovieAdapter.MovieHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false)
        return MovieHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.count()
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {

        holder?.bindMovie(movies[position], context)
    }

    inner class MovieHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val movieImage =itemView?.findViewById<ImageView>(R.id.movieImg)
        val movieName = itemView?.findViewById<TextView>(R.id.movieTitleTxt)

        fun bindMovie(movie: Movies, context: Context){

            if (movieName != null) {
               // movieName.setText(movies.get(adapterPosition).getMyTitle())
                movieName.setText(movies.get(adapterPosition).title)
            }

            var builder : Picasso.Builder =  Picasso.Builder(context)
            builder.downloader(OkHttp3Downloader(context))
            builder.build().load("https://image.tmdb.org/t/p/w500/" + movies.get(adapterPosition).poster_path)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(movieImage)
        }
    }
}