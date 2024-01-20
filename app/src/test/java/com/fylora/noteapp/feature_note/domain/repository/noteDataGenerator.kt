package com.fylora.noteapp.feature_note.domain.repository

import com.fylora.noteapp.feature_note.domain.model.Note

fun note(): Note {
    return Note(
        title = "",
        body = "",
        timeStamp = System.currentTimeMillis(),
        color = 0,
        id = 0
    )
}