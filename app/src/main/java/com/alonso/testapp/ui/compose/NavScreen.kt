package com.alonso.testapp.ui.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.alonso.testapp.domain.models.BottomNavRoutes
import com.alonso.testapp.R
import com.alonso.testapp.ui.navigation.mainScreenNavigation
import com.alonso.testapp.ui.navigation.trainingScreenNavigation

const val ANIMATION_DELAY = 500
const val ZERO_DELAY = 0

@Composable
fun NavScreen() {
    val bottomBarRoutes = listOf(
        BottomBarItem(
            label = stringResource(R.string.start),
            route = BottomNavRoutes.Main
        ),
        BottomBarItem(
            label = stringResource(R.string.screen_training),
            route = BottomNavRoutes.Trainings
        )
    )

    val navController = rememberNavController()

    val bottomNavRoutes = BottomNavRoutes.entries.map { it.name }
    val showBottomBar = remember { mutableStateOf(true) }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    showBottomBar.value = currentRoute in bottomNavRoutes || currentRoute.isNullOrEmpty()

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar.value,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = ANIMATION_DELAY)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = ZERO_DELAY)
                )
            ) {
                Column {
                    HorizontalDivider()
                    NavigationBar {
                        val currentDestination = backStackEntry?.destination
                        bottomBarRoutes.forEach { bottomBarRoute ->
                            NavigationBarItem(
                                icon = { },
                                label = { Text(bottomBarRoute.label) },
                                selected = currentDestination?.route == bottomBarRoute.route.name,
                                onClick = {
                                    navController.navigate(bottomBarRoute.route.name) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavRoutes.Main.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            mainScreenNavigation(navController = navController)
            trainingScreenNavigation(navController = navController)
        }
    }
}
data class BottomBarItem(
    val label: String,
    val route: BottomNavRoutes
)