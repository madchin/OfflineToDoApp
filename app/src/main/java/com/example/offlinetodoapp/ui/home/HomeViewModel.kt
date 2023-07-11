package com.example.offlinetodoapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.offlinetodoapp.AppContainer
import com.example.offlinetodoapp.data.Task
import com.example.offlinetodoapp.data.TaskRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        viewModelScope.launch {
            taskRepository
                .getAllTasksStream()
                .collect {
                    uiState = uiState.copy(tasks = it)
                }
        }
    }

    fun toggleDialog(task: Task = Task()) {
        val isShown = uiState.isDeleteDialogShown
        uiState = uiState.copy(
            isDeleteDialogShown = !isShown,
            taskToDelete = task
        )
    }

    fun deleteTask() {
        viewModelScope.launch {
            taskRepository.deleteTask(uiState.taskToDelete)
            uiState = uiState.copy(isDeleteDialogShown = false)
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                HomeViewModel(AppContainer.taskRepository)
            }
        }
    }
}

data class HomeUiState(
    val tasks: List<Task> = listOf(),
    val isDeleteDialogShown: Boolean = false,
    val taskToDelete: Task = Task()
)