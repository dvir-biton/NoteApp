package com.fylora.noteapp.di

import android.app.Application
import androidx.room.Room
import com.fylora.noteapp.core.Commons.DATABASE_NAME
import com.fylora.noteapp.feature_note.data.data_sources.NoteDataBase
import com.fylora.noteapp.feature_note.data.repository.NoteRepositoryImpl
import com.fylora.noteapp.feature_note.domain.repository.NoteRepository
import com.fylora.noteapp.feature_note.domain.use_case.AddNoteUseCase
import com.fylora.noteapp.feature_note.domain.use_case.DeleteNoteUseCase
import com.fylora.noteapp.feature_note.domain.use_case.GetNoteByIdUseCase
import com.fylora.noteapp.feature_note.domain.use_case.GetNotesUseCase
import com.fylora.noteapp.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDataBase(app: Application): NoteDataBase{
        return Room.databaseBuilder(
            context = app,
            klass = NoteDataBase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(dataBase: NoteDataBase): NoteRepository{
        return NoteRepositoryImpl(dataBase.dao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases{
        return NoteUseCases(
            getNoteUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            getNoteByIdUseCase = GetNoteByIdUseCase(repository)
        )
    }
}