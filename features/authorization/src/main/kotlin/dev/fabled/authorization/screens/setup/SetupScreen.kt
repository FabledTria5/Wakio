package dev.fabled.authorization.screens.setup

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.fabled.authorization.AuthorizationViewModel
import dev.fabled.authorization.components.AuthorizationIndication
import dev.fabled.authorization.screens.setup.pages.GenderPage
import dev.fabled.authorization.screens.setup.pages.ProfileBackgroundScreen
import dev.fabled.common.ui.theme.Oxygen
import dev.fabled.common.ui.theme.PrimaryMuted
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SetupScreen(modifier: Modifier = Modifier, authorizationViewModel: AuthorizationViewModel) {
    val authorizationState by authorizationViewModel.authorizationState.collectAsStateWithLifecycle()
    val setupState by authorizationViewModel.setupState.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    var isLoading by remember { mutableStateOf(value = false) }

    Scaffold(modifier = modifier, snackbarHost = { SnackbarHost(snackBarHostState) }) {
        Column(modifier = modifier.padding(horizontal = 15.dp, vertical = 20.dp)) {
            SetupTopBar()
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                pageCount = 2,
                state = pagerState,
                userScrollEnabled = false,
                pageSpacing = 15.dp
            ) { page ->
                when (page) {
                    0 -> GenderPage(
                        selectedGenderModel = setupState.genderModel,
                        onGenderSelected = authorizationViewModel::updateGender,
                        onNextClicked = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page = 1)
                            }
                        }
                    )

                    1 -> ProfileBackgroundScreen(
                        selectedBackgroundModel = setupState.profileBackgroundModel,
                        onBackgroundSelected = authorizationViewModel::updateProfileBackground,
                        onFinishClicked = authorizationViewModel::finishSignUp
                    )
                }
            }
        }

        AuthorizationIndication(
            authorizationState = authorizationState,
            isLoading = isLoading,
            onLoadStateChange = { isLoading = it },
            onError = { errorMessage ->
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(message = errorMessage)
                }
            }
        )
    }
}

@Composable
fun SetupTopBar() {
    Text(
        text = buildAnnotatedString {
            append(
                text = AnnotatedString(
                    text = "Welcome! ",
                    spanStyle = SpanStyle(color = Color.White, fontSize = 32.sp)
                )
            )
            append(
                text = AnnotatedString(
                    text = "Set ",
                    spanStyle = SpanStyle(color = Color.White, fontSize = 28.sp)
                )
            )
            append(
                text = AnnotatedString(
                    text = "Wakio\n",
                    spanStyle = SpanStyle(color = PrimaryMuted, fontSize = 32.sp)
                )
            )
            append(
                text = AnnotatedString(
                    text = "to achieve your morning goals",
                    spanStyle = SpanStyle(color = Color.White, fontSize = 28.sp)
                )
            )
        },
        fontWeight = FontWeight.Bold,
        fontFamily = Oxygen,
        lineHeight = 40.sp
    )
}