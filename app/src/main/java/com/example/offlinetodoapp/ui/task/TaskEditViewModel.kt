package com.example.offlinetodoapp.ui.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.offlinetodoapp.AppContainer
import com.example.offlinetodoapp.data.Task
import com.example.offlinetodoapp.data.TaskRepository
import com.example.offlinetodoapp.utils.InputType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class TaskEditUiState(
    val title: String = "",
    val description: String = "",
    val areInputsValid: Boolean = true
)

fun Task.toTaskEditUiState(): TaskEditUiState =
    TaskEditUiState(this.title, this.description, areInputsValid = true)

fun TaskEditUiState.toTask(id: Int): Task =
    Task(this.title, this.description, id = id)

class TaskEditViewModel(
    savedStateHandle: SavedStateHandle = SavedStateHandle(mapOf("taskId" to 1)),
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val taskId: Int = checkNotNull(savedStateHandle["taskId"])
    var uiState by mutableStateOf(TaskEditUiState())
        private set

    init {
        viewModelScope.launch {
            uiState = taskRepository
                .getTaskStream(taskId)
                .map { it.toTaskEditUiState() }
                .first()
        }
    }

    fun onTaskEdit() {
        viewModelScope.launch {
            if (!validateInputs()) {
                uiState = uiState.copy(
                    areInputsValid = false
                )
                return@launch
            }
            taskRepository.updateTask(uiState.toTask(id = taskId))
            uiState = TaskEditUiState()
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
                TaskEditViewModel(
                    this.createSavedStateHandle(),
                    taskRepository = AppContainer.taskRepository
                )
            }
        }
    }
}