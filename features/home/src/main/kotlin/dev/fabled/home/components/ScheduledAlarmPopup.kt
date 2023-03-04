package dev.fabled.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.fabled.common.ui.theme.Oxygen

@Composable
fun ScheduledAlarmPopup(
    enabled: Boolean,
    onDismiss: () -> Unit,
    onDeleteClick: () -> Unit,
    onPreviewClick: () -> Unit
) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(surface = Color(color = 0xFF353535)),
        shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp))
    ) {
        DropdownMenu(
            modifier = Modifier.size(215.dp, 100.dp),
            expanded = enabled,
            onDismissRequest = onDismiss
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDeleteClick() }
                        .padding(horizontal = 15.dp)
                        .padding(top = 15.dp, bottom = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Delete",
                        color = Color.White,
                        fontFamily = Oxygen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Icon(
                        imageVector = Icons.Default.Backspace,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(color = 0xFF4B4B4B))
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPreviewClick() }
                        .padding(horizontal = 15.dp)
                        .padding(top = 5.dp, bottom = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Preview Alarm",
                        color = Color.White,
                        fontFamily = Oxygen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}