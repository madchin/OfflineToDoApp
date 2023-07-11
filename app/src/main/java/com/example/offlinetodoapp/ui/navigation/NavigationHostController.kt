package com.example.offlinetodoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.offlinetodoapp.ui.home.HomeScreen
import com.example.offlinetodoapp.ui.task.TaskAddScreen
import com.example.offlinetodoapp.ui.task.TaskEditScreen

@Composable
fun NavigationHostController(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.HOME.name
    ) {
        composable(route = NavigationDestination.HOME.name) {
            HomeScreen(
                onTaskAddButtonClick = { navController.navigate(NavigationDestination.TASK_ADD.name) },
                onTaskEditButtonClick = {
                    navController.navigate("${NavigationDestination.TASK_EDIT.name}/$it")
                }
            )
        }
        composable(route = NavigationDestination.TASK_ADD.name) {
            TaskAddScreen(navigateUp = navController::navigateUp)
        }
        composable(
            route = "${NavigationDestination.TASK_EDIT.name}/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) {
            TaskEditScreen(navigateUp = navController::navigateUp)
        }
    }
}