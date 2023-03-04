package dev.fabled.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.fabled.common.ui.theme.Oxygen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(splashViewModel: SplashViewModel) {
    LaunchedEffect(key1 = splashViewModel) {
        delay(timeMillis = 500)
        splashViewModel.navigateForward()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.splash_background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(vertical = 120.dp),
            text = stringResource(id = dev.fabled.common.R.string.app_name),
            color = Color.White,
            fontFamily = Oxygen,
            fontWeight = FontWeight.Bold,
            fontSize = 56.sp
        )
    }
}