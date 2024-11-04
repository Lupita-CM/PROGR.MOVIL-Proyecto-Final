package com.example.mynotes20

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mynotes20.Screens.BodyContent
import com.example.mynotes20.Screens.FirstScreen
import com.example.mynotes20.Screens.MainScreen
import com.example.mynotes20.Screens.MyNotesScreen
import com.example.mynotes20.Screens.NavigationBarNotesTasks
import com.example.mynotes20.ui.theme.MyNotes20Theme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mynotes20.Screens.MainViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNotes20Theme {
                val navController = rememberNavController() // Aquí creamos el NavController
                SetupNavGraph(navController) // Pasamos el navController a la función SetupNavGraph
            }
        }
    }
}




@Composable
fun SetupNavGraph(navController: NavHostController) {
    val mainViewModel: MainViewModel = viewModel()
    NavHost(navController = navController, startDestination = "FirstScreen") {
        composable("FirstScreen") {
            FirstScreen(navController, onComplete = { taskName ->
                println("Tarea completada: $taskName")
            }, mainViewModel = mainViewModel) // Pasa el MainViewModel
        }
        composable("MyNoteScreen") {
            MyNotesScreen(navController)
        }
    }
}
