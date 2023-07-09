package com.example.offlinetodoapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.offlinetodoapp.data.Task

@Composable
fun HomeScreen(
    onTaskAddButtonClick: () -> Unit,
    tasks: List<Task>?,
    modifier: Modifier = Modifier
) {
    val exampleTasks = listOf(
        Task(
            id = 1,
            title = "Example Task",
            description = "Example description provided for task"
        )
    )
    Column(modifier = modifier.fillMaxSize()) {
        Box {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(tasks ?: exampleTasks, key = { it.id }) {
                    TaskItem(task = it, modifier = Modifier.padding(16.dp))
                }
            }
            AddTaskButton(
                onTaskAddButtonClick = onTaskAddButtonClick,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
fun AddTaskButton(
    onTaskAddButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ExtendedFloatingActionButton(
        onClick = { onTaskAddButtonClick() },
        modifier = modifier
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.AddCircle,
            contentDescription = null,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(text = "Add New Task")
    }
}

@Composable
fun TaskItem(task: Task, modifier: Modifier = Modifier) {
    val title = task.title.capitalize(Locale.current)
    val description = task.description?.capitalize(Locale.current) ?: "Description"
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onTaskAddButtonClick = { /*TODO*/ },
        tasks = listOf(
            Task(
                id = 1,
                title = "Title",
                description = "Description"
            )
        )
    )
}
