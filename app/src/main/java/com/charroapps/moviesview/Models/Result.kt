package com.charroapps.moviesview.Models

import android.os.Parcel
import android.os.Parcelable

data class Result(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<Movies>
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.createTypedArrayList(Movies)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(page)
        parcel.writeInt(total_results)
        parcel.writeInt(total_pages)
        parcel.writeTypedList(results)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Result> {
        override fun createFromParcel(parcel: Parcel): Result {
            return Result(parcel)
        }

        override fun newArray(size: Int): Array<Result?> {
            return arrayOfNulls(size)
        }
    }
}

data class Movies(
    val vote_count: Int,
    val id: Int,
    val video: Boolean,
    val vote_average: Double,
    val title: String,
    val popularity: Double,
    val poster_path: String,
    val original_language: String,
    val original_title: String,
    val genre_ids: List<Int>,
    val backdrop_path: String,
    val adult: Boolean,
    val overview: String,
    val release_date: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createIntArray().toList(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString())

    /*Use the Elvis operator to get a standard value in case of a null value*/
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(vote_count ?: -1)
        parcel.writeInt(id ?: -1)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeDouble(vote_average ?: -1.0)
        parcel.writeString(title ?: "")
        parcel.writeDouble(popularity ?: -1.0)
        parcel.writeString(poster_path ?: "")
        parcel.writeString(original_language ?: "")
        parcel.writeString(original_title ?: "")
        parcel.writeIntArray(genre_ids.toIntArray())
        parcel.writeString(backdrop_path ?: "")
        parcel.writeByte(if (adult) 1 else 0)
        parcel.writeString(overview ?: "")
        parcel.writeString(release_date ?: "")
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movies> {
        override fun createFromParcel(parcel: Parcel): Movies {
            return Movies(parcel)
        }

        override fun newArray(size: Int): Array<Movies?> {
            return arrayOfNulls(size)
        }
    }
}

