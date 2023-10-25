package com.fylora.noteapp.feature_note.data.data_sources

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fylora.noteapp.feature_note.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDataBase: RoomDatabase() {

    abstract val dao: NoteDao
}