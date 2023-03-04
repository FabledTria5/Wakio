package dev.fabled.home

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.fabled.common.code.model.Gender
import dev.fabled.domain.model.Resource
import dev.fabled.domain.repository.AuthorizationRepository
import dev.fabled.domain.repository.QuotesRepository
import dev.fabled.domain.use_cases.articles.GetDailyArticle
import dev.fabled.home.model.UiArticle
import dev.fabled.home.model.WelcomeData
import dev.fabled.navigation.navigation_core.Navigator
import dev.fabled.navigation.navigation_directions.PrimaryDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    private val quotesRepository: QuotesRepository,
    private val getDailyArticle: GetDailyArticle
) : ViewModel(), Navigator by navigator {

    private val _welcomeData = MutableStateFlow(WelcomeData())
    val welcomeData = _welcomeData.asStateFlow()

    private val _dailyQuote = MutableStateFlow(value = "")
    val dailyQuote = _dailyQuote.asStateFlow()

    private val _notificationsCount = MutableStateFlow(value = 5)
    val notificationsCount = _notificationsCount.asStateFlow()

    private val _dailyArticle = MutableStateFlow<Resource<UiArticle>>(Resource.Loading)
    val dailyArticle = _dailyArticle.asStateFlow()

    init {
        getWelcomeData()
        observeDailyQuote()
        observeDailyArticle()
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

    private fun observeDailyQuote() = quotesRepository.getQuoteOfTheDay()
        .debounce(timeoutMillis = 500)
        .onEach { quote ->
            _dailyQuote.update { quote }
        }
        .flowOn(Dispatchers.IO)
        .launchIn(viewModelScope)

    private fun observeDailyArticle() = getDailyArticle()
        .debounce(timeoutMillis = 500)
        .onEach { resource ->
            when (resource) {
                is Resource.Error -> _dailyArticle.update { Resource.Error(resource.error) }
                Resource.Loading -> _dailyArticle.update { Resource.Loading }
                is Resource.Success -> _dailyArticle.update {
                    Resource.Success(
                        data = UiArticle(
                            url = resource.data.url,
                            imagePath = resource.data.imagePath,
                            articleTitle = resource.data.articleName,
                            articleText = resource.data.shortText
                        )
                    )
                }

                else -> Unit
            }
        }
        .flowOn(Dispatchers.IO)
        .launchIn(viewModelScope)

    fun openUserStats() {
        navigator.navigate(PrimaryDirections.ActivityScreen.route())
    }

}