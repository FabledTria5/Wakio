package dev.fabled.authorization.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.fabled.common.ui.theme.PrimaryDark
import dev.fabled.domain.model.Resource

@Composable
fun AuthorizationIndication(
    modifier: Modifier = Modifier,
    authorizationState: Resource<Any>,
    isLoading: Boolean,
    onLoadStateChange: (isLoading: Boolean) -> Unit,
    onError: (errorMessage: String) -> Unit
) {
    LaunchedEffect(key1 = authorizationState) {
        when (authorizationState) {
            is Resource.Error -> {
                onLoadStateChange(false)
                onError(authorizationState.error.errorMessage)
            }

            Resource.Loading -> onLoadStateChange(true)
            else -> onLoadStateChange(false)
        }
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = isLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = modifier.background(Color.Black.copy(alpha = .5f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.size(35.dp), color = PrimaryDark)
        }
    }
}