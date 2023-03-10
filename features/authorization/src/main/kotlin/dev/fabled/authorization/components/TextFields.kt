package dev.fabled.authorization.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.fabled.authorization.R
import dev.fabled.common.ui.theme.Oxygen
import dev.fabled.common.ui.theme.PrimaryDark
import dev.fabled.common.ui.theme.TextFieldsColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EmailTextField(
    modifier: Modifier = Modifier,
    email: String,
    onEmailChanged: (String) -> Unit,
    enabled: Boolean
) {
    TextField(
        modifier = modifier,
        value = email,
        onValueChange = onEmailChanged,
        shape = RoundedCornerShape(8.dp),
        placeholder = {
            Text(
                text = stringResource(R.string.email_hint),
                color = Color.White,
                fontFamily = Oxygen,
                fontSize = 14.sp
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            disabledTextColor = Color.White,
            containerColor = TextFieldsColor,
            cursorColor = PrimaryDark,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        enabled = enabled
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UsernameTextField(
    modifier: Modifier = Modifier,
    username: String,
    onUsernameChange: (String) -> Unit,
    enabled: Boolean
) {
    TextField(
        modifier = modifier,
        value = username,
        onValueChange = onUsernameChange,
        shape = RoundedCornerShape(8.dp),
        placeholder = {
            Text(
                text = stringResource(R.string.enter_username_hint),
                color = Color.White,
                fontFamily = Oxygen,
                fontSize = 14.sp
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            disabledTextColor = Color.White,
            containerColor = TextFieldsColor,
            cursorColor = PrimaryDark,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        enabled = enabled
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PasswordTextField(
    modifier: Modifier = Modifier,
    password: String,
    onPasswordChanged: (String) -> Unit,
    enabled: Boolean
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    TextField(
        modifier = modifier,
        value = password,
        onValueChange = onPasswordChanged,
        shape = RoundedCornerShape(8.dp),
        placeholder = {
            Text(
                text = stringResource(R.string.password_hint),
                color = Color.White,
                fontFamily = Oxygen,
                fontSize = 14.sp
            )
        },
        trailingIcon = {
            IconButton(
                onClick = { isPasswordVisible = !isPasswordVisible },
                enabled = enabled
            ) {
                Icon(
                    modifier = Modifier.rotate(degrees = 180f),
                    imageVector = if (isPasswordVisible)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff,
                    contentDescription = null
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            disabledTextColor = Color.White,
            containerColor = TextFieldsColor,
            cursorColor = PrimaryDark,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            focusedTrailingIconColor = Color.White,
            unfocusedTrailingIconColor = Color.White,
        ),
        visualTransformation = if (isPasswordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        enabled = enabled
    )
}