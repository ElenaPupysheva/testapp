package com.alonso.testapp.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.alonso.testapp.domain.models.BottomNavRoutes
import com.alonso.testapp.ui.compose.TrainingScreen
private const val DELAY = 500
fun NavGraphBuilder.trainingScreenNavigation(navController: NavHostController)  {
    composable(
        route = BottomNavRoutes.Trainings.name,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(DELAY)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(DELAY)
            )
        }
    ) {
        TrainingScreen(navController = navController)
    }
}