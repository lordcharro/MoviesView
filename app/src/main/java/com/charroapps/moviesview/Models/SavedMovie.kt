package com.charroapps.moviesview.Models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity
class SavedMovie (
        @PrimaryKey (autoGenerate = true) var id : Long,
        @ColumnInfo var title : String,
        @ColumnInfo var description : String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SavedMovie> {
        override fun createFromParcel(parcel: Parcel): SavedMovie {
            return SavedMovie(parcel)
        }

        override fun newArray(size: Int): Array<SavedMovie?> {
            return arrayOfNulls(size)
        }
    }

}
