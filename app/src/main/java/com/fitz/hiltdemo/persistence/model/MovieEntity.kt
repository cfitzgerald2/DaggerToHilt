package com.fitz.hiltdemo.persistence.model

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
    @ColumnInfo(name = "rating") var movieRating: Double,
    @PrimaryKey(autoGenerate = true) var  id: Int? = null
) : Parcelable