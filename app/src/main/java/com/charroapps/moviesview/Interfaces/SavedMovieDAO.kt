package com.charroapps.moviesview.Interfaces

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.charroapps.moviesview.Models.SavedMovie

@Dao
interface SavedMovieDAO {

    @Query("SELECT * from SavedMovie")
    fun getAll() : List<SavedMovie>

    @Insert(onConflict = REPLACE)
    fun insert(SavedMovie: SavedMovie)

    @Query("DELETE from SavedMovie")
    fun deleteAll()
}