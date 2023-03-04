package dev.fabled.authorization.screens

import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.fabled.authorization.AuthorizationViewModel
import dev.fabled.authorization.di.FakeAuthorizationRepository
import dev.fabled.authorization.screens.authorization.AuthorizationScreen
import dev.fabled.authorization.util.TestTags
import dev.fabled.common.ui.theme.WakioTheme
import dev.fabled.domain.repository.AuthorizationRepository
import dev.fabled.domain.use_cases.authorization.ValidateEmail
import dev.fabled.domain.use_cases.authorization.ValidatePassword
import dev.fabled.domain.use_cases.authorization.ValidateUsername
import dev.fabled.domain.use_cases.authorization.ValidationCases
import dev.fabled.navigation.navigation_core.Navigator
import dev.fabled.navigation.navigation_core.NavigatorImpl
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AuthorizationScreenTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @BindValue
    @JvmField
    val authorizationRepository: AuthorizationRepository = FakeAuthorizationRepository()

    @BindValue
    @JvmField
    val navigator: Navigator = NavigatorImpl()

    private lateinit var authorizationViewModel: AuthorizationViewModel

    @Before
    fun setup() {
        hiltRule.inject()

        authorizationViewModel = AuthorizationViewModel(
            navigator = navigator,
            validationCases = ValidationCases(
                ValidateEmail(),
                ValidateUsername(),
                ValidatePassword()
            ),
            authorizationRepository = authorizationRepository
        )

        composeRule.setContent {
            WakioTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AuthorizationScreen(authorizationViewModel = authorizationViewModel)
                }
            }
        }
    }

    @Test
    fun performSwipeOnPager_shouldNotOpenNextPage() {
        composeRule.onNodeWithTag(TestTags.SIGN_IN_PAGE).assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.SIGN_IN_PAGE).performTouchInput {
            swipeLeft()
        }

        composeRule.onNodeWithTag(TestTags.SIGN_IN_PAGE).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.SIGN_UP_PAGE).assertDoesNotExist()
    }

    @Test
    fun performClickOnSignUpButtonOnSignInPage_shouldGoToSignUpPage() {
        composeRule.onNodeWithTag(TestTags.SIGN_IN_PAGE).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.SIGN_UP_PAGE).assertDoesNotExist()

        composeRule.onNodeWithTag(TestTags.SIGN_UP_BUTTON).performClick()

        composeRule.onNodeWithTag(TestTags.SIGN_UP_PAGE).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.SIGN_IN_PAGE).assertIsNotDisplayed()
    }

    @Test
    fun signInPageEmptyInput_showsTwoErrorMessages() {
        composeRule.onNodeWithTag(TestTags.SIGN_IN_BUTTON).performClick()

        composeRule.onNodeWithTag(TestTags.EMAIL_ERROR_TEXT).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.PASSWORD_ERROR_TEXT).assertIsDisplayed()
    }

}