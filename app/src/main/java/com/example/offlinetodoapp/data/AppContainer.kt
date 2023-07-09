package com.example.offlinetodoapp.data

import android.content.Context

interface AppContainer {
    val tasksRepository: TaskRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val tasksRepository: TaskRepository by lazy {
    OfflineTaskRepository(ToDoAppDatabase.getDatabase(context).taskDao())
    }
}