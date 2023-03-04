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
import dev.fabled.authorization.components.UsernameTextField
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
internal fun SignUpPage(
    modifier: Modifier = Modifier,
    signUpState: AuthorizationState,
    onFormEvent: (AuthorizationEvent) -> Unit,
    onSignUpClicked: () -> Unit,
    onSignInClicked: () -> Unit,
    enabled: Boolean
) {
    val onEmailChanged: (String) -> Unit = remember {
        { email ->
            onFormEvent(AuthorizationEvent.EmailChanged(email))
        }
    }

    val onUsernameChanged: (String) -> Unit = remember {
        { username ->
            onFormEvent(AuthorizationEvent.UsernameChanged(username))
        }
    }

    val onPasswordChanged: (String) -> Unit = remember {
        { password ->
            onFormEvent(AuthorizationEvent.PasswordChanged(password))
        }
    }

    ConstraintLayout(modifier = modifier) {
        val (welcomeMessage, inputColumn, signUpButton, signInButton) = createRefs()

        WelcomeMessage(
            modifier = Modifier.constrainAs(welcomeMessage) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)

                width = Dimension.percent(.9f)
            },
            message = stringResource(id = R.string.sign_up_message)
        )
        Column(
            modifier = Modifier.constrainAs(inputColumn) {
                start.linkTo(parent.start)
                top.linkTo(welcomeMessage.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(signUpButton.top)
            }
        ) {
            EmailTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestTags.EMAIL_TEXT_FIELD),
                email = signUpState.email,
                onEmailChanged = onEmailChanged,
                enabled = enabled
            )
            AnimatedVisibility(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .align(Alignment.End),
                visible = signUpState.emailError != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    modifier = Modifier.testTag(TestTags.EMAIL_ERROR_TEXT),
                    text = signUpState.emailError.orEmpty(),
                    color = DarkOrange,
                    fontFamily = Oxygen
                )
            }
            UsernameTextField(
                modifier = Modifier
                    .padding(top = 25.dp)
                    .fillMaxWidth()
                    .testTag(TestTags.USERNAME_TEXT_FIELD),
                username = signUpState.username,
                onUsernameChange = onUsernameChanged,
                enabled = enabled
            )
            AnimatedVisibility(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .align(Alignment.End),
                visible = signUpState.usernameError != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    modifier = Modifier.testTag(TestTags.EMAIL_ERROR_TEXT),
                    text = signUpState.usernameError.orEmpty(),
                    color = DarkOrange,
                    fontFamily = Oxygen
                )
            }
            PasswordTextField(
                modifier = Modifier
                    .padding(top = 25.dp)
                    .fillMaxWidth()
                    .testTag(TestTags.PASSWORD_TEXT_FIELD),
                password = signUpState.password,
                onPasswordChanged = onPasswordChanged,
                enabled = enabled
            )
            AnimatedVisibility(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .align(Alignment.End),
                visible = signUpState.passwordError != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    modifier = Modifier.testTag(TestTags.EMAIL_ERROR_TEXT),
                    text = signUpState.passwordError.orEmpty(),
                    color = DarkOrange,
                    fontFamily = Oxygen
                )
            }
        }
        GradientButton(
            modifier = Modifier
                .padding(bottom = 150.dp)
                .constrainAs(signUpButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(signInButton.top)
                }
                .fillMaxWidth(),
            gradient = PrimaryGradient,
            shape = RoundedCornerShape(8.dp),
            onClick = onSignUpClicked,
            enabled = enabled
        ) {
            Text(
                modifier = Modifier.padding(vertical = 15.dp),
                text = stringResource(id = R.string.sign_up),
                color = Color.White
            )
        }
        Row(
            modifier = Modifier
                .padding(bottom = 72.dp)
                .constrainAs(signInButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.sign_up_question),
                color = Color.White,
                fontFamily = Oxygen,
                fontSize = 16.sp
            )
            TextButton(onClick = onSignInClicked, enabled = enabled) {
                Text(
                    text = stringResource(R.string.sign_in),
                    style = TextStyle(brush = PrimaryGradient),
                    fontFamily = Oxygen,
                    fontSize = 16.sp
                )
            }
        }
    }
}