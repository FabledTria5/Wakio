package dev.fabled.home

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.fabled.common.code.model.Gender
import dev.fabled.domain.model.Resource
import dev.fabled.domain.repository.AuthorizationRepository
import dev.fabled.domain.repository.QuotesRepository
import dev.fabled.domain.use_cases.alarms.GetNextAlarm
import dev.fabled.domain.use_cases.articles.GetDailyArticle
import dev.fabled.home.model.UiArticle
import dev.fabled.home.model.WelcomeData
import dev.fabled.home.utils.toUiModel
import dev.fabled.navigation.navigation_core.Navigator
import dev.fabled.navigation.navigation_directions.ActivityDirections
import dev.fabled.navigation.navigation_directions.AlarmDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
@Stable
class HomeViewModel @Inject constructor(
    private val navigator: Navigator,
    private val authorizationRepository: AuthorizationRepository,
    quotesRepository: QuotesRepository,
    getDailyArticle: GetDailyArticle,
    getNextAlarm: GetNextAlarm
) : ViewModel(), Navigator by navigator {

    private val _welcomeData = MutableStateFlow(WelcomeData())
    val welcomeData = _welcomeData.asStateFlow()

    private val _notificationsCount = MutableStateFlow(value = 5)
    val notificationsCount = _notificationsCount.asStateFlow()

    val dailyQuote = quotesRepository.getQuoteOfTheDay()
        .debounce(timeoutMillis = 500)
        .catch { e -> Timber.e(e) }
        .flowOn(Dispatchers.IO)
        .stateIn(scope = viewModelScope, started = SharingStarted.Lazily, initialValue = "")

    val nextAlarm = getNextAlarm()
        .debounce(timeoutMillis = 500)
        .map { result ->
            when (result) {
                is Resource.Success -> Resource.Success(result.data.toUiModel())
                is Resource.Error -> {
                    Timber.e(result.error)
                    Resource.Error(result.error)
                }

                Resource.Idle -> Resource.Idle
                Resource.Loading -> Resource.Loading
                Resource.Completed -> Resource.Completed
            }
        }
        .catch { Timber.e(it) }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = Resource.Idle
        )

    val dailyArticle = getDailyArticle()
        .debounce(timeoutMillis = 500)
        .map { result ->
            when (result) {
                is Resource.Success -> Resource.Success(
                    data = UiArticle(
                        url = result.data.url,
                        imagePath = result.data.imagePath,
                        articleTitle = result.data.articleName,
                        articleText = result.data.shortText
                    )
                )

                Resource.Loading -> Resource.Loading
                Resource.Completed -> Resource.Completed
                is Resource.Error -> {
                    Timber.e(result.error)
                    Resource.Error(result.error)
                }

                Resource.Idle -> Resource.Idle
            }
        }
        .catch { e -> Timber.e(e) }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = Resource.Idle
        )

    init {
        getWelcomeData()
    }

    private fun getWelcomeData() = viewModelScope.launch {
        val userModel = withContext(Dispatchers.IO) {
            authorizationRepository.getUserData()
        }
        val currentTime = withContext(Dispatchers.Default) {
            val dateFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy", Locale.ENGLISH)
            return@withContext LocalDate.now().format(dateFormatter)
        }

        _welcomeData.update {
            WelcomeData(
                username = userModel.username,
                userGender = Gender.ofTag(userModel.userGender),
                currentTime = currentTime
            )
        }
    }

    fun openAlarmEditScreen() {
        navigate(route = AlarmDirections.AlarmEditScreen.route())
    }

    fun openUserStats() {
        navigate(route = ActivityDirections.ActivityScreen.route())
    }

}