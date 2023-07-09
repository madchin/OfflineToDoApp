package com.example.offlinetodoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.offlinetodoapp.ui.home.HomeScreen
import com.example.offlinetodoapp.ui.home.HomeUiState
import com.example.offlinetodoapp.ui.task.TaskAddScreen
import com.example.offlinetodoapp.ui.task.TaskAddUiState
import com.example.offlinetodoapp.ui.task.TaskAddViewModel

@Composable
fun NavigationHostController(
    navController: NavHostController = rememberNavController(),
    homeUiState: HomeUiState,
    taskAddUiState: TaskAddUiState,
    taskAddViewModel: TaskAddViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.HOME.name
    ) {
        composable(NavigationDestination.HOME.name) {
            HomeScreen(
                onTaskAddButtonClick = { navController.navigate(NavigationDestination.TASK_ADD.name) },
                tasks = homeUiState.tasks
            )
        }
        composable(NavigationDestination.TASK_ADD.name) {
            TaskAddScreen(
                onInputChange = taskAddViewModel::onInputChange,
                title = taskAddUiState.title,
                description = taskAddUiState.description,
                areInputsValid = taskAddUiState.areInputsValid,
                onTaskAdd = {
                    taskAddViewModel.onTaskAdd()
                    if (taskAddViewModel.validateInputs()) {
                        navController.navigateUp()
                    }
                }
            )
        }
        composable(NavigationDestination.TASK_DELETE.name) {

        }
        composable(NavigationDestination.TASK_EDIT.name) {

        }
    }
}