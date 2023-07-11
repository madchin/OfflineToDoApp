package com.example.offlinetodoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.offlinetodoapp.data.OfflineTaskRepository
import com.example.offlinetodoapp.data.ToDoAppDatabase
import com.example.offlinetodoapp.ui.OfflineToDoApp
import com.example.offlinetodoapp.ui.theme.OfflineToDoAppTheme

object AppContainer {
    lateinit var taskRepository: OfflineTaskRepository
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val db = Room.databaseBuilder(
            applicationContext,
            ToDoAppDatabase::class.java, "todoapp_database"
        )
            .fallbackToDestructiveMigration()
            .build()
        AppContainer.taskRepository = OfflineTaskRepository(db.taskDao)

        super.onCreate(savedInstanceState)
        setContent {
            OfflineToDoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OfflineToDoApp()
                }
            }
        }
    }
}
