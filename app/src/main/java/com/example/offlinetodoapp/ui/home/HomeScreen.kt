package com.example.offlinetodoapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.offlinetodoapp.data.Task
import com.example.offlinetodoapp.ui.components.AppDialog

@Composable
fun HomeScreen(
    onTaskAddButtonClick: () -> Unit,
    onTaskEditButtonClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    val uiState: HomeUiState = viewModel.uiState

    Column(modifier = modifier.fillMaxSize()) {
        Box {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(uiState.tasks, key = { it.id }) {
                    TaskItem(
                        task = it,
                        modifier = Modifier.padding(16.dp),
                        onTaskEditButtonClick = {
                            onTaskEditButtonClick(it.id)
                        },
                        onTaskDeleteButtonClick = {
                            viewModel.toggleDialog(it)
                        }
                    )
                }
            }
            AddTaskButton(
                onTaskAddButtonClick = onTaskAddButtonClick,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
            if (uiState.isDeleteDialogShown) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppDialog(
                        title = "Delete task",
                        description = "Are you sure you want to delete this task? Action is irreversible.",
                        onDismiss = { viewModel.toggleDialog() },
                        onAccept = { viewModel.deleteTask() }
                    )
                }
            }
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
fun TaskItem(
    task: Task,
    onTaskEditButtonClick: () -> Unit,
    onTaskDeleteButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val title = task.title.capitalize(Locale.current)
    val description = task.description.capitalize(Locale.current)
    var showMenu by rememberSaveable { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )
                Text(
                    text = description,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        TaskMenu(
            isShown = showMenu,
            onMenuToggle = { showMenu = !showMenu }
        ) {
            MenuItem(
                icon = Icons.Default.Edit,
                itemName = "Edit",
                contentDescription = "Menu item edit task",
                onMenuItemClick = {
                    showMenu = !showMenu
                    onTaskEditButtonClick()
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            MenuItem(
                icon = Icons.Default.Delete,
                itemName = "Delete",
                contentDescription = "Menu item delete task",
                onMenuItemClick = {
                    showMenu = !showMenu
                    onTaskDeleteButtonClick()
                }
            )
        }
    }
}

@Composable
fun MenuItem(
    icon: ImageVector,
    itemName: String,
    onMenuItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String = "",
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onMenuItemClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier
        )
        Text(
            text = itemName,
            modifier = Modifier
                .padding(start = 8.dp)
        )
    }
}

@Composable
fun TaskMenu(
    modifier: Modifier = Modifier,
    isShown: Boolean,
    onMenuToggle: () -> Unit,
    taskMenuItems: @Composable ColumnScope.() -> Unit,
) {
    Row {
        if (isShown) {
            Column(
                modifier = Modifier
                    .clip(shape = MaterialTheme.shapes.medium)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = MaterialTheme.shapes.medium
                    )
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                taskMenuItems()
            }
        }
        OutlinedIconButton(
            onClick = onMenuToggle,
            modifier = modifier
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Task Menu Button",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onTaskAddButtonClick = {},
        onTaskEditButtonClick = {},
    )
}