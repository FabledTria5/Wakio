package dev.fabled.alarm.screens.full_screen_alarm

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.fabled.alarm.R
import dev.fabled.common.ui.components.GifImage
import dev.fabled.common.ui.theme.Oxygen
import dev.fabled.common.ui.theme.Roboto
import dev.fabled.common.ui.theme.WakioTheme
import java.util.Calendar

@Composable
fun WorkingAlarmScreen(onCloseScreenClicked: () -> Unit) {
    val currentTime by remember {
        derivedStateOf {
            val calendar = Calendar.getInstance()

            "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GifImage(
            modifier = Modifier.fillMaxSize(),
            model = R.drawable.alarm_background,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Rise and shine",
                color = Color.White,
                fontFamily = Oxygen,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Text(
                modifier = Modifier.padding(top = 24.dp, bottom = 56.dp),
                text = currentTime,
                color = Color.White,
                fontFamily = Roboto,
                fontWeight = FontWeight.Thin,
                fontSize = 96.sp
            )
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(fraction = .7f),
                onClick = onCloseScreenClicked,
                shape = RoundedCornerShape(56.dp),
                border = BorderStroke(width = 1.dp, color = Color.White)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "Press to stop",
                    color = Color.White,
                    fontFamily = Oxygen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun WorkingAlarmScreenPrev() {
    WakioTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            WorkingAlarmScreen {

            }
        }
    }
}