package dev.fabled.wakio.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dev.fabled.navigation.navigation_core.Navigator
import dev.fabled.navigation.navigation_core.NavigatorEvent
import dev.fabled.navigation.navigation_directions.BottomNavigationDestinations
import dev.fabled.navigation.navigation_directions.StartUpDirections
import dev.fabled.wakio.navigation.authorizationGraph
import dev.fabled.wakio.navigation.primaryGraph
import dev.fabled.wakio.navigation.startUpGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SetupNavigation(navigator: Navigator) {
    val navHostController = rememberAnimatedNavController()
    val currentBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestinationRoute =
        currentBackStackEntry?.destination?.route ?: StartUpDirections.SplashScreen.route()

    val navigationDestination = navigator.destinations

    LaunchedEffect(key1 = navHostController) {
        navigationDestination.collect {
            when (val event = it) {
                NavigatorEvent.NavigateUp -> navHostController.popBackStack()
                is NavigatorEvent.Direction -> navHostController.navigate(
                    route = event.destination,
                    builder = event.builder
                )
            }
        }
    }

    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentDestination = currentDestinationRoute,
                navHostController = navHostController
            )
        }
    ) {
        AnimatedNavHost(
            navController = navHostController,
            startDestination = "start_up"
        ) {
            startUpGraph()
            authorizationGraph(viewModelStoreOwner = viewModelStoreOwner)
            primaryGraph(viewModelStoreOwner = viewModelStoreOwner)
        }
    }
}

@Composable
fun BottomNavigationBar(
    currentDestination: String,
    navHostController: NavHostController,
    navigationItems: List<BottomNavigationDestinations> = remember {
        BottomNavigationDestinations.getDestinations()
    }
) {
    val primaryDirections = BottomNavigationDestinations.getDestinations()

    val isVisible by remember(currentDestination) {
        derivedStateOf {
            primaryDirections.any { it.direction.route() == currentDestination }
        }
    }

    AnimatedVisibility(visible = isVisible, enter = fadeIn(), exit = fadeOut()) {
        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.background
        ) {
            navigationItems.forEach { item ->
                NavigationBarItem(
                    selected = item.direction.route() == currentDestination,
                    onClick = {
                        navHostController.navigate(item.direction.route())
                    },
                    icon = {
                        Icon(
                            painter = if (item.direction.route() == currentDestination)
                                painterResource(item.selectedIcon)
                            else
                                painterResource(item.unselectedIcon),
                            contentDescription = item.direction.route()
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Unspecified,
                        unselectedIconColor = Color.Unspecified,
                        indicatorColor = MaterialTheme.colorScheme.background,
                    )
                )
            }
        }
    }
}