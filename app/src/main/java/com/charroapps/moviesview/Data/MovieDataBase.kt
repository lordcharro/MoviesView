package com.charroapps.moviesview.Data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.charroapps.moviesview.Interfaces.SavedMovieDAO
import com.charroapps.moviesview.Models.SavedMovie


@Database(entities = arrayOf(SavedMovie::class), version = 1)
abstract class MovieDataBase : RoomDatabase() {

    abstract fun SavedMovieDAO() : SavedMovieDAO

}


