package com.fitz.movie.persistence.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "movies")
data class MovieEntity(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "image_url") var imageURL: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "rating") var movieRating: Float,
    @PrimaryKey var id: Int = 0
) : Parcelable