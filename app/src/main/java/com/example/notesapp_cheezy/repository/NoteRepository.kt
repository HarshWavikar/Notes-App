package com.example.notesapp_cheezy.repository

import androidx.lifecycle.LiveData
import com.example.notesapp_cheezy.data.Note
import com.example.notesapp_cheezy.data.NoteDAO
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDAO: NoteDAO) {

    suspend fun insertNote(note: Note){
        noteDAO.insertNote(note)
    }

    suspend fun deleteNote(note: Note){
        noteDAO.deleteNote(note)
    }

     fun getAllNotesSortedByCreated() : LiveData<List<Note>>{
        return noteDAO.getAllNotesSortedByCreated()
    }
}

//If we had RemoteData Source then all those functions would have been added here as well