package dev.fabled.alarm.screens

import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.fabled.alarm.AlarmViewModel
import dev.fabled.alarm.mocks.FakeAlarmUtil
import dev.fabled.alarm.mocks.FakeAlarmsRepository
import dev.fabled.alarm.mocks.FakeNavigator
import dev.fabled.alarm.utils.TestTags
import dev.fabled.common.ui.theme.WakioTheme
import dev.fabled.domain.use_cases.alarms.CreateNewAlarm
import dev.fabled.domain.use_cases.alarms.DeleteAlarm
import dev.fabled.domain.use_cases.alarms.GetAlarmList
import dev.fabled.navigation.navigation_core.NavigatorEvent
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class AlarmEditScreenTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var alarmViewModel: AlarmViewModel

    private val fakeAlarmsRepository = FakeAlarmsRepository()

    private val fakeNavigator = FakeNavigator()

    @Before
    fun init() {
        alarmViewModel = AlarmViewModel(
            getAlarmList = GetAlarmList(fakeAlarmsRepository),
            navigator = FakeNavigator(),
            createNewAlarm = CreateNewAlarm(FakeAlarmUtil(), fakeAlarmsRepository),
            deleteAlarm = DeleteAlarm(fakeAlarmsRepository, FakeAlarmUtil())
        )

        composeTestRule.setContent {
            WakioTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AlarmEditScreen(alarmViewModel = alarmViewModel)
                }
            }
        }
    }

    @Test
    fun checkIfAllContentIsDisplayed(): Unit = with(composeTestRule) {
        onNodeWithText("Set Alarm").assertIsDisplayed()
        onNodeWithText("Vibrate").assertIsDisplayed()
    }

    @Test
    fun checkIfAlarmNameDialogShowsCorrect(): Unit = with(composeTestRule) {
        // Check if dialog does not exist
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG).assertDoesNotExist()

        // Open dialog
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG_OPEN_BUTTON).performClick()

        // Check if dialog exists and displayed
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG).assertExists()
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG).assertIsDisplayed()
    }

    @Test
    fun checkIfAlarmNameDialogClosesCorrect(): Unit = with(composeTestRule) {
        // Open dialog
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG_OPEN_BUTTON).performClick()

        // Check if dialog is displayed
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG).assertIsDisplayed()

        // Close dialog
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG_CLOSE_BUTTON).performClick()

        // Check if dialog does not exist
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG).assertDoesNotExist()
    }

    @Test
    fun checkIfAlarmDialogDismissesCorrect(): Unit = with(composeTestRule) {
        // Open dialog
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG_OPEN_BUTTON).performClick()

        // Check if dialog is displayed
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG).assertIsDisplayed()

        // Emulate back press
        activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        // Check if dialog does not exist
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG).assertDoesNotExist()
    }

    @Test
    fun checkIfAlarmNameChangesCorrect(): Unit = with(composeTestRule) {
        val newText = "Test text input"

        //Open dialog
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG_OPEN_BUTTON).performClick()

        // Check if has default text
        onNodeWithTag(TestTags.ALARM_NAME_INPUT).assertTextContains("New alarm")

        // Clear text
        onNodeWithTag(TestTags.ALARM_NAME_INPUT).performTextClearance()

        // Check if text has been cleared
        onNodeWithTag(TestTags.ALARM_NAME_INPUT).assertTextContains("")

        // Enter new text
        onNodeWithTag(TestTags.ALARM_NAME_INPUT).performTextInput(newText)

        // Check if text field contains new text
        onNodeWithTag(TestTags.ALARM_NAME_INPUT).assertTextContains(newText)

        // Close dialog
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG_CLOSE_BUTTON).performClick()

        // Check if alarm name label contains new text
        onNodeWithText(newText).assertIsDisplayed()
    }

    @Test
    fun checkIfEmptyAlarmNameDoesNotClosesDialog(): Unit = with(composeTestRule) {

        // Open dialog
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG_OPEN_BUTTON).performClick()

        // Clear text
        onNodeWithTag(TestTags.ALARM_NAME_INPUT).performTextClearance()

        // Check if button is not enabled
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG_CLOSE_BUTTON).assertIsNotEnabled()

        // Try click multiple times and check if dialog is enabled
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG_CLOSE_BUTTON).performClick()
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG_CLOSE_BUTTON).performClick()
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG_CLOSE_BUTTON).performClick()

        onNodeWithTag(TestTags.ALARM_NAME_DIALOG).assertExists()
        onNodeWithTag(TestTags.ALARM_NAME_DIALOG).assertIsDisplayed()

        // Enter text and successfully close dialog
        onNodeWithTag(TestTags.ALARM_NAME_INPUT).performTextInput("Sample text")

        onNodeWithTag(TestTags.ALARM_NAME_DIALOG_CLOSE_BUTTON).performClick()

        onNodeWithTag(TestTags.ALARM_NAME_DIALOG).assertDoesNotExist()
    }

    @Test
    fun checkIfTimeCarouserInitializesWithCorrectValues(): Unit = with(composeTestRule) {
        val time = LocalDateTime.now()

        val hour = if (time.hour < 10) "0${time.hour}" else "${time.hour}"
        val minute = if (time.minute < 10) "0${time.minute}" else "${time.minute}"

        onNodeWithText(hour).assertIsDisplayed()
        onNodeWithText(minute).assertIsDisplayed()
    }

    @Test
    fun checkIfDaySelectionOpenCloseOptionWorks(): Unit = with(composeTestRule) {
        // Check if section is opened by default
        onNodeWithTag(TestTags.DAY_SELECTOR).assertExists()
        onNodeWithTag(TestTags.DAY_SELECTOR).assertIsDisplayed()

        // Perform click on repeat section
        onNodeWithText("Repeat").performClick()

        // Check if section is now closed
        onNodeWithTag(TestTags.DAY_SELECTOR).assertDoesNotExist()

        // Click again
        onNodeWithText("Repeat").performClick()

        // Check if section is opened again
        onNodeWithTag(TestTags.DAY_SELECTOR).assertExists()
        onNodeWithTag(TestTags.DAY_SELECTOR).assertIsDisplayed()
    }

    @Test
    fun checkIfBackNavigationWorks(): Unit = with(composeTestRule) {
        var currentNavCommand: NavigatorEvent? = null
        runBlocking {
            fakeNavigator.destinations.collect {
                currentNavCommand = it
            }

            // Try to navigate back
            composeTestRule.onNodeWithText("Cancel").performClick()

            assertEquals(NavigatorEvent.NavigateUp, currentNavCommand)
        }
    }

}