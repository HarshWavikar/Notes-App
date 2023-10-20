package com.example.notesapp_cheezy.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("Select * FROM notes_table ORDER BY created DESC")
    fun getAllNotesSortedByCreated() : LiveData<List<Note>>
}