package dev.fabled.authorization.screens.authorization.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import dev.fabled.authorization.R
import dev.fabled.authorization.components.EmailTextField
import dev.fabled.authorization.components.PasswordTextField
import dev.fabled.authorization.model.AuthorizationEvent
import dev.fabled.authorization.model.AuthorizationState
import dev.fabled.authorization.screens.authorization.WelcomeMessage
import dev.fabled.authorization.util.TestTags
import dev.fabled.common.ui.components.GradientButton
import dev.fabled.common.ui.theme.DarkOrange
import dev.fabled.common.ui.theme.Oxygen
import dev.fabled.common.ui.theme.PrimaryGradient

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun SignInPage(
    modifier: Modifier = Modifier,
    signInState: AuthorizationState,
    onFormEvent: (AuthorizationEvent) -> Unit,
    onSignInClicked: () -> Unit,
    onSignUpClicked: () -> Unit,
    enabled: Boolean
) {
    val onEmailChanged: (String) -> Unit = remember {
        { email ->
            onFormEvent(AuthorizationEvent.EmailChanged(email))
        }
    }

    val onPasswordChanged: (String) -> Unit = remember {
        { password ->
            onFormEvent(AuthorizationEvent.PasswordChanged(password))
        }
    }

    ConstraintLayout(modifier = modifier) {
        val (welcomeMessage, inputColumn, signInButton, signUpButton) = createRefs()

        WelcomeMessage(
            modifier = Modifier.constrainAs(welcomeMessage) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)

                width = Dimension.percent(.9f)
            },
            message = stringResource(id = R.string.sign_in_message)
        )
        Column(
            modifier = Modifier.constrainAs(inputColumn) {
                start.linkTo(parent.start)
                top.linkTo(welcomeMessage.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(signInButton.top)
            }
        ) {
            EmailTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(tag = TestTags.EMAIL_TEXT_FIELD),
                email = signInState.email,
                onEmailChanged = onEmailChanged,
                enabled = enabled
            )
            AnimatedVisibility(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .align(Alignment.End),
                visible = signInState.emailError != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    modifier = Modifier.testTag(TestTags.EMAIL_ERROR_TEXT),
                    text = signInState.emailError.orEmpty(),
                    color = DarkOrange,
                    fontFamily = Oxygen
                )
            }
            PasswordTextField(
                modifier = Modifier
                    .padding(top = 25.dp)
                    .fillMaxWidth()
                    .testTag(tag = TestTags.PASSWORD_TEXT_FIELD),
                password = signInState.password,
                onPasswordChanged = onPasswordChanged,
                enabled = enabled
            )
            AnimatedVisibility(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .align(Alignment.End),
                visible = signInState.passwordError != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    modifier = Modifier.testTag(TestTags.PASSWORD_ERROR_TEXT),
                    text = signInState.passwordError.orEmpty(),
                    color = DarkOrange,
                    fontFamily = Oxygen
                )
            }
        }
        GradientButton(
            modifier = Modifier
                .padding(bottom = 150.dp)
                .constrainAs(signInButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(signUpButton.top)
                }
                .fillMaxWidth()
                .testTag(TestTags.SIGN_IN_BUTTON),
            gradient = PrimaryGradient,
            shape = RoundedCornerShape(8.dp),
            onClick = onSignInClicked,
            enabled = enabled
        ) {
            Text(
                modifier = Modifier.padding(vertical = 15.dp),
                text = stringResource(R.string.sign_in),
                fontFamily = Oxygen
            )
        }
        Row(
            modifier = Modifier
                .padding(bottom = 72.dp)
                .constrainAs(signUpButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.sign_in_question),
                color = Color.White,
                fontFamily = Oxygen,
                fontSize = 16.sp
            )
            TextButton(
                modifier = Modifier.testTag(TestTags.SIGN_UP_BUTTON),
                onClick = onSignUpClicked,
                enabled = enabled
            ) {
                Text(
                    text = stringResource(R.string.sign_up),
                    style = TextStyle(brush = PrimaryGradient),
                    fontFamily = Oxygen,
                    fontSize = 16.sp
                )
            }
        }
    }
}