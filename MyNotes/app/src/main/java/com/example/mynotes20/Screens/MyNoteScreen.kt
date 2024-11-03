package com.example.mynotes20.Screens
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mynotes20.R

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
@Composable
fun MyNotesScreen(navController: NavController) {
    var isTask by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var date by remember { mutableStateOf(TextFieldValue("DD/MM/AAAA")) }
    var time by remember { mutableStateOf(TextFieldValue("05:35 AM")) }
    var repeat by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var showMediaOptions by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { if (showMediaOptions) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(color = MaterialTheme.colorScheme.secondary),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = { /* Acción para imágenes */ }) {
                            Icon(Icons.Default.Image, contentDescription = "Imágenes")
                        }
                        Text(text = stringResource(R.string.Photos), color = Color.White)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = { /* Acción para videos */ }) {
                            Icon(Icons.Default.VideoLibrary, contentDescription = "Videos")
                        }
                        Text(text = stringResource(R.string.Videos), color = Color.White)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = { /* Acción para audio */ }) {
                            Icon(Icons.Default.Audiotrack, contentDescription = "Audio")
                        }
                        Text(text = stringResource(R.string.Audio), color = Color.White)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = { /* Acción para grabar audio */ }) {
                            Icon(Icons.Default.Mic, contentDescription = "Grabar Audio")
                        }
                        Text(text = stringResource(R.string.Record_audio), color = Color.White)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = { /* Acción para cámara */ }) {
                            Icon(Icons.Default.CameraAlt, contentDescription = "Cámara")
                        }
                        Text(text = stringResource(R.string.Camera), color = Color.White)
                    }
                }
            }
        } }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
//                .background(Color.Black),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            //Spacer(modifier = Modifier.height(10.dp))
            NavigationRailItem(
                icon = {
                    Icon(
                        Icons.Filled.ArrowCircleLeft,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.Start)
                    )
                },
                selected = true,
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    //.fillMaxWidth()
                    .heightIn(min = 56.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.Title),
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier
            )
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text(text = stringResource(R.string.Title), color = Color.Gray) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.DarkGray,
                    unfocusedContainerColor = Color.DarkGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.Description),
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier
            )
            TextField(
                value = description,
                onValueChange = { description = it },
                placeholder = {
                    Text(
                        text = stringResource(R.string.Description),
                        color = Color.Gray
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.DarkGray,
                    unfocusedContainerColor = Color.DarkGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.Media),
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier
            )
            // Área para multimedia
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Gray, RoundedCornerShape(8.dp))
                    .clickable { showMediaOptions = !showMediaOptions },
                contentAlignment = Alignment.Center
            ) {
                //Text(text = "+", color = Color.White, fontSize = 30.sp)
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.Classification),
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier
            )
            // Clasificación
            Row(
                horizontalArrangement = Arrangement.Center, // Centra horizontalmente el contenido
                verticalAlignment = Alignment.CenterVertically // Centra verticalmente el contenido
            ) {
                    RadioButton(
                        selected = !isTask,
                        onClick = { isTask = false },
                        colors = RadioButtonDefaults.colors(selectedColor = Color.White)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(R.string.Notes), color = Color.White)

                Spacer(modifier = Modifier.width(16.dp))

                RadioButton(
                    selected = isTask,
                    onClick = { isTask = true },
                    colors = RadioButtonDefaults.colors(selectedColor = Color.White)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(R.string.Tasks), color = Color.White)
            }

            // Mostrar campos adicionales si se selecciona "Tarea"
            if (isTask) {
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = date,
                    onValueChange = { date = it },
                    placeholder = { Text("DD/MM/AAAA", color = Color.Gray) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.DarkGray,
                        unfocusedContainerColor = Color.DarkGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = time,
                    onValueChange = { time = it },
                    placeholder = { Text("05:35 AM", color = Color.Gray) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.DarkGray,
                        unfocusedContainerColor = Color.DarkGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Text(text = stringResource(R.string.Repeat), color = Color.White)
                    Spacer(modifier = Modifier.width(16.dp))
                    Switch(
                        checked = repeat,
                        onCheckedChange = { repeat = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.White)
                    )
                }
                if (repeat) {
                    var selectedOption by remember { mutableStateOf("") }
                    var selectedDurationOption by remember { mutableStateOf("") }


                    Column(modifier = Modifier.padding(16.dp)) {
                        // Títulos de repetición
                        //Text(text = stringResource(R.string.Repeat), color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))

                        // RadioButtons para opciones de repetición
                        Column {
                            listOf(
                                stringResource(R.string.Every_hour),
                                stringResource(R.string.Everyday),
                                stringResource(R.string.Every_week),
                                stringResource(R.string.Every_month),
                                stringResource(R.string.Every_year)
                            ).forEach { text ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = (text == selectedOption),
                                        onClick = { selectedOption = text }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = text, color = Color.White)
                                }
                            }
                        }
                    }
                    Column(modifier = Modifier.padding(16.dp)) {
                        Spacer(modifier = Modifier.height(16.dp))

                        // Opciones adicionales de duración
                        Text(text = stringResource(R.string.Duration), color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))

                        Column {
                            listOf(
                                stringResource(R.string.Forever),
                                stringResource(R.string.specific_number_of_times),
                                stringResource(R.string.Until)
                            ).forEach { text ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = (text == selectedDurationOption),
                                        onClick = { selectedDurationOption = text }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = text, color = Color.White)
                                }
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize(), // Ocupa todo el espacio disponible
                contentAlignment = Alignment.Center // Alinea el contenido en el centro
            ) {
                // Agregamos un botón que contiene el texto "Añadir"
                Button(
                    onClick = { /* No hay funcionalidad */ },
                    modifier = Modifier
                        .padding(16.dp) // Añade un margen
                ) {
                    Text(text = stringResource(R.string.Add)) // Texto dentro del botón
                }
            }
        }

    }
}
