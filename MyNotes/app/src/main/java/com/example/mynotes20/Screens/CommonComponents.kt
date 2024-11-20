package com.example.mynotes20.Screens

import NoteViewModel
import SharedViewModel
import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Note
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mynotes20.R
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextButton

import androidx.compose.runtime.remember
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mynotes20.data.Note
import com.example.mynotes20.data.Task
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.Button


@Composable
fun NavigationBarNotesTasks(
    modifier: Modifier = Modifier,
    selectedScreen: Int,
    onScreenSelected: (Int) -> Unit // Callback para la selección de la pantalla
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier.fillMaxWidth()
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.EditNote,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.Notes))
            },
            selected = selectedScreen == 0,
            onClick = {
                onScreenSelected(0) // Cambia a pantalla de notas
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Checklist,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.Tasks))
            },
            selected = selectedScreen == 1,
            onClick = {
                onScreenSelected(1) // Cambia a pantalla de tareas
            }
        )
    }
}

// Inicio barra
@Composable
fun BarNameAppAndOptions(modifier: Modifier = Modifier, viewModel: SharedViewModel) {
    var showDialog by remember { mutableStateOf(false) }

    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable(onClick = { showDialog = true })
            )
        }
    }

    if (showDialog) {
        SettingsDialog(
            onDismiss = { showDialog = false },
            selectedLanguage = viewModel.selectedLanguage.value,
            onLanguageSelected = { viewModel.setLanguage(it) }, // Cambia el idioma
            selectedTheme = if (viewModel.isDarkTheme.value) "Oscuro" else "Claro",
            onThemeSelected = { viewModel.toggleTheme() } // Cambia el tema
        )
    }


}


@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    selectedTheme: String,
    onThemeSelected: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.Configuration)) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth() // Asegura que el diálogo use todo el ancho
                    .verticalScroll(rememberScrollState()) // Permite el desplazamiento si el contenido es grande
                    .padding(16.dp) // Espaciado interno
            ) {
                Text(text = stringResource(R.string.SelectLanguaje))
                RadioGroup(
                    options = listOf(
                        stringResource(R.string.Spanish),
                        stringResource(R.string.English)
                    ),
                    selectedOption = selectedLanguage,
                    onOptionSelected = onLanguageSelected // Cambia el idioma
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = stringResource(R.string.SelectTheme))
                RadioGroup(
                    options = listOf(
                        stringResource(R.string.Light),
                        stringResource(R.string.Dark)
                    ),
                    selectedOption = selectedTheme,
                    onOptionSelected = { theme ->
                        onThemeSelected() // Llama a la función para alternar el tema
                    }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.Close))
            }
        },
        modifier = Modifier.fillMaxWidth() // Asegura que el diálogo use todo el ancho
    )
}



@Composable
fun RadioGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOptionSelected(option) }
            ) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = { onOptionSelected(option) }
                )
                Text(text = option, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}
//---------------------------Fin barra

//--------------------Funcion de la alerta
@Composable
fun TaskAlertDialog(
    taskName: String,
    onComplete: () -> Unit,
    onPostpone: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Alerta de Tarea") },
        text = { Text("¿Qué deseas hacer con la tarea \"$taskName\"?") },
        confirmButton = {
            TextButton(onClick = {
                onComplete()
                onDismiss()
            }) {
                Text("Marcar como completada")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onPostpone()
                onDismiss()
            }) {
                Text("Posponer")
            }
        }
    )
}
//Finaliza la funcion de la alerta

@Composable
fun BodyContent(
    innerPadding: PaddingValues,
    selectedScreen: Int,
    isInitialMode: Boolean,
    onComplete: (String) -> Unit,
    viewModel: SharedViewModel, // Agregar el ViewModel aquí
    noteViewModel: NoteViewModel,
    navController: NavHostController
) {
    // Agrega un estado de desplazamiento
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(scrollState), // Permite el desplazamiento vertical
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BarNameAppAndOptions(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            viewModel = viewModel // Pasa el ViewModel a la barra
        )

        if (isInitialMode) {
            MainScreen("Notas", showSearchBar = false, onComplete, noteViewModel,navController)
            Spacer(modifier = Modifier.height(16.dp))
            MainScreen("Tareas", showSearchBar = false, onComplete, noteViewModel,navController)
        } else {
            when (selectedScreen) {
                0 -> MainScreen("Notas", showSearchBar = true, onComplete, noteViewModel,navController)
                1 -> MainScreen("Tareas", showSearchBar = true, onComplete, noteViewModel, navController)
            }
        }
    }
}



// Inicio funcion principal
@Composable
fun MainScreen(screenType: String, showSearchBar: Boolean,
               onComplete: (String) -> Unit, viewModel: NoteViewModel, navController: NavHostController) {
    val notes by viewModel.notes.collectAsState()
    val tasks by viewModel.tasks.collectAsState()

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        if (showSearchBar) {
            SearchBar(modifier = Modifier.padding(bottom = 16.dp))
        }

        Section(title = screenType) {
            if (screenType == "Notas") {
                // Mostrar notas de la base de datos
                for (note in notes) {
                    NoteItem(
                        note = note, // Asumiendo que Note tiene una propiedad name
                        onEdit = { noteToEdit ->
                            // Navega a MyNotesScreen y pasa la nota
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "note", noteToEdit
                            )
                            navController.navigate("MyNoteScreen") },
                        onDelete = { noteToDelete -> viewModel.delete(noteToDelete) },
                        navController = navController // Pasa navController
                    )
                }
            } else {
                // Mostrar tareas de la base de datos
                for (task in tasks) {
                    TaskItem(
                        task = task, // Pasa el objeto task
                        onEdit = { taskToEdit ->
                            // Navega a MyNotesScreen y pasa la tarea
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "task", taskToEdit
                            )
                            navController.navigate("MyNoteScreen")
                        },
                        onDelete = { taskToDelete -> viewModel.delete(taskToDelete) },
                        onComplete = { taskToComplete -> /* Acción al completar taskToComplete */ },
                        navController = navController
                    )
                }
            }
        }
    }
}


// Fin funcion principal

// Funcion para las secciones de tareas o las notas
@Composable
fun Section(title: String, content: @Composable () -> Unit) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        content()
    }
}
// Fin seccion tareas notas

// Elementos notas
@Composable
fun NoteItem(note: Note,
             onEdit: (Note) -> Unit, // onEdit ahora recibe el objeto Note
             onDelete: (Note) -> Unit, // onDelete ahora recibe el objeto Note
             navController: NavController
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { isExpanded = !isExpanded }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.AutoMirrored.Filled.Note,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = note.title, style = MaterialTheme.typography.bodyMedium)
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    IconButton(onClick = {
                        val bundle = Bundle().apply {
                            putInt("id", note.id)
                            putString("title", note.title)
                            putString("description", note.description)
                            putLong("dateCreate", note.dateCreate)
                            // ... agregar otros campos de Note al bundle ...
                        }
                        navController.currentBackStackEntry?.savedStateHandle?.set("note", bundle)
                        navController.navigate("MyNoteScreen")
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                }
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(
                text = stringResource(R.string.ConfirmDeletion)) },
            text = { Text(
                text = stringResource(R.string.ConfirmNote)) },
            confirmButton = {
                Button(onClick = {
                    onDelete(note)
                    showDialog = false
                }) {
                    Text(
                        text = stringResource(R.string.Delete))
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(
                        text = stringResource(R.string.Cancel))
                }
            }
        )
    }
}
// Fin elemento notas

// Inicio elemento tareas
@Composable
fun TaskItem(
    task: Task, // Pasa el objeto Task como parámetro
    onEdit: (Task) -> Unit, // onEdit ahora recibe el objeto Task
    onDelete: (Task) -> Unit, // onDelete ahora recibe el objeto Task
    onComplete: (Task) -> Unit, // onComplete ahora recibe el objeto Task
    navController: NavController
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { isExpanded = !isExpanded }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = task.title, style = MaterialTheme.typography.bodyMedium)
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    IconButton(onClick = {
                        val bundle = Bundle().apply {
                            putInt("id", task.id)
                            putString("title", task.title)
                            putString("description", task.description)
                            putLong("dateComplete", task.dateComplete)
                            putLong("dateCreate", task.dateCreate)
                            putString("time", task.time)
                            putBoolean("repeat", task.repeat)
                            putString("repeatFrecuency", task.repeatFrecuency)
                            putString("duration", task.duration)
                            putBoolean("complete",task.complete)
                            // ... agregar otros campos de Note al bundle ...
                        }
                        navController.currentBackStackEntry?.savedStateHandle?.set("task", bundle)
                        navController.navigate("MyNoteScreen")
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                    IconButton(onClick = { onComplete(task) }) {
                        Icon(Icons.Default.Check, contentDescription = "Completar")
                    }
                }
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(
                text = stringResource(R.string.ConfirmDeletion)) },
            text = { Text(
                text = stringResource(R.string.ConfirmTask)) },
            confirmButton = {
                Button(onClick = {
                    onDelete(task)
                    showDialog = false
                }) {
                    Text(
                        text = stringResource(R.string.Delete))
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(
                        text = stringResource(R.string.Cancel))
                }
            }
        )
    }
}
// Fin elemento tareas

// Barra de búsqueda
@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text(text = stringResource(R.string.Search)) },
        modifier = modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Buscar")
        }
    )
}
