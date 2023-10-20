package com.example.notesapp_cheezy.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import com.example.notesapp_cheezy.data.Note
import com.example.notesapp_cheezy.repository.NoteRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {

    fun insertNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    val notes = repository.getAllNotesSortedByCreated()              //This fun will return LiveData and can be observed
}