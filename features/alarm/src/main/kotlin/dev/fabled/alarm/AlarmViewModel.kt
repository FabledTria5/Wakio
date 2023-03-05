package dev.fabled.alarm

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.fabled.alarm.model.AlarmSoundModel
import dev.fabled.alarm.model.AlarmUiModel
import dev.fabled.alarm.model.TimeModel
import dev.fabled.alarm.utils.toUiModel
import dev.fabled.domain.model.AlarmModel
import dev.fabled.domain.model.Resource
import dev.fabled.domain.use_cases.alarms.CreateNewAlarm
import dev.fabled.domain.use_cases.alarms.DeleteAlarm
import dev.fabled.domain.use_cases.alarms.GetAlarmList
import dev.fabled.navigation.navigation_core.Navigator
import dev.fabled.navigation.navigation_directions.AlarmDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
@Stable
class AlarmViewModel @Inject constructor(
    private val navigator: Navigator,
    private val createNewAlarm: CreateNewAlarm,
    private val deleteAlarm: DeleteAlarm,
    getAlarmList: GetAlarmList
) : ViewModel(), Navigator by navigator {

    private val _alarmsList = MutableStateFlow<List<AlarmUiModel>>(emptyList())
    val alarmsList = _alarmsList.asStateFlow()

    private val _selectedAlarm = MutableStateFlow(AlarmUiModel())
    val selectedAlarm = _selectedAlarm.asStateFlow()

    init {
        getAlarmList()
            .map { modelsList ->
                modelsList.map { model -> model.toUiModel() }
            }
            .onEach { list -> _alarmsList.update { list } }
            .catch { exception -> Timber.e(exception) }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun openAlarmEditScreen(alarmUiModel: AlarmUiModel = AlarmUiModel()) {
        _selectedAlarm.update { alarmUiModel }
        navigator.navigate(AlarmDirections.AlarmEditScreen.route())
    }

    fun updateAlarmName(newName: String) {
        _selectedAlarm.value.alarmName.value = newName
    }

    fun updateTime(timeModel: TimeModel) {
        _selectedAlarm.value.alarmTime.value = timeModel
    }

    fun onDaySelected(index: Int) {
        val isDaySelected = _selectedAlarm.value.alarmDays[index].isChecked.value
        _selectedAlarm.value.alarmDays[index].isChecked.value = !isDaySelected
    }

    fun updateAlarmSound(newSound: AlarmSoundModel) {
        _selectedAlarm.value.alarmSoundModel.value = newSound
    }

    fun updateVolume(newVolume: Float) {
        _selectedAlarm.value.alarmVolume.value = newVolume
    }

    fun updateVibrationState(isEnabled: Boolean) {
        _selectedAlarm.value.isVibrating.value = isEnabled
    }

    fun saveAlarm() {
        viewModelScope.launch(Dispatchers.IO) {
            val alarmUiModel = _selectedAlarm.value

            createNewAlarm(
                alarmModel = AlarmModel(
                    alarmId = alarmUiModel.alarmId,
                    alarmName = alarmUiModel.alarmName.value,
                    alarmHour = alarmUiModel.alarmTime.value.hours,
                    alarmMinute = alarmUiModel.alarmTime.value.minutes,
                    alarmDays = alarmUiModel.alarmDays
                        .filter { it.isChecked.value }
                        .map { it.dayOfWeekNumber },
                    alarmSoundTag = alarmUiModel.alarmSoundModel.value.tag,
                    alarmVolume = alarmUiModel.alarmVolume.value,
                    isVibrationEnabled = alarmUiModel.isVibrating.value,
                    isAlarmEnabled = true,
                    gradientTag = alarmUiModel.gradientUiModel.value.tag
                )
            ).collect { result ->
                when (result) {
                    Resource.Completed -> navigateUp()
                    is Resource.Error -> {
                        Timber.e(result.error)
                        navigateUp()
                    }

                    Resource.Loading -> Timber.d("Loading")
                    else -> Unit
                }
            }
        }
    }

    fun removeAlarm(alarmUiModel: AlarmUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAlarm(
                alarmModel = AlarmModel(
                    alarmId = alarmUiModel.alarmId,
                    alarmName = alarmUiModel.alarmName.value,
                    alarmHour = alarmUiModel.alarmTime.value.hours,
                    alarmMinute = alarmUiModel.alarmTime.value.minutes,
                    alarmDays = alarmUiModel.alarmDays
                        .filter { it.isChecked.value }
                        .map { it.dayOfWeekNumber },
                    alarmSoundTag = alarmUiModel.alarmSoundModel.value.tag,
                    alarmVolume = alarmUiModel.alarmVolume.value,
                    isVibrationEnabled = alarmUiModel.isVibrating.value,
                    isAlarmEnabled = true,
                    gradientTag = alarmUiModel.gradientUiModel.value.tag
                )
            )
        }
    }

}