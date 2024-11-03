package com.example.mynotes20.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController // Importa NavController
import androidx.navigation.compose.rememberNavController
import com.example.mynotes20.ui.theme.MyNotes20Theme

@Composable
fun FirstScreen(navController: NavController, onComplete: (String) -> Unit) {
    var selectedScreen by remember { mutableStateOf(0) } // 0 para Notas, 1 para Tareas
    var isInitialMode by remember { mutableStateOf(true) } // Modo inicial que muestra ambas secciones

    Scaffold(
        bottomBar = {
            NavigationBarNotesTasks(
                selectedScreen = selectedScreen,
                onScreenSelected = { newScreen ->
                    selectedScreen = newScreen
                    isInitialMode = false // Cambiar a modo individual
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Navega a la pantalla de notas cuando se presiona el botón
                    navController.navigate("MyNoteScreen")
                },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar", tint = Color.White)
            }
        }
    ) { innerPadding ->
        BodyContent(innerPadding, selectedScreen, isInitialMode, onComplete) // Asegúrate de pasar onComplete
    }
}

//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyNotes20Theme {
        FirstScreen(rememberNavController()) { taskName -> // Cambia a un navController por defecto
            println("Tarea completada: $taskName") // Maneja la tarea completada
        }
    }
}
