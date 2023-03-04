package dev.fabled.alarm.screens.full_screen_alarm

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.fabled.alarm.R
import dev.fabled.common.ui.components.GifImage
import dev.fabled.common.ui.theme.Oxygen
import dev.fabled.common.ui.theme.Roboto

@Composable
fun WorkingAlarmScreen(onCloseScreenClicked: () -> Unit) {
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
            Row(
                modifier = Modifier.padding(top = 24.dp, bottom = 56.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "5:30",
                    color = Color.White,
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Thin,
                    fontSize = 96.sp
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp, top = 20.dp),
                    text = "AM",
                    color = Color.White,
                    fontFamily = Roboto,
                    fontWeight = FontWeight(200),
                    fontSize = 16.sp
                )
            }
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

@Preview
@Composable
fun WorkingAlarmScreenPrev() {
    WorkingAlarmScreen() {

    }
}