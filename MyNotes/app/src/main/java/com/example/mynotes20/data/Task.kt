package com.example.mynotes20.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val dateComplete: Long,
    val dateCreate: Long,
    val time: String,
    val repeat: Boolean,
    val repeatFrecuency: String,
    val duration: String,
    val complete: Boolean
)


