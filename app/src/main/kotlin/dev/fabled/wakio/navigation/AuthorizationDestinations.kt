package dev.fabled.wakio.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.google.accompanist.navigation.animation.composable
import dev.fabled.authorization.screens.authorization.AuthorizationScreen
import dev.fabled.authorization.screens.setup.SetupScreen
import dev.fabled.navigation.navigation_directions.AuthorizationDirections

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.authorizationGraph(viewModelStoreOwner: ViewModelStoreOwner) {
    navigation(
        route = AuthorizationDirections.AUTHORIZATION_DIRECTION,
        startDestination = AuthorizationDirections.AuthorizationScreen.route()
    ) {
        composable(route = AuthorizationDirections.AuthorizationScreen.route()) {
            AuthorizationScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
                authorizationViewModel = hiltViewModel(viewModelStoreOwner)
            )
        }
        composable(route = AuthorizationDirections.SetupScreen.route()) {
            SetupScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
                authorizationViewModel = hiltViewModel(viewModelStoreOwner)
            )
        }
    }
}