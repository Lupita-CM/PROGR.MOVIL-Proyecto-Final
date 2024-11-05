package com.example.mynotes20.Screens

import SharedViewModel
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
        title = { Text("Configuración") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth() // Asegura que el diálogo use todo el ancho
                    .verticalScroll(rememberScrollState()) // Permite el desplazamiento si el contenido es grande
                    .padding(16.dp) // Espaciado interno
            ) {
                Text("Selecciona un idioma:")
                RadioGroup(
                    options = listOf("Español", "Inglés"),
                    selectedOption = selectedLanguage,
                    onOptionSelected = onLanguageSelected // Cambia el idioma
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Selecciona un tema:")
                RadioGroup(
                    options = listOf("Claro", "Oscuro"),
                    selectedOption = selectedTheme,
                    onOptionSelected = { theme ->
                        onThemeSelected() // Llama a la función para alternar el tema
                    }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
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
    viewModel: SharedViewModel // Agregar el ViewModel aquí
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
