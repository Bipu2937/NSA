package com.nsa.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nsa.app.data.repository.NotificationRepository
import com.nsa.app.ui.detail.NotificationDetailScreen
import com.nsa.app.ui.list.NotificationListScreen
import com.nsa.app.ui.settings.SettingsScreen

sealed class Screen(val route: String) {
    object List : Screen("list")
    object Detail : Screen("detail/{notificationId}") {
        fun createRoute(notificationId: Long) = "detail/$notificationId"
    }
    object Settings : Screen("settings")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    repository: NotificationRepository
) {
    NavHost(
        navController = navController,
        startDestination = Screen.List.route
    ) {
        composable(Screen.List.route) {
            NotificationListScreen(
                navController = navController,
                repository = repository
            )
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("notificationId") { type = NavType.LongType })
        ) { backStackEntry ->
            val notificationId = backStackEntry.arguments?.getLong("notificationId") ?: return@composable
            NotificationDetailScreen(
                navController = navController,
                repository = repository,
                notificationId = notificationId
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                navController = navController,
                repository = repository
            )
        }
    }
}
