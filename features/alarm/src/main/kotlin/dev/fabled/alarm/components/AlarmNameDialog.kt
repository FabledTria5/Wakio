package dev.fabled.alarm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import dev.fabled.alarm.R
import dev.fabled.common.ui.components.GradientButton
import dev.fabled.common.ui.theme.BackgroundColor
import dev.fabled.common.ui.theme.Oxygen
import dev.fabled.common.ui.theme.PrimaryDark
import dev.fabled.common.ui.theme.PrimaryGradient
import dev.fabled.common.ui.theme.Roboto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmNameDialog(
    modifier: Modifier = Modifier,
    alarmName: String = "",
    onDismiss: () -> Unit,
    onSaveAlarmName: (String) -> Unit
) {
    var alarmNameText by remember { mutableStateOf(alarmName) }

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.clip(RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = modifier
                .background(BackgroundColor)
                .clip(RoundedCornerShape(10.dp))
                .padding(25.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.alarm_name),
                    color = Color.White,
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Normal
                )
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(18.dp),
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            TextField(
                modifier = Modifier
                    .padding(top = 25.dp, bottom = 40.dp)
                    .fillMaxWidth(),
                value = alarmNameText,
                onValueChange = { alarmNameText = it },
                shape = RoundedCornerShape(16.dp),
                placeholder = {
                    Text(
                        text = stringResource(R.string.enter_name),
                        color = Color(0xFF8D8D8D),
                        fontFamily = Roboto,
                        fontSize = 14.sp
                    )
                },
                textStyle = TextStyle(fontFamily = Roboto, fontSize = 14.sp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    containerColor = Color(0xFF353535),
                    focusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = PrimaryDark
                )
            )
            GradientButton(
                modifier = Modifier.fillMaxWidth(),
                gradient = PrimaryGradient,
                shape = RoundedCornerShape(8.dp),
                onClick = { onSaveAlarmName(alarmNameText) }
            ) {
                Text(
                    text = stringResource(id = R.string.save),
                    color = Color.White,
                    fontFamily = Oxygen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}