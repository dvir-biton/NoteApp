package com.fylora.noteapp.feature_note.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fylora.noteapp.feature_note.presentation.add_edit_note.components.AddEditNoteScreen
import com.fylora.noteapp.feature_note.presentation.notes.components.NotesScreen
import com.fylora.noteapp.feature_note.presentation.ui.theme.NoteAppTheme
import com.fylora.noteapp.feature_note.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint
import java.io.BufferedReader
import java.io.InputStreamReader

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                    ) {
                        composable(route = Screen.NotesScreen.route) {
                            NotesScreen(navController)
                        }
                        composable(
                            route = Screen.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}&noteContent={noteContent}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteContent"
                                ){
                                    type = NavType.StringType
                                    defaultValue = ""
                                }
                            )
                        ) {
                            val color = it.arguments?.getInt("noteColor") ?: -1

                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = color
                            )
                        }
                    }

                    if(intent != null)
                        onNewIntent(intent)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val noteText = intent?.getStringExtra(Intent.EXTRA_TEXT)

        val noteUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM)
        }

        val noteContent = (if(noteUri != null) {
            readTextFromUri(this, noteUri)
        } else noteText) ?: return
        navController.navigate(Screen.AddEditNoteScreen.route + "?noteContent=$noteContent")
    }

    private fun readTextFromUri(context: Context, uri: Uri): String {
        val stringBuilder = StringBuilder()
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
            }
        }
        return stringBuilder.toString()
    }
}
