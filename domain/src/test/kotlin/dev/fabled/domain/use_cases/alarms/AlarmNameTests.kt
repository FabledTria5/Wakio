package dev.fabled.domain.use_cases.alarms

import dev.fabled.domain.model.AlarmModel
import dev.fabled.domain.model.Resource
import dev.fabled.domain.repository.FakeAlarmUtil
import dev.fabled.domain.repository.FakeAlarmsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AlarmNameTests {

    private val fakeRepository = FakeAlarmsRepository()

    private val fakeAlarmUtil = FakeAlarmUtil()

    private val createNewAlarm = CreateNewAlarm(fakeAlarmUtil, fakeRepository)

    @Test
    fun `Create alarm with existing name, creates alarm with suffix 1`() = runBlocking {
        val alarmName = "New alarm"
        val expectingName = "New alarm(1)"

        val alarmModel = AlarmModel(
            alarmId = 0,
            alarmName = alarmName,
            alarmHour = 9,
            alarmMinute = 0,
            alarmDays = listOf(5),
            alarmSoundTag = "",
            alarmVolume = 0f,
            isVibrationEnabled = false,
            isAlarmEnabled = false,
            gradientTag = "",
            createdAt = 0L
        )

        createNewAlarm(alarmModel).collect()
        createNewAlarm(alarmModel).collect()

        val lastCreatedAlarm = fakeRepository
            .getAlarmsList()
            .first()
            .last()

        assertEquals(expectingName, lastCreatedAlarm.alarmName)
    }

    @Test
    fun `Create multiple alarms with existing names, creates correct suffixes`() = runBlocking {
        val regex = Regex(pattern = "^[A-Za-z0-9]+.*\\([0-9]+\\)$")
        val alarmName = "New alarm"

        val alarmModel = AlarmModel(
            alarmId = 0,
            alarmName = alarmName,
            alarmHour = 9,
            alarmMinute = 0,
            alarmDays = listOf(5),
            alarmSoundTag = "",
            alarmVolume = 0f,
            isVibrationEnabled = false,
            isAlarmEnabled = false,
            gradientTag = "",
            createdAt = 0L
        )

        repeat(7) {
            createNewAlarm(alarmModel).collect()
        }

        val alarmList = fakeRepository
            .getAlarmsList()
            .first()

        assertEquals(alarmList.first().alarmName, alarmName)

        for (i in 1..alarmList.lastIndex) {
            val name = alarmList[i].alarmName

            assertTrue(name.matches(regex))
        }

        assertTrue(alarmList.last().alarmName.contains("(6)"))
    }

    @Test
    fun `Create alarm with empty name - emits Error`() = runBlocking {
        val alarmName = ""

        val alarmModel = AlarmModel(
            alarmId = 0,
            alarmName = alarmName,
            alarmHour = 9,
            alarmMinute = 0,
            alarmDays = listOf(5),
            alarmSoundTag = "",
            alarmVolume = 0f,
            isVibrationEnabled = false,
            isAlarmEnabled = false,
            gradientTag = "",
            createdAt = 0L
        )

        val result = createNewAlarm(alarmModel)
            .drop(count = 1)
            .first()

        assertTrue(result is Resource.Error)
    }

    @AfterEach
    fun clearUp() = runBlocking {
        fakeRepository.deleteAlarm(-1)
    }

}