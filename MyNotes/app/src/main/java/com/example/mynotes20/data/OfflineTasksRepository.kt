package com.example.mynotes20.data

import kotlinx.coroutines.flow.Flow

class OfflineTasksRepository (private val TaskDao: TaskDao) : TasksRepository{
    override fun getAllTasksStream(): Flow<List<Task>> = TaskDao.getAllTasks()

    override fun getTaskStream(id: Int): Flow<Task?> = TaskDao.getTasks(id)

    override suspend fun insertTask(task: Task):Long = TaskDao.insert(task)

    override suspend fun deleteTask(task: Task) = TaskDao.delete(task)

    override suspend fun updateTask(task: Task) = TaskDao.update(task)
}