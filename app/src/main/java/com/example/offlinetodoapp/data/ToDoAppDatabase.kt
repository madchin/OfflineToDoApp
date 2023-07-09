package com.example.offlinetodoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 2, exportSchema = false)
abstract class ToDoAppDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
}