package dev.fabled.alarm.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.fabled.alarm.AlarmViewModel
import dev.fabled.alarm.R
import dev.fabled.alarm.model.AlarmUiModel
import dev.fabled.common.ui.components.GradientIcon
import dev.fabled.common.ui.theme.BackgroundColor
import dev.fabled.common.ui.theme.Oxygen
import dev.fabled.common.ui.theme.PrimaryGradient
import dev.fabled.common.ui.theme.PrimaryLight
import dev.fabled.common.ui.utils.gradientBackground

@OptIn(ExperimentalTextApi::class)
@Composable
fun AlarmsScreen(modifier: Modifier = Modifier, alarmViewModel: AlarmViewModel) {
    val alarmsList by alarmViewModel.alarmsList.collectAsStateWithLifecycle()

    var isEditing by remember { mutableStateOf(value = false) }

    Scaffold(
        modifier = modifier.padding(horizontal = 15.dp, vertical = 20.dp),
        topBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.alarm),
                    color = Color.White,
                    fontFamily = Oxygen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                if (!isEditing) {
                    TextButton(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        onClick = { isEditing = true }
                    ) {
                        Text(
                            text = stringResource(R.string.edit),
                            style = TextStyle(brush = PrimaryGradient),
                            fontFamily = Oxygen,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                } else {
                    IconButton(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        onClick = { isEditing = false }
                    ) {
                        GradientIcon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            brush = PrimaryGradient
                        )
                    }
                }
            }
        },
        containerColor = BackgroundColor
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(top = padding.calculateTopPadding())
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                contentPadding = PaddingValues(top = 20.dp, bottom = 80.dp)
            ) {
                items(items = alarmsList, key = { it.alarmId }) { model ->
                    AlarmListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(143.dp),
                        alarmUiModel = model,
                        isEditing = isEditing,
                        onItemClick = alarmViewModel::openAlarmEditScreen,
                        onDeleteAlarmClick = alarmViewModel::removeAlarm
                    )
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 30.dp)
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(brush = PrimaryGradient)
                    .clickable { alarmViewModel.openAlarmEditScreen() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_alarm),
                    tint = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlarmListItem(
    modifier: Modifier = Modifier,
    alarmUiModel: AlarmUiModel,
    isEditing: Boolean,
    onItemClick: (AlarmUiModel) -> Unit,
    onDeleteAlarmClick: (AlarmUiModel) -> Unit
) {
    val alarmActiveDays by remember {
        derivedStateOf {
            alarmUiModel.alarmDays
                .filter { it.isChecked.value }
                .joinToString { it.dayName }
        }
    }

    val cardPadding by animateDpAsState(
        targetValue = if (isEditing) 60.dp else 0.dp,
        label = "card_padding_animation"
    )

    Box(modifier = modifier) {
        if (isEditing) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(50.dp),
                onClick = { onDeleteAlarmClick(alarmUiModel) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
        Card(
            modifier = Modifier.padding(end = cardPadding),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            onClick = { onItemClick(alarmUiModel) }
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .gradientBackground(
                        colors = alarmUiModel.gradientUiModel.value.colors,
                        angle = 45f
                    )
                    .padding(horizontal = 13.dp, vertical = 15.dp),
            ) {
                val (alarmName, alarmTime, alarmDays, alarmDelay, alarmSwitch) = createRefs()
                Text(
                    modifier = Modifier.constrainAs(alarmName) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    },
                    text = alarmUiModel.alarmName.value,
                    color = Color.White,
                    fontFamily = Oxygen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Switch(
                    modifier = Modifier.constrainAs(alarmSwitch) {
                        top.linkTo(alarmName.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(alarmTime.top)
                    },
                    checked = alarmUiModel.isAlarmEnabled.value,
                    onCheckedChange = {},
                    colors = SwitchDefaults.colors(
                        checkedBorderColor = Color.Transparent,
                        uncheckedBorderColor = Color.Transparent,
                        checkedTrackColor = PrimaryLight,
                        uncheckedTrackColor = Color.LightGray,
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White
                    )
                )
                Text(
                    modifier = Modifier.constrainAs(alarmTime) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                    text = "${alarmUiModel.alarmTime.value.hours}" +
                            ":" +
                            if (alarmUiModel.alarmTime.value.minutes < 10)
                                "0${alarmUiModel.alarmTime.value.minutes}"
                            else
                                alarmUiModel.alarmTime.value.minutes,
                    color = Color.White,
                    fontFamily = Oxygen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )
                Text(
                    modifier = Modifier.constrainAs(alarmDays) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    },
                    text = alarmActiveDays,
                    color = Color.White,
                    fontFamily = Oxygen,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.constrainAs(alarmDelay) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                    text = alarmUiModel.alarmDelay.value,
                    color = Color.White,
                    fontFamily = Oxygen,
                    fontSize = 12.sp
                )
            }
        }
    }
}