package com.example.searchnotesappviewmodel

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(version = 2, entities = [NotesData::class])

abstract class  NotesDatabase  : RoomDatabase() {
    abstract fun getNotesDao(): NotesDao



}