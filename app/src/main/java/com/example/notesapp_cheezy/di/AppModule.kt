package com.example.notesapp_cheezy.di

import android.content.Context
import androidx.room.Room
import com.example.notesapp_cheezy.data.NoteDAO
import com.example.notesapp_cheezy.data.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun providesNoteDatabase(@ApplicationContext context: Context): NoteDatabase = Room.databaseBuilder(context, NoteDatabase::class.java, "noteDB").build()


    @Singleton
    @Provides
    fun providesNoteDAO(db: NoteDatabase): NoteDAO =  db.getNoteDAO()
}