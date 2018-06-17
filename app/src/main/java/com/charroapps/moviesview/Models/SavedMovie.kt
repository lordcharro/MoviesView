package com.charroapps.moviesview.Models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class SavedMovie (
        @PrimaryKey (autoGenerate = true) var id : Long,
        @ColumnInfo var title : String,
        @ColumnInfo var description : String)
