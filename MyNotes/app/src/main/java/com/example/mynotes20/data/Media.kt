package com.example.mynotes20.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "media",
    foreignKeys = [
        ForeignKey(entity = Note::class, parentColumns = ["id"], childColumns = ["noteId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Task::class, parentColumns = ["id"], childColumns = ["taskId"], onDelete = ForeignKey.CASCADE)
    ]
)
data class Media(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val filePath: String, // Ruta del archivo
    val mediaType: String, // Tipo de medio (imagen, video, audio)
    val noteId: Long? = null, // ID de la nota asociada (puede ser nulo si está asociada a una tarea)
    val taskId: Long? = null // ID de la tarea asociada (puede ser nulo si está asociada a una nota)
)
