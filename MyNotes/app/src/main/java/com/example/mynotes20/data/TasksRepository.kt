package com.example.mynotes20.data

import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllTasksStream(): Flow<List<Task>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getTaskStream(id: Int): Flow<Task?>

    /**
     * Insert item in the data source
     */
    suspend fun insertTask(task: Task): Long

    /**
     * Delete item from the data source
     */
    suspend fun deleteTask(task: Task)

    /**
     * Update item in the data source
     */
    suspend fun updateTask(task: Task)
}