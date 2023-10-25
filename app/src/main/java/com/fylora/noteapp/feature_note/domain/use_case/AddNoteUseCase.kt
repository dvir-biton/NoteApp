package com.fylora.noteapp.feature_note.domain.use_case

import com.fylora.noteapp.feature_note.domain.model.InvalidNoteException
import com.fylora.noteapp.feature_note.domain.model.Note
import com.fylora.noteapp.feature_note.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNoteUseCase(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if(note.title.isBlank()){
            throw InvalidNoteException("the title cannot be empty!")
        }
        if(note.body.isBlank()){
            throw InvalidNoteException("the body cannot be empty!")
        }
        repository.addNote(note)
    }
}