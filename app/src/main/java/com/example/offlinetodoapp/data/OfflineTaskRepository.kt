package com.example.offlinetodoapp.data

import kotlinx.coroutines.flow.Flow

class OfflineTaskRepository(private val taskDao: TaskDao) : TaskRepository {
    override suspend fun deleteTask(task: Task) = taskDao.delete(task)

    override fun getAllTasksStream(): Flow<List<Task>> = taskDao.getAll()

    override fun getTaskStream(id: Int): Flow<Task> = taskDao.getTask(id)

    override suspend fun insertTask(task: Task) = taskDao.insert(task)

    override suspend fun updateTask(task: Task) = taskDao.update(task)
}