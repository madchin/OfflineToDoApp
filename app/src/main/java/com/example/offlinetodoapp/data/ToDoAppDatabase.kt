package com.example.offlinetodoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class ToDoAppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var Instance: ToDoAppDatabase? = null

        fun getDatabase(context: Context): ToDoAppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ToDoAppDatabase::class.java, "item_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}