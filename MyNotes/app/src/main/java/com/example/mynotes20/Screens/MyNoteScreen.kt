package com.example.mynotes20.Screens


import NoteViewModel
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mynotes20.R
import com.example.mynotes20.data.Note
import com.example.mynotes20.data.Task
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.activity.compose.rememberLauncherForActivityResult
import com.example.mynotes20.data.Media
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.core.content.ContextCompat
import com.example.mynotes20.data.MediaItem
import com.example.mynotes20.data.MediaTask
import coil.compose.AsyncImage
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
@Composable
fun MyNotesScreen(navController: NavController, viewModel: NoteViewModel) {
    val noteBundle = navController.previousBackStackEntry?.savedStateHandle?.get<Bundle>("note")
    val taskBundle = navController.previousBackStackEntry?.savedStateHandle?.get<Bundle>("task")

    var isTask by remember { mutableStateOf(taskBundle != null)  }
    var taskComplete by remember { mutableStateOf(taskBundle?.getBoolean("complete") ?: false)   }
    var title by remember {mutableStateOf(TextFieldValue(noteBundle?.getString("title") ?: taskBundle?.getString("title") ?: "")) }
    var description by remember { mutableStateOf(TextFieldValue(noteBundle?.getString("description") ?: taskBundle?.getString("description") ?: "")) }
    var date by remember { mutableStateOf(TextFieldValue(
        taskBundle?.getLong("date")?.let {
            val date = Date(it)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateFormat.format(date)
        } ?: "DD/MM/AAAA")
    )}
    var time by remember { mutableStateOf(TextFieldValue(taskBundle?.getString("time") ?: "05:35 AM")) }
    var repeat by remember { mutableStateOf(taskBundle?.getBoolean("repeat") ?: false) }
    val scrollState = rememberScrollState()
    var showMediaOptions by remember { mutableStateOf(false) }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dateCreation = System.currentTimeMillis()

// Variables para gestionar los archivos seleccionados
    val selectedMediaList = remember { mutableStateListOf<Pair<Uri, String>>() } // Lista de URIs y tipos de medios
    //var showMediaOptions by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Lanzadores para permisos
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                Toast.makeText(context, "Permiso denegado para acceder a archivos", Toast.LENGTH_SHORT).show()
            }
        }
    )

    // Lanzadores para actividades
    val launcherForImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris ->
            uris.forEach { uri ->
                selectedMediaList.add(uri to "image")
            }
        }
    )

    val launcherForVideo = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris ->
            uris.forEach { uri ->
                selectedMediaList.add(uri to "video")
            }
        }
    )

    val launcherForAudio = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        uris.forEach { uri ->
            selectedMediaList.add(uri to "audio")
        }
    }

    val launcherForCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                selectedMediaList.add(Uri.EMPTY to "image") // Reemplaza Uri.EMPTY con la URI generada
            }
        }
    )

    // Solicitar permiso para acceder a archivos
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showMediaOptions) {
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
                            IconButton(onClick = { launcherForImage.launch("image/*") }) {
                                Icon(Icons.Default.Image, contentDescription = "Imágenes")
                            }
                            Text(text = stringResource(R.string.Photos), color = Color.White)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(onClick = { launcherForVideo.launch("video/*") }) {
                                Icon(Icons.Default.VideoLibrary, contentDescription = "Videos")
                            }
                            Text(text = stringResource(R.string.Videos), color = Color.White)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(onClick = { launcherForAudio.launch("audio/*") }) {
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
                            IconButton(onClick = { /*uri = ComposeFileProvider.getImageUri(context)
                                //imageUri = uri
                                launcherForCamera.launch(uri!!)*/ }) {
                                Icon(Icons.Default.CameraAlt, contentDescription = "Cámara")
                            }
                            Text(text = stringResource(R.string.Camera), color = Color.White)
                        }
                    }
                }
            }
        }
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
                onClick = {
                    title = TextFieldValue("")
                    description = TextFieldValue("")
                    date = TextFieldValue("DD/MM/AAAA")
                    time = TextFieldValue("05:35 AM")
                    repeat = false
                    /*selectedMediaUri = null
                    selectedMediaType = null*/
                    navController.popBackStack() },
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
            LazyColumn (
                modifier = Modifier.heightIn(max = 200.dp) // Limitar la altura del LazyColumn
            ){
                items(selectedMediaList) { media ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        when (media.second) {
                            "image" -> {
                                AsyncImage(
                                    model = media.first,
                                    contentDescription = "Imagen seleccionada",
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                            "video" -> {
                                Icon(
                                    imageVector = Icons.Filled.VideoLibrary,
                                    contentDescription = "Video seleccionado",
                                    tint = Color.White // o el color que prefieras
                                )
                            }
                            "audio" -> {
                                Icon(
                                    imageVector = Icons.Filled.Audiotrack,
                                    contentDescription = "Audio seleccionado",
                                    tint = Color.White // o el color que prefieras
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${media.second.capitalize()}: ${media.first}",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
            // Mostrar archivos seleccionados
            /*Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(selectedMediaUris) { uri ->
                    Text("Archivo seleccionado: ${uri.path}", color = Color.White)
                }
            }*/

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
                val lifecycleOwner = LocalLifecycleOwner.current
                // Agregamos un botón que contiene el texto "Añadir"
                Button(
                    onClick = {
                        lifecycleOwner.lifecycleScope.launch {
                            val dateLong = try {
                                dateFormat.parse(date.text)?.time ?: 0L
                            } catch (e: ParseException) {
                                // Manejar la excepción, por ejemplo, mostrar un mensaje de error
                                0L // O algún otro valor por defecto
                            }
                            var newNote: Note? = null
                            var newTask: Task? = null

                            if (isTask) {
                                val taskId = if (taskBundle != null) {
                                    // Editar tarea existente
                                    val updatedTask = Task(
                                        id = taskBundle.getInt("id"), // Obtener el ID de la tarea
                                        title = title.text,
                                        description = description.text,
                                        dateComplete = dateLong,
                                        dateCreate = taskBundle.getLong("dateCreate"), // Mantener la fecha de creación original
                                        time = time.text,
                                        repeat = repeat,
                                        repeatFrecuency = "",
                                        duration = "",
                                        complete = taskComplete
                                    )
                                    viewModel.update(updatedTask) // Llamar a la función update del ViewModel
                                    newTask = updatedTask
                                } else {
                                    // Crear nueva tarea
                                    newTask = Task(
                                        title = title.text,
                                        description = description.text,
                                        dateComplete = dateLong,
                                        dateCreate = dateCreation,
                                        time = time.text,
                                        repeat = repeat,
                                        repeatFrecuency = "",
                                        duration = "",
                                        complete = false
                                    )
                                    //viewModel.insert(newTask)
                                    val id = viewModel.insertTaskAndGetId(newTask)
                                    newTask = newTask.copy(id = id.toInt())
                                }
                                /*newTask?.let {
                                if (selectedMediaUri != null && selectedMediaType != null) {
                                    val mediaTask = MediaTask(
                                        filePath = selectedMediaUri.toString(),
                                        mediaType = selectedMediaType!!,
                                        taskId = it!!.id // Usar el ID de la tarea
                                    )
                                    viewModel.insert(mediaTask)

                                    // Limpiar las variables de Uri y tipo de medio
                                    selectedMediaUri = null
                                    selectedMediaType = null
                                }
                            }*/

                                newTask.let { task ->
                                    selectedMediaList.forEach { media ->
                                        val mediaTask = MediaTask(
                                            filePath = media.first.toString(),
                                            mediaType = media.second,
                                            taskId = task.id
                                        )
                                        viewModel.insertMediaTask(mediaTask)
                                    }
                                }
                            } else {
                                if (noteBundle != null) {
                                    // Editar nota existente
                                    val updatedNote = Note(
                                        id = noteBundle.getInt("id"), // Obtener el ID de la nota
                                        title = title.text,
                                        description = description.text,
                                        dateCreate = noteBundle.getLong("dateCreate") // Mantener la fecha de creación original
                                    )
                                    viewModel.update(updatedNote) // Llamar a la función update del ViewModel
                                    newNote = updatedNote
                                } else {
                                    // Crear nueva nota
                                    newNote = Note(
                                        title = title.text,
                                        description = description.text,
                                        dateCreate = dateCreation
                                    )
                                    //viewModel.insert(newNote)
                                    val id = viewModel.insertNoteAndGetId(newNote)
                                    newNote = newNote.copy(id = id.toInt())
                                }
                                /*newNote?.let {
                                if (selectedMediaUri != null && selectedMediaType != null) {
                                    val mediaNote = Media(
                                        filePath = selectedMediaUri.toString(),
                                        mediaType = selectedMediaType!!,
                                        noteId = it.id // Usar el ID de la nota
                                    )
                                    viewModel.insert(mediaNote)

                                    // Limpiar las variables de Uri y tipo de medio
                                    selectedMediaUri = null
                                    selectedMediaType = null
                                }
                            }*/
                                // Insertar archivos multimedia
                                newNote.let { note ->
                                    selectedMediaList.forEach { media ->
                                        val mediaNote = Media(
                                            filePath = media.first.toString(),
                                            mediaType = media.second,
                                            noteId = note.id
                                        )
                                        viewModel.insertMedia(mediaNote)
                                    }
                                }
                            }

                            title = TextFieldValue("")
                            description = TextFieldValue("")
                            date = TextFieldValue("DD/MM/AAAA")
                            time = TextFieldValue("05:35 AM")
                            repeat = false
                            // Limpieza de la lista de archivos seleccionados
                            selectedMediaList.clear()
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp) // Añade un margen
                ) {
                    Text(text = stringResource(R.string.Add)) // Texto dentro del botón
                }
            }
        }

    }
}

