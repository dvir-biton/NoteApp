package com.fylora.noteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fylora.noteapp.feature_note.presentation.ui.theme.BabyBlue
import com.fylora.noteapp.feature_note.presentation.ui.theme.LightGreen
import com.fylora.noteapp.feature_note.presentation.ui.theme.RedOrange
import com.fylora.noteapp.feature_note.presentation.ui.theme.RedPink
import com.fylora.noteapp.feature_note.presentation.ui.theme.Violet

@Entity
data class Note(
    val title: String,
    val body: String,
    val timeStamp: Long,
    val color: Int,

    @PrimaryKey
    val id: Int? = null,
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String): Exception(message)