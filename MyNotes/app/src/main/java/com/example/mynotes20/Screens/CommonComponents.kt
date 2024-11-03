package com.example.mynotes20.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Note
import androidx.compose.material.icons.filled.*
import com.example.mynotes20.R



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
fun BarNameAppAndOptions(modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("Español") }
    var selectedTheme by remember { mutableStateOf("Claro") }

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
            selectedLanguage = selectedLanguage,
            onLanguageSelected = { selectedLanguage = it },
            selectedTheme = selectedTheme,
            onThemeSelected = { selectedTheme = it }
        )
    }
}

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    selectedTheme: String,
    onThemeSelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Configuración") },
        text = {
            Column {
                Text("Selecciona un idioma:")
                RadioGroup(
                    options = listOf("Español", "Inglés"),
                    selectedOption = selectedLanguage,
                    onOptionSelected = onLanguageSelected
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Selecciona un tema:")
                RadioGroup(
                    options = listOf("Claro", "Oscuro"),
                    selectedOption = selectedTheme,
                    onOptionSelected = onThemeSelected
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
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
fun BodyContent(innerPadding: PaddingValues, selectedScreen: Int, isInitialMode: Boolean, onComplete: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BarNameAppAndOptions(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        if (isInitialMode) {
            MainScreen("Notas", showSearchBar = false, onComplete)
            Spacer(modifier = Modifier.height(16.dp))
            MainScreen("Tareas", showSearchBar = false, onComplete)
        } else {
            when (selectedScreen) {
                0 -> MainScreen("Notas", showSearchBar = true, onComplete)
                1 -> MainScreen("Tareas", showSearchBar = true, onComplete)
            }
        }
    }
}

// Inicio funcion principal
@Composable
fun MainScreen(screenType: String, showSearchBar: Boolean, onComplete: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        if (showSearchBar) {
            SearchBar(modifier = Modifier.padding(bottom = 16.dp))
        }

        Section(title = screenType) {
            if (screenType == "Notas") {
                NoteItem(
                    name = "Nota 1",
                    onEdit = { /* Acción de editar Nota 1 */ },
                    onDelete = { /* Acción de eliminar Nota 1 */ }
                )
                NoteItem(
                    name = "Nota 2",
                    onEdit = { /* Acción de editar Nota 2 */ },
                    onDelete = { /* Acción de eliminar Nota 2 */ }
                )
                NoteItem(
                    name = "Nota 3",
                    onEdit = { /* Acción de editar Nota 3 */ },
                    onDelete = { /* Acción de eliminar Nota 3 */ }
                )
            } else {
                TaskItem(
                    name = "Tarea 1",
                    onEdit = { /* Acción de editar Tarea 1 */ },
                    onDelete = { /* Acción de eliminar Tarea 1 */ },
                    onComplete = { onComplete("Tarea 1") } // Llama a onComplete con el nombre de la tarea
                )
                TaskItem(
                    name = "Tarea 2",
                    onEdit = { /* Acción de editar Tarea 2 */ },
                    onDelete = { /* Acción de eliminar Tarea 2 */ },
                    onComplete = { onComplete("Tarea 2") }
                )
                TaskItem(
                    name = "Tarea 3",
                    onEdit = { /* Acción de editar Tarea 3 */ },
                    onDelete = { /* Acción de eliminar Tarea 3 */ },
                    onComplete = { onComplete("Tarea 3") }
                )
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
fun NoteItem(name: String, onEdit: () -> Unit, onDelete: () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

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
                Text(text = name, style = MaterialTheme.typography.bodyMedium)
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                }
            }
        }
    }
}
// Fin elemento notas

// Inicio elemento tareas
@Composable
fun TaskItem(
    name: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onComplete: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

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
                Text(text = name, style = MaterialTheme.typography.bodyMedium)
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                    IconButton(onClick = onComplete) {
                        Icon(Icons.Default.Check, contentDescription = "Completar")
                    }
                }
            }
        }
    }
}
// Fin elemento tareas

// Barra de búsqueda
@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Buscar...") },
        modifier = modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Buscar")
        }
    )
}
