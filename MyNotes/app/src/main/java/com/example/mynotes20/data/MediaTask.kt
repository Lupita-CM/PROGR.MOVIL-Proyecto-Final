package com.example.mynotes20.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "mediaTasks",
    foreignKeys = [
        ForeignKey(entity = Task::class, parentColumns = ["id"], childColumns = ["taskId"], onDelete = ForeignKey.CASCADE)
    ]
)
data class MediaTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val filePath: String, // Ruta del archivo
    val mediaType: String, // Tipo de medio (imagen, video, audio)
    val taskId: Int // ID de la tarea asociada (puede ser nulo si está asociada a una nota)
)
