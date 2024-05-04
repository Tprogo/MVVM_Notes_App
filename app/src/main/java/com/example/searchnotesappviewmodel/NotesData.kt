package com.example.searchnotesappviewmodel

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity (tableName = "Notes_Table")
data class NotesData(
    @PrimaryKey (autoGenerate = true)
    val id: Int?,
    @ColumnInfo (name = "title_text")
    val title: String,
    @ColumnInfo (name = "description_text")
    val description: String
): Parcelable
