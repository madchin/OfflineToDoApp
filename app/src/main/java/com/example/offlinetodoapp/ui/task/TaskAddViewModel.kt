package com.example.offlinetodoapp.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.offlinetodoapp.data.Task
import com.example.offlinetodoapp.data.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TaskAddUiState(
    val title: String = "",
    val description: String = "",
    val areInputsValid: Boolean = true
)

sealed interface Input {
    object TaskTitle : Input

    object TaskDescription : Input
}

class TaskAddViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    private var _uiState = MutableStateFlow(TaskAddUiState())

    val uiState = _uiState.asStateFlow()
    fun onTaskAdd() {
        viewModelScope.launch {
            if (!validateInputs()) {
                _uiState.update {
                    it.copy(
                        areInputsValid = false
                    )
                }
                return@launch
            }
            taskRepository.insertTask(
                Task(
                    title = _uiState.value.title,
                    description = _uiState.value.description
                )
            )
            _uiState.update { TaskAddUiState() }
        }
    }

    fun validateInputs(): Boolean =
        _uiState.value.title.trim().isNotEmpty() || _uiState.value.description.trim().isNotEmpty()

    fun onInputChange(input: Input, value: String) {
        when (input) {
            is Input.TaskTitle -> {
                _uiState.update {
                    it.copy(
                        title = value,
                        areInputsValid = true
                    )
                }
            }

            is Input.TaskDescription -> {
                _uiState.update {
                    it.copy(
                        description = value,
                        areInputsValid = true
                    )
                }
            }
        }
    }

    companion object {
        fun Factory(taskRepository: TaskRepository) = viewModelFactory {
            initializer {
                TaskAddViewModel(taskRepository)
            }
        }
    }
}