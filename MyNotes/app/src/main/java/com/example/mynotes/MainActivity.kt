package com.example.mynotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource

import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mynotes.ui.theme.MyNotesTheme

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon

import androidx.compose.ui.res.stringResource
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.material.icons.filled.Spa
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.NavigationRailItem
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Note
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton

import androidx.compose.material3.Surface

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import com.example.mynotes.ui.theme.MyNotesTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyNotesTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { SootheBottomNavigation() }, // Barra de navegación inferior
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        // Mostrar barra de búsqueda o sección de colección favorita
                        FavoriteCollectionCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )

                        // Mostrar contenido principal (MainScreen)
                        MainScreen()
                    }
                }
            }
        }
    }
}


@Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hola $name!",
            modifier = modifier
        )
    }

    @Composable
    fun SearchBar(
        modifier: Modifier = Modifier
    ) {
        NavigationRailItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null
                )
            },
            selected = true,
            onClick = {},
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
        )
    }

@Composable
@Preview(showBackground = true)
fun FavoriteCollectionCard(
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier.fillMaxWidth() // Asegúrate de que ocupe todo el ancho
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth() // Asegúrate de que el Row ocupe todo el ancho
                .padding(16.dp) // Añade padding interno si es necesario
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.weight(1f)) // Esto empuja el icono hacia la derecha
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )


        }
    }
}
@Preview(showBackground = true)
@Composable
fun MainScreen() {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Acción para agregar tarea o nota */ },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar", tint = Color.White)
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Section(title = "Tareas") {
                    TaskItem(name = "Task name")
                    TaskItem(name = "Task name")
                    TaskItem(name = "Task name")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Section(title = "Notas") {
                    NoteItem(name = "Note name")
                    NoteItem(name = "Note name")
                    NoteItem(name = "Note name")
                }
            }
        }
    )
}

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

@Composable
fun TaskItem(name: String) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(Icons.Filled.Checklist, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = name, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun NoteItem(name: String) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(Icons.Filled.Note, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = name, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
    private fun SootheBottomNavigation(modifier: Modifier = Modifier) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = modifier
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
                selected = true,
                onClick = {}
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
                selected = false,
                onClick = {}
            )
        }
    }



    //@Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        MyNotesTheme {
            Greeting("Android")
        }
    }