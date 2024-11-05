package com.example.mynotes20.Screens

import SharedViewModel
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mynotes20.ui.theme.MyNotes20Theme

@Composable
fun FirstScreen(
    navController: NavController,
    onComplete: (String) -> Unit,
    viewModel: SharedViewModel = viewModel()
) {
    // Obteniendo estados desde el ViewModel
    val selectedScreen by viewModel.selectedScreen.collectAsState()
    val isInitialMode by viewModel.isInitialMode.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBarNotesTasks(
                selectedScreen = selectedScreen,
                onScreenSelected = { newScreen -> viewModel.selectScreen(newScreen) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("MyNoteScreen") },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar", tint = Color.White)
            }
        }
    ) { innerPadding ->
        BodyContent(
            innerPadding,
            selectedScreen = selectedScreen,
            isInitialMode = isInitialMode,
            onComplete = onComplete,
            viewModel = viewModel // Pasa el ViewModel aquí
        )
    }
}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyNotes20Theme {
        FirstScreen(
            navController = rememberNavController(),
            onComplete = { taskName -> println("Tarea completada: $taskName") },
            viewModel = SharedViewModel() // Puedes pasar una instancia para el preview
        )
    }
}
*/