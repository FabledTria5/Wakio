package dev.fabled.alarm.screens

import android.media.MediaPlayer
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.fabled.alarm.AlarmViewModel
import dev.fabled.alarm.R
import dev.fabled.alarm.model.AlarmSoundModel
import dev.fabled.alarm.utils.playRawResAudio
import dev.fabled.common.ui.theme.Oxygen
import dev.fabled.common.ui.theme.PrimaryDark
import dev.fabled.common.ui.theme.PrimaryGradient
import dev.fabled.common.ui.theme.PrimaryLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmSoundScreen(modifier: Modifier = Modifier, alarmViewModel: AlarmViewModel) {
    val context = LocalContext.current

    val selectedAlarm by alarmViewModel.selectedAlarm.collectAsStateWithLifecycle()

    val mediaPlayer = remember { MediaPlayer() }
    val soundsList = remember { AlarmSoundModel.getSoundsList() }

    var currentPlayingTrack by remember {
        mutableStateOf<AlarmSoundModel?>(
            value = null,
            policy = neverEqualPolicy()
        )
    }

    BackHandler(onBack = alarmViewModel::navigateUp)

    DisposableEffect(key1 = currentPlayingTrack) {
        if (currentPlayingTrack != null) {
            playRawResAudio(
                context = context,
                mediaPlayer = mediaPlayer,
                audioRes = currentPlayingTrack!!.audio
            )
        }

        onDispose {
            mediaPlayer.stop()
            mediaPlayer.reset()
        }
    }

    Scaffold(
        modifier = modifier
            .padding(horizontal = 15.dp)
            .padding(top = 20.dp),
        topBar = {
            AlarmSoundTopBar(
                modifier = Modifier.fillMaxWidth(),
                onCancelClick = alarmViewModel::navigateUp
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(top = padding.calculateTopPadding())
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(
                items = soundsList,
                key = { it.tag }
            ) { model ->
                AlarmSoundListItem(
                    soundModel = model,
                    isPlaying = currentPlayingTrack == model,
                    isSelected = model == selectedAlarm.alarmSoundModel.value,
                    onSelectSound = alarmViewModel::updateAlarmSound,
                    onPlayAudioClick = { sound ->
                        currentPlayingTrack = if (currentPlayingTrack == sound) null else model
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun AlarmSoundTopBar(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
) {
    Box(modifier = modifier) {
        TextButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = onCancelClick,
            contentPadding = PaddingValues()
        ) {
            Text(
                text = stringResource(R.string.back),
                style = TextStyle(brush = PrimaryGradient),
                fontFamily = Oxygen,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.set_alarm_sound),
            color = Color.White,
            fontFamily = Oxygen,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    }
}

@Composable
private fun AlarmSoundListItem(
    soundModel: AlarmSoundModel,
    isPlaying: Boolean,
    isSelected: Boolean,
    onPlayAudioClick: (AlarmSoundModel) -> Unit,
    onSelectSound: (AlarmSoundModel) -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color.Black else Color.Transparent,
        animationSpec = tween(durationMillis = 500)
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected) PrimaryLight else Color.White,
        animationSpec = tween(durationMillis = 500)
    )

    val iconTint by animateColorAsState(
        targetValue = if (isSelected) PrimaryDark else Color.White,
        animationSpec = tween(durationMillis = 500)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                brush = PrimaryGradient,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 15.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = soundModel.name),
            color = textColor,
            fontFamily = Oxygen,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(Color.DarkGray)
            )
            IconButton(onClick = { onPlayAudioClick(soundModel) }) {
                if (!isPlaying) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = stringResource(R.string.button_play),
                        tint = iconTint
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Pause,
                        contentDescription = stringResource(R.string.button_play),
                        tint = iconTint
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(Color.DarkGray)
            )
            IconButton(onClick = { onSelectSound(soundModel) }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(R.string.button_play),
                    tint = iconTint
                )
            }
        }
    }
}