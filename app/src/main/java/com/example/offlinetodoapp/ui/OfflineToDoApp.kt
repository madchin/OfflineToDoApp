package com.example.offlinetodoapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.offlinetodoapp.data.OfflineTaskRepository
import com.example.offlinetodoapp.ui.home.HomeUiState
import com.example.offlinetodoapp.ui.home.HomeViewModel
import com.example.offlinetodoapp.ui.navigation.NavigationHostController
import com.example.offlinetodoapp.ui.task.TaskAddUiState
import com.example.offlinetodoapp.ui.task.TaskAddViewModel

@Composable
fun OfflineToDoApp(taskRepository: OfflineTaskRepository) {
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory(taskRepository))
    val homeUiState: HomeUiState by homeViewModel.uiState.collectAsState()
    val taskAddViewModel: TaskAddViewModel = viewModel(factory = TaskAddViewModel.Factory(taskRepository))
    val taskAddUiState: TaskAddUiState by taskAddViewModel.uiState.collectAsState()
    NavigationHostController(
        homeUiState = homeUiState,
        taskAddUiState = taskAddUiState,
        taskAddViewModel = taskAddViewModel
    )
}