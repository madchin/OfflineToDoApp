package com.example.offlinetodoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.offlinetodoapp.ui.home.HomeScreen
import com.example.offlinetodoapp.ui.task.TaskAddScreen

@Composable
fun NavigationHostController(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.HOME.name
    ) {
        composable(NavigationDestination.HOME.name) {
            HomeScreen(
                onTaskAddButtonClick = { navController.navigate(NavigationDestination.TASK_ADD.name) },
                tasks = null
            )
        }
        composable(NavigationDestination.TASK_ADD.name) {
            TaskAddScreen()
        }
        composable(NavigationDestination.TASK_DELETE.name) {

        }
        composable(NavigationDestination.TASK_EDIT.name) {

        }
    }
}