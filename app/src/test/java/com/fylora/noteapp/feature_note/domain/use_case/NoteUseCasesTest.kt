package com.fylora.noteapp.feature_note.domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.fylora.noteapp.feature_note.domain.repository.NoteRepository
import com.fylora.noteapp.feature_note.domain.repository.NoteRepositoryFake
import com.fylora.noteapp.feature_note.domain.repository.note
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NoteUseCasesTest {
    private lateinit var noteRepository: NoteRepository
    private lateinit var addNoteUseCase: AddNoteUseCase
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var getNoteByIdUseCase: GetNoteByIdUseCase
    private lateinit var getNotesUseCase: GetNotesUseCase
    private lateinit var noteUseCases: NoteUseCases

    @BeforeEach
    fun setup() {
        noteRepository = NoteRepositoryFake()
        addNoteUseCase = AddNoteUseCase(noteRepository)
        deleteNoteUseCase = DeleteNoteUseCase(noteRepository)
        getNoteByIdUseCase = GetNoteByIdUseCase(noteRepository)
        getNotesUseCase = GetNotesUseCase(noteRepository)

        noteUseCases = NoteUseCases(
            getNotesUseCase,
            deleteNoteUseCase,
            addNoteUseCase,
            getNoteByIdUseCase
        )
    }

    @Test
    fun `Add note, note is found with the id`() = runBlocking {
        val note = note()
            .copy(
                title = "test",
                body = "test body",
                id = 3
            )

        noteRepository.addNote(note)

        assertThat(getNoteByIdUseCase(3)).isEqualTo(note)
    }

    @Test
    fun `Add note, note is added to the database`() = runBlocking {
        val note = note()
            .copy(
                title = "test",
                body = "test body",
                id = 3
            )

        addNoteUseCase(note)

        assertThat(noteRepository.getNoteById(3)).isEqualTo(note)
    }

    @Test
    fun `Delete note, note is deleted from the database`() = runBlocking {
        val note = note()
            .copy(
                title = "test",
                body = "test body",
                id = 3
            )
        addNoteUseCase(note)

        deleteNoteUseCase(note)
        assertThat(noteRepository.getNoteById(3)).isNull()
    }
}