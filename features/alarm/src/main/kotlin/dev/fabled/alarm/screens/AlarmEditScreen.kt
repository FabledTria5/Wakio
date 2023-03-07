package dev.fabled.alarm.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.fabled.alarm.AlarmViewModel
import dev.fabled.alarm.R
import dev.fabled.alarm.components.AlarmNameDialog
import dev.fabled.alarm.components.TimeCarousel
import dev.fabled.alarm.model.AlarmSoundModel
import dev.fabled.alarm.model.DayUiModel
import dev.fabled.common.ui.theme.Oxygen
import dev.fabled.common.ui.theme.PrimaryDark
import dev.fabled.common.ui.theme.PrimaryGradient
import dev.fabled.common.ui.theme.Roboto
import dev.fabled.navigation.navigation_directions.AlarmDirections

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmEditScreen(modifier: Modifier = Modifier, alarmViewModel: AlarmViewModel) {
    val selectedAlarm by alarmViewModel.selectedAlarm.collectAsStateWithLifecycle()

    var isAlarmNameDialogVisible by rememberSaveable { mutableStateOf(value = false) }
    val canSaveAlarm by remember {
        derivedStateOf {
            selectedAlarm.alarmDays.any { it.isChecked.value }
        }
    }

    val onUpdateAlarmName: (String) -> Unit = remember {
        { newName ->
            alarmViewModel.updateAlarmName(newName)
            isAlarmNameDialogVisible = false
        }
    }

    BackHandler(onBack = alarmViewModel::navigateUp)

    Scaffold(
        modifier = modifier.padding(horizontal = 15.dp, vertical = 20.dp),
        topBar = {
            AlarmEditTopBar(
                modifier = Modifier.fillMaxWidth(),
                canSaveAlarm = canSaveAlarm,
                onCancelClick = alarmViewModel::navigateUp,
                onSaveClick = alarmViewModel::saveAlarm
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .padding(top = 25.dp)
        ) {
            AlarmNameEdit(
                alarmName = selectedAlarm.alarmName.value,
                onEditAlarmClick = { isAlarmNameDialogVisible = true }
            )
            TimeCarousel(
                modifier = Modifier
                    .padding(vertical = 25.dp)
                    .fillMaxWidth(),
                initialTime = selectedAlarm.alarmTime.value,
                onHoursChanged = alarmViewModel::updateHours,
                onMinutesChanged = alarmViewModel::updateMinutes
            )
            RepeatModeSelector(
                modifier = Modifier.fillMaxWidth(),
                repeatDays = selectedAlarm.alarmDays,
                onDaySelected = alarmViewModel::onDaySelected
            )
            SoundSelection(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .fillMaxWidth()
                    .clickable {
                        alarmViewModel.navigate(route = AlarmDirections.AlarmSoundScreen.route())
                    },
                soundModel = selectedAlarm.alarmSoundModel.value
            )
            VolumeSettings(
                modifier = Modifier.fillMaxWidth(),
                alarmVolume = selectedAlarm.alarmVolume.value,
                onVolumeChanged = alarmViewModel::updateVolume
            )
            VibrationSettings(
                modifier = Modifier.fillMaxWidth(),
                isChecked = selectedAlarm.isVibrating.value,
                onCheckedChange = alarmViewModel::updateVibrationState
            )
        }
    }

    if (isAlarmNameDialogVisible)
        AlarmNameDialog(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .height(240.dp)
                .clip(RoundedCornerShape(16.dp)),
            onDismiss = { isAlarmNameDialogVisible = false },
            onSaveAlarmName = onUpdateAlarmName
        )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun AlarmEditTopBar(
    modifier: Modifier = Modifier,
    canSaveAlarm: Boolean,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(onClick = onCancelClick, contentPadding = PaddingValues()) {
            Text(
                text = stringResource(R.string.cancel),
                style = TextStyle(brush = PrimaryGradient),
                fontFamily = Oxygen,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
        Text(
            text = stringResource(R.string.set_alarm),
            color = Color.White,
            fontFamily = Oxygen,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        TextButton(
            onClick = onSaveClick,
            contentPadding = PaddingValues(),
            enabled = canSaveAlarm
        ) {
            Text(
                text = stringResource(R.string.save),
                style = TextStyle(brush = PrimaryGradient),
                fontFamily = Oxygen,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AlarmNameEdit(alarmName: String, onEditAlarmClick: () -> Unit) {
    Row(
        modifier = Modifier.clickable { onEditAlarmClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AnimatedContent(
            targetState = alarmName,
            transitionSpec = { fadeIn() with fadeOut() }
        ) {
            Text(
                text = alarmName.ifEmpty { stringResource(id = R.string.alarm_name) },
                color = Color.White,
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal
            )
        }
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.Default.Edit,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Composable
fun RepeatModeSelector(
    modifier: Modifier = Modifier,
    repeatDays: List<DayUiModel>,
    onDaySelected: (Int) -> Unit
) {
    var isRepeatOptionOpened by rememberSaveable { mutableStateOf(value = true) }

    val iconRotation by animateFloatAsState(targetValue = if (isRepeatOptionOpened) 0f else -90f)

    Column(modifier = modifier.animateContentSize()) {
        Row(
            modifier = modifier
                .clickable { isRepeatOptionOpened = !isRepeatOptionOpened }
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.repeat),
                color = Color.White,
                fontFamily = Oxygen,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Icon(
                modifier = Modifier.rotate(iconRotation),
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.White
            )
        }
        if (isRepeatOptionOpened) {
            Row(
                modifier = modifier.padding(top = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeatDays.forEachIndexed { index, day ->
                    Box(
                        modifier = Modifier
                            .weight(.1f)
                            .clip(RoundedCornerShape(8.dp))
                            .then(
                                if (day.isChecked.value)
                                    Modifier.background(brush = PrimaryGradient)
                                else
                                    Modifier.background(color = Color.DarkGray)
                            )
                            .clickable { onDaySelected(index) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(modifier = Modifier.padding(vertical = 6.dp), text = day.dayName)
                    }
                }
            }
        }
    }
}

@Composable
fun SoundSelection(modifier: Modifier = Modifier, soundModel: AlarmSoundModel) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = stringResource(R.string.sound),
                color = Color.White,
                fontFamily = Oxygen,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = stringResource(id = soundModel.name),
                color = Color.White,
                fontFamily = Oxygen,
                fontSize = 16.sp
            )
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Composable
fun VolumeSettings(
    modifier: Modifier = Modifier,
    alarmVolume: Float,
    onVolumeChanged: (Float) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.volume),
            color = Color.White,
            fontFamily = Oxygen,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Slider(
            value = alarmVolume,
            onValueChange = onVolumeChanged,
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = PrimaryDark,
                inactiveTrackColor = Color.DarkGray
            )
        )
    }
}

@Composable
fun VibrationSettings(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.vibrate),
            color = Color.White,
            fontFamily = Oxygen,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedTrackColor = PrimaryDark,
                uncheckedThumbColor = Color.White,
                uncheckedBorderColor = Color.Transparent,
                uncheckedTrackColor = Color.Gray
            )
        )
    }
}