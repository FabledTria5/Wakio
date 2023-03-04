package dev.fabled.home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.fabled.common.ui.theme.Oxygen
import dev.fabled.home.HomeViewModel

@Composable
fun NotificationsScreen(modifier: Modifier = Modifier, homeViewModel: HomeViewModel) {
    val notifications by homeViewModel.notificationsCount.collectAsStateWithLifecycle()

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Notifications",
                color = Color.White,
                fontFamily = Oxygen,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            IconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = homeViewModel::navigateUp
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Icon close",
                    tint = Color.White
                )
            }
        }
        LazyColumn(
            modifier = Modifier.padding(start = 15.dp, top = 20.dp, end = 15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(vertical = 5.dp)
        ) {
            items(notifications) {
                NotificationsListItem()
            }
        }
    }
}

@Composable
fun NotificationsListItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.DarkGray)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xFF55DE25), Color(0xFF23E97E))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Alarm,
                contentDescription = null,
                tint = Color.White
            )
        }
        Text(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = .8f),
            text = "You have added new alarm the ring will stop once you start walking for at least 1 min",
            color = Color.White,
            fontFamily = Oxygen,
            fontSize = 16.sp
        )
        Text(
            modifier = Modifier
                .padding(bottom = 1.5.dp)
                .align(Alignment.Bottom),
            text = "1h ago",
            color = Color(0xFFDFDFDF),
            fontFamily = Oxygen,
            fontSize = 12.sp
        )
    }
}