package dev.fabled.domain.use_cases.alarms

import dev.fabled.domain.model.AlarmModel
import dev.fabled.domain.model.Resource
import dev.fabled.domain.repository.AlarmsRepository
import dev.fabled.domain.repository.FakeAlarmsRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class GetNextAlarmTests {

    private val fakeRepository: AlarmsRepository = FakeAlarmsRepository()

    private val getNextAlarm = GetNextAlarm(fakeRepository)

    private val currentTime = LocalDateTime.now()

    @Test
    fun `No alarms in database, returns Resource - Idle`() = runBlocking {
        assertEquals(Resource.Idle, getNextAlarm())
    }

    @Test
    fun `One alarm with inappropriate day, return Resource - Idle`() = runBlocking {
        val currentDay = currentTime.dayOfWeek.value

        val alarmModel = AlarmModel(
            alarmId = 0,
            alarmName = "New alarm",
            alarmHour = 9,
            alarmMinute = 0,
            alarmDays = listOf(currentDay - 1, currentDay + 1),
            alarmSoundTag = "",
            alarmVolume = 0f,
            isVibrationEnabled = false,
            isAlarmEnabled = false,
            gradientTag = "",
            createdAt = 0L
        )

        fakeRepository.createNewAlarm(alarmModel)

        assertEquals(Resource.Idle, getNextAlarm())
    }

    @Test
    fun `Multiple alarms with inappropriate days, return Resource - Idle`() = runBlocking {
        val currentDay = currentTime.dayOfWeek.value

        val alarmModel = AlarmModel(
            alarmId = 0,
            alarmName = "New alarm",
            alarmHour = 9,
            alarmMinute = 0,
            alarmDays = listOf(currentDay - 1, currentDay + 1, currentDay + 3, currentDay - 2),
            alarmSoundTag = "",
            alarmVolume = 0f,
            isVibrationEnabled = false,
            isAlarmEnabled = false,
            gradientTag = "",
            createdAt = 0L
        )

        fakeRepository.createNewAlarm(alarmModel)

        assertEquals(Resource.Idle, getNextAlarm())
    }

    @Test
    fun `One alarm with one appropriate day, inappropriate hour, return Resource - Idle`() =
        runBlocking {
            val currentDay = currentTime.dayOfWeek.value
            val currentHour = currentTime.hour

            val alarmModel = AlarmModel(
                alarmId = 0,
                alarmName = "New alarm",
                alarmHour = currentHour - 1,
                alarmMinute = 0,
                alarmDays = listOf(currentDay),
                alarmSoundTag = "",
                alarmVolume = 0f,
                isVibrationEnabled = false,
                isAlarmEnabled = false,
                gradientTag = "",
                createdAt = 0L
            )

            fakeRepository.createNewAlarm(alarmModel)

            assertEquals(Resource.Idle, getNextAlarm())
        }

    @Test
    fun `Multiple alarms for appropriate day with inappropriate time, return Resource - Idle`() =
        runBlocking {
            val currentDay = currentTime.dayOfWeek.value
            val currentHour = currentTime.hour
            val currentMinute = currentTime.minute

            val alarmModel1 = AlarmModel(
                alarmId = 0,
                alarmName = "New alarm",
                alarmHour = currentHour,
                alarmMinute = currentMinute - 30,
                alarmDays = listOf(currentDay),
                alarmSoundTag = "",
                alarmVolume = 0f,
                isVibrationEnabled = false,
                isAlarmEnabled = false,
                gradientTag = "",
                createdAt = 0L
            )

            val alarmModel2 = AlarmModel(
                alarmId = 0,
                alarmName = "New alarm1",
                alarmHour = currentHour - 3,
                alarmMinute = currentMinute,
                alarmDays = listOf(currentDay),
                alarmSoundTag = "",
                alarmVolume = 0f,
                isVibrationEnabled = false,
                isAlarmEnabled = false,
                gradientTag = "",
                createdAt = 0L
            )

            val alarmModel3 = AlarmModel(
                alarmId = 0,
                alarmName = "New alarm2",
                alarmHour = currentHour - 1,
                alarmMinute = currentMinute - 45,
                alarmDays = listOf(currentDay),
                alarmSoundTag = "",
                alarmVolume = 0f,
                isVibrationEnabled = false,
                isAlarmEnabled = false,
                gradientTag = "",
                createdAt = 0L
            )

            fakeRepository.createNewAlarm(alarmModel1)
            fakeRepository.createNewAlarm(alarmModel2)
            fakeRepository.createNewAlarm(alarmModel3)

            assertEquals(Resource.Idle, getNextAlarm())
        }

    @Test
    fun `One alarm with appropriate day and time, return Success`() = runBlocking {
        val currentDay = currentTime.dayOfWeek.value
        val currentHour = currentTime.hour
        val currentMinute = currentTime.minute

        val alarmModel = AlarmModel(
            alarmId = 0,
            alarmName = "New alarm",
            alarmHour = currentHour,
            alarmMinute = currentMinute + 10,
            alarmDays = listOf(currentDay),
            alarmSoundTag = "",
            alarmVolume = 0f,
            isVibrationEnabled = false,
            isAlarmEnabled = false,
            gradientTag = "",
            createdAt = 0L
        )

        fakeRepository.createNewAlarm(alarmModel)

        assertTrue(getNextAlarm() is Resource.Success)
    }

    @Test
    fun `Alarms for every day with appropriate time, return Success`() = runBlocking {
        val currentHour = currentTime.hour
        val currentMinute = currentTime.minute

        val alarmModel = AlarmModel(
            alarmId = 0,
            alarmName = "New alarm",
            alarmHour = currentHour,
            alarmMinute = currentMinute + 15,
            alarmDays = listOf(1, 2, 3, 4, 5, 6, 7),
            alarmSoundTag = "",
            alarmVolume = 0f,
            isVibrationEnabled = false,
            isAlarmEnabled = false,
            gradientTag = "",
            createdAt = 0L
        )

        fakeRepository.createNewAlarm(alarmModel)

        assertTrue(getNextAlarm() is Resource.Success)
    }

    @Test
    fun `Alarm for every day with current minute, return Idle`() = runBlocking {
        val currentHour = currentTime.hour
        val currentMinute = currentTime.minute

        val alarmModel = AlarmModel(
            alarmId = 0,
            alarmName = "New alarm",
            alarmHour = currentHour,
            alarmMinute = currentMinute,
            alarmDays = listOf(1, 2, 3, 4, 5, 6, 7),
            alarmSoundTag = "",
            alarmVolume = 0f,
            isVibrationEnabled = false,
            isAlarmEnabled = false,
            gradientTag = "",
            createdAt = 0L
        )

        fakeRepository.createNewAlarm(alarmModel)

        assertTrue(getNextAlarm() is Resource.Success)
    }

    @AfterEach
    fun removeLastAddedAlarm() = runBlocking {
        fakeRepository.deleteAlarm(0)
    }

}