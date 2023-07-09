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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddScreen(
    onInputChange: (Input, String) -> Unit,
    title: String,
    description: String,
    areInputsValid: Boolean,
    onTaskAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    val elementsModifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    Column(modifier = modifier.fillMaxWidth()) {
        TextField(
            value = title,
            onValueChange = {
                onInputChange(Input.TaskTitle, it)
            },
            placeholder = { Text(text = "Enter task title") },
            isError = !areInputsValid,
            modifier = elementsModifier
        )
        OutlinedTextField(
            value = description,
            onValueChange = {
                onInputChange(Input.TaskDescription, it)
            },
            placeholder = { Text(text = "Enter task description") },
            isError = !areInputsValid,
            modifier = elementsModifier
        )
        OutlinedButton(
            onClick = onTaskAdd,
            enabled = areInputsValid,
            modifier = elementsModifier
        ) {
            Text(text = "Add Task", modifier = Modifier.align(Alignment.CenterVertically))
        }
    }
}
