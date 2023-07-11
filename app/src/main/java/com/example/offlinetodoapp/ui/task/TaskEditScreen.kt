package com.example.offlinetodoapp.ui.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.offlinetodoapp.utils.InputType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TaskEditViewModel = viewModel(factory = TaskEditViewModel.Factory)
) {
    val uiState: TaskEditUiState = viewModel.uiState
    val elementsModifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    Column(modifier = modifier.fillMaxWidth()) {
        TextField(
            value = uiState.title,
            onValueChange = {
                viewModel.onInputChange(InputType.TaskTitle, it)
            },
            placeholder = { Text(text = "Enter task title") },
            isError = !uiState.areInputsValid,
            modifier = elementsModifier
        )
        OutlinedTextField(
            value = uiState.description,
            onValueChange = {
                viewModel.onInputChange(InputType.TaskDescription, it)
            },
            placeholder = { Text(text = "Enter task description") },
            isError = !uiState.areInputsValid,
            modifier = elementsModifier
        )
        OutlinedButton(
            onClick = {
                viewModel.editTask()
                navigateUp()
            },
            enabled = uiState.areInputsValid,
            modifier = elementsModifier
        ) {
            Text(text = "Edit Task", modifier = Modifier.align(Alignment.CenterVertically))
        }
    }
}
