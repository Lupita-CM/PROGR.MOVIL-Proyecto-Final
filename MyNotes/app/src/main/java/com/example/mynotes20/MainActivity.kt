package com.example.mynotes20

import NoteViewModel
import SharedViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mynotes20.Screens.FirstScreen
import com.example.mynotes20.Screens.MyNotesScreen
import com.example.mynotes20.ui.theme.MyNotes20Theme
import androidx.room.Room
import com.example.mynotes20.data.InventoryDatabase
import com.example.mynotes20.data.InventoryDatabase.Companion.MIGRATION_1_2
import com.example.mynotes20.data.InventoryDatabase.Companion.MIGRATION_2_3
import com.example.mynotes20.data.InventoryDatabase.Companion.MIGRATION_3_4
import com.example.mynotes20.data.InventoryDatabase.Companion.MIGRATION_4_5
import com.example.mynotes20.data.InventoryDatabase.Companion.MIGRATION_5_6
import com.example.mynotes20.data.NoteViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val db = Room.databaseBuilder(
                applicationContext,
                InventoryDatabase::class.java,
                "notes_database"
            ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6).build()

            // Obtener los DAOs
            val noteDao = db.noteDao()
            val taskDao = db.taskDao()
            val mediaDao = db.mediaDao()
            val mediataskDao = db.mediataskDao()
            //val reminderDao = db.reminderDao()

            // Crear la fábrica y el ViewModel
            val viewModelFactory = NoteViewModelFactory(noteDao, taskDao, mediaDao, mediataskDao)
            val noteViewModel: NoteViewModel = viewModel(factory = viewModelFactory)
            val viewModel: SharedViewModel = viewModel()

            MyNotes20Theme(viewModel = viewModel) { // Pasa el viewModel al tema
                val navController = rememberNavController() // Aquí creamos el NavController
                SetupNavGraph(navController, viewModel, noteViewModel) // Pasamos el navController y el viewModel
            }
        }
    }
}

@Composable
fun SetupNavGraph(navController: NavHostController, viewModel: SharedViewModel, noteViewModel: NoteViewModel) {
    NavHost(navController = navController, startDestination = "FirstScreen") {
        composable("FirstScreen") {
            FirstScreen(navController, onComplete = { taskName ->
                println("Tarea completada: $taskName")
            }, viewModel = viewModel, noteViewModel = noteViewModel) // Pasa el viewModel aquí
        }
        composable("MyNoteScreen") {
            MyNotesScreen(navController, noteViewModel)
        }
    }
}
