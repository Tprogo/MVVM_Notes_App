package com.example.searchnotesappviewmodel

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {

    @Query ("SELECT * FROM Notes_Table")
   suspend fun getAllNotes(): List<NotesData>

    @Update
    suspend fun updateNotes(notes: NotesData)

    @Delete
   suspend  fun deleteNotes(notesData: NotesData)



    @Insert (onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: NotesData)

    @Query("SELECT * FROM Notes_Table WHERE title_text LIKE :query OR description_text LIKE:query ")
    suspend fun serachNotes(query: String?): List<NotesData>


}