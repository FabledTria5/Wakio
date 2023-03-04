package dev.fabled.authorization.screens.authorization

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dev.fabled.authorization.AuthorizationViewModel
import dev.fabled.authorization.R
import dev.fabled.authorization.components.AuthorizationIndication
import dev.fabled.authorization.screens.authorization.pages.SignInPage
import dev.fabled.authorization.screens.authorization.pages.SignUpPage
import dev.fabled.authorization.util.TestTags
import dev.fabled.common.ui.theme.Oxygen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AuthorizationScreen(
    modifier: Modifier = Modifier,
    authorizationViewModel: AuthorizationViewModel
) {
    val authorizationState by authorizationViewModel.authorizationState.collectAsStateWithLifecycle()
    val signInState by authorizationViewModel.signInAuthorizationState.collectAsStateWithLifecycle()
    val signUpState by authorizationViewModel.signUpAuthorizationState.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    var isLoading by remember { mutableStateOf(value = false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {
        HorizontalPager(
            modifier = modifier,
            count = 2,
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> SignInPage(
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 20.dp)
                        .fillMaxSize()
                        .testTag(tag = TestTags.SIGN_IN_PAGE),
                    signInState = signInState,
                    onFormEvent = authorizationViewModel::onSignInEvent,
                    onSignInClicked = authorizationViewModel::signIn,
                    onSignUpClicked = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page = 1)
                        }
                    },
                    enabled = !isLoading
                )

                1 -> SignUpPage(
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 20.dp)
                        .fillMaxSize()
                        .testTag(tag = TestTags.SIGN_UP_PAGE),
                    signUpState = signUpState,
                    onFormEvent = authorizationViewModel::onSignUpEvent,
                    onSignUpClicked = authorizationViewModel::onSignUpClicked,
                    onSignInClicked = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page = 0)
                        }
                    },
                    enabled = !isLoading
                )
            }
        }

        AuthorizationIndication(
            modifier = Modifier.fillMaxSize(),
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
internal fun WelcomeMessage(modifier: Modifier = Modifier, message: String) {
    Column(
        modifier = modifier.padding(bottom = 44.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.hi_there),
            color = Color.White,
            fontFamily = Oxygen,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = message,
            color = Color.White,
            fontFamily = Oxygen,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            minLines = 2
        )
    }
}