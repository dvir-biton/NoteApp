package com.fylora.noteapp.feature_note.domain.repository

import com.fylora.noteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class NoteRepositoryFake: NoteRepository {

    private val notes = MutableStateFlow(emptyList<Note>())

    override fun getNotes(): Flow<List<Note>> {
        return notes
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notes.value.find { it.id == id }
    }

    override suspend fun addNote(note: Note) {
        notes.value += note
    }

    override suspend fun deleteNote(note: Note) {
        notes.value -= note
    }
}