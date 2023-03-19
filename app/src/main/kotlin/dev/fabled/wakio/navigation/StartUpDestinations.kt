package dev.fabled.wakio.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import dev.fabled.navigation.navigation_directions.StartUpDirections
import dev.fabled.on_boarding.screens.OnBoardingScreen

@ExperimentalAnimationApi
fun NavGraphBuilder.startUpGraph() {
    navigation(
        route = StartUpDirections.START_UP_DIRECTION,
        startDestination = StartUpDirections.OnBoarding.route()
    ) {
        composable(route = StartUpDirections.OnBoarding.route()) {
            OnBoardingScreen(onBoardingViewModel = hiltViewModel())
        }
    }
}