package com.example.offlinetodoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.offlinetodoapp.OfflineToDoApplication
import com.example.offlinetodoapp.data.Task
import com.example.offlinetodoapp.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiState: StateFlow<HomeUiState> = taskRepository
        .getAllTasksStream()
        .map { HomeUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )
    val uiState = _uiState


    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(OfflineToDoApplication().container.tasksRepository)
        }
    }
}

data class HomeUiState(
    val tasks: List<Task> = listOf()
)