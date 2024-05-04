package com.example.searchnotesappviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor (val notesDao: NotesDao): ViewModel() {

    private val mutableLiveData = MutableLiveData<List<NotesData>>()

    val notesLiveData: LiveData<List<NotesData>>
        get() = mutableLiveData

    suspend fun getAllNotes() {
        val notes = notesDao.getAllNotes()
        mutableLiveData.postValue(notes)
    }

    suspend fun insertNotes(note: NotesData){
        notesDao.insertNotes(note)
    }

    suspend fun deletNotes(note: NotesData){
        notesDao.deleteNotes(note)
    }

    suspend fun searchNote(query: String){
        mutableLiveData.postValue(notesDao.serachNotes(query))
    }
}