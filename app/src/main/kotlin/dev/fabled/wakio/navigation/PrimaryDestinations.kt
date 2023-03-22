package dev.fabled.wakio.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import dev.fabled.alarm.screens.AlarmEditScreen
import dev.fabled.alarm.screens.AlarmSoundScreen
import dev.fabled.alarm.screens.AlarmsScreen
import dev.fabled.home.screens.ArticlesScreen
import dev.fabled.home.screens.HomeScreen
import dev.fabled.home.screens.NotificationsScreen
import dev.fabled.navigation.navigation_directions.ActivityDirections
import dev.fabled.navigation.navigation_directions.AlarmDirections
import dev.fabled.navigation.navigation_directions.HomeDirections
import dev.fabled.navigation.navigation_directions.ProfileDirections

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeGraph(viewModelStoreOwner: ViewModelStoreOwner) {
    navigation(
        route = HomeDirections.HOME_DIRECTION,
        startDestination = HomeDirections.HomeScreen.route()
    ) {
        composable(
            route = HomeDirections.HomeScreen.route(),
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 200)) }
        ) {
            HomeScreen(
                modifier = Modifier
                    .systemBarsPadding()
                    .padding(bottom = 80.dp)
                    .fillMaxSize(),
                homeViewModel = hiltViewModel(viewModelStoreOwner)
            )
        }
        composable(
            route = HomeDirections.NotificationsScreen.route(),
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(durationMillis = 500),
                    initialOffsetX = { it / 2 }
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            },
            popExitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(durationMillis = 500),
                    targetOffsetX = { it / 2 }
                ) + fadeOut(animationSpec = tween(durationMillis = 200))
            }
        ) {
            NotificationsScreen(
                modifier = Modifier
                    .systemBarsPadding()
                    .fillMaxSize(),
                homeViewModel = hiltViewModel(viewModelStoreOwner)
            )
        }
        composable(
            route = HomeDirections.ArticlesScreen.route(),
            arguments = HomeDirections.ArticlesScreen.arguments
        ) { navBackStackEntry ->
            val articleUrl = navBackStackEntry
                .arguments
                ?.getString(HomeDirections.ArticlesScreen.ARTICLE_URL)
                .orEmpty()

            ArticlesScreen(
                modifier = Modifier
                    .systemBarsPadding()
                    .fillMaxSize(),
                targetUrl = articleUrl
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.activityGraph(viewModelStoreOwner: ViewModelStoreOwner) {
    navigation(
        route = ActivityDirections.ACTIVITY_DIRECTION,
        startDestination = ActivityDirections.ActivityScreen.route()
    ) {
        composable(route = ActivityDirections.ActivityScreen.route()) {
            Box(modifier = Modifier.fillMaxSize())
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.alarmGraph(viewModelStoreOwner: ViewModelStoreOwner) {
    navigation(
        route = AlarmDirections.ALARM_DIRECTION,
        startDestination = AlarmDirections.AlarmsScreen.route()
    ) {
        composable(route = AlarmDirections.AlarmsScreen.route()) {
            AlarmsScreen(
                modifier = Modifier
                    .systemBarsPadding()
                    .padding(bottom = 80.dp)
                    .fillMaxSize(),
                alarmViewModel = hiltViewModel(viewModelStoreOwner)
            )
        }
        composable(
            route = AlarmDirections.AlarmEditScreen.route(),
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 250))
            }
        ) {
            AlarmEditScreen(
                modifier = Modifier
                    .systemBarsPadding()
                    .fillMaxSize(),
                alarmViewModel = hiltViewModel(viewModelStoreOwner)
            )
        }
        composable(
            route = AlarmDirections.AlarmSoundScreen.route(),
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(durationMillis = 500),
                    initialOffsetX = { it / 2 }
                ) + fadeIn(
                    animationSpec = tween(durationMillis = 500)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(durationMillis = 500),
                    targetOffsetX = { it }
                ) + fadeOut(
                    animationSpec = tween(durationMillis = 500)
                )
            },
        ) {
            AlarmSoundScreen(
                modifier = Modifier
                    .systemBarsPadding()
                    .fillMaxSize(),
                alarmViewModel = hiltViewModel(viewModelStoreOwner)
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.profileDirections(viewModelStoreOwner: ViewModelStoreOwner) {
    navigation(
        route = ProfileDirections.PROFILE_DIRECTION,
        startDestination = ProfileDirections.ProfileScreen.route()
    ) {
        composable(route = ProfileDirections.ProfileScreen.route()) {
            Box(modifier = Modifier.fillMaxSize())
        }
    }
}