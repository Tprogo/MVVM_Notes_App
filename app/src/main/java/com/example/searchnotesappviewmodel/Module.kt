package com.example.searchnotesappviewmodel

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton

    fun getRoomBuider(@ApplicationContext context: Context): NotesDatabase{

        return Room.databaseBuilder(
            context,
            NotesDatabase::class.java,
            "mvvm_room_diffutil"
        ).build()

    }

    @Provides
    @Singleton
    fun getNotesDao(notesDatabase: NotesDatabase): NotesDao{
        return notesDatabase.getNotesDao()
    }
}