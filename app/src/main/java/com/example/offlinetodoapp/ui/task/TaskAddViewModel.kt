package com.example.offlinetodoapp.ui.task

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
import com.example.offlinetodoapp.utils.InputType
import kotlinx.coroutines.launch

data class TaskAddUiState(
    val title: String = "",
    val description: String = "",
    val areInputsValid: Boolean = true
)

fun TaskAddUiState.toTask() = Task(this.title, this.description)

class TaskAddViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    var uiState by mutableStateOf(TaskAddUiState())
        private set

    fun onTaskAdd() {
        viewModelScope.launch {
            if (!validateInputs()) {
                uiState = uiState.copy(
                    areInputsValid = false
                )

                return@launch
            }
            taskRepository.insertTask(uiState.toTask())
            uiState = TaskAddUiState()
        }
    }

    fun validateInputs(): Boolean =
        uiState.title.trim().isNotEmpty() || uiState.description.trim().isNotEmpty()

    fun onInputChange(input: InputType, value: String) {
        when (input) {
            is InputType.TaskTitle -> {
                uiState = uiState.copy(
                    title = value,
                    areInputsValid = true
                )
            }

            is InputType.TaskDescription -> {
                uiState = uiState.copy(
                    description = value,
                    areInputsValid = true
                )
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                TaskAddViewModel(AppContainer.taskRepository)
            }
        }
    }
}