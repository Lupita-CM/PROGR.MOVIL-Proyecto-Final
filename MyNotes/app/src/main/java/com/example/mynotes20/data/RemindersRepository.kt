package com.example.mynotes20.data

import kotlinx.coroutines.flow.Flow

interface RemindersRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllRemindersStream(): Flow<List<Reminders>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getReminderStream(id: Int): Flow<Reminders?>

    /**
     * Insert item in the data source
     */
    suspend fun insertReminder(reminders: Reminders)

    /**
     * Delete item from the data source
     */
    suspend fun deleteReminder(reminders: Reminders)

    /**
     * Update item in the data source
     */
    suspend fun updateReminder(reminders: Reminders)
}