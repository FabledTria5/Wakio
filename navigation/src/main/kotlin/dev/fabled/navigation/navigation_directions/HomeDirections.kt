package dev.fabled.navigation.navigation_directions

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import dev.fabled.navigation.navigation_core.NavigationDestination

object HomeDirections {

    const val HOME_DIRECTION = "home_direction"

    object HomeScreen : NavigationDestination {

        override fun route(): String = HOME_BOTTOM_DESTINATION_ROUTE

        private const val HOME_BOTTOM_DESTINATION_ROUTE = "home_screen"

    }

    object NotificationsScreen : NavigationDestination {

        override fun route(): String = NOTIFICATIONS_SCREEN_DESTINATION_ROUTE

        private const val NOTIFICATIONS_SCREEN_DESTINATION_ROUTE = "notifications_screen"

    }

    object ArticlesScreen : NavigationDestination {

        override fun route(): String = ARTICLES_SCREEN_ROUTE

        override val arguments: List<NamedNavArgument>
            get() = listOf(navArgument(name = ARTICLE_URL) { type = NavType.StringType })

        const val ARTICLE_URL = "article_url"

        private const val ARTICLES_SCREEN_DESTINATION_ROUTE = "articles_screen"
        private const val ARTICLES_SCREEN_ROUTE = "$ARTICLES_SCREEN_DESTINATION_ROUTE/{$ARTICLE_URL}"

        fun createArticlesRoute(articleUrl: String) =
            "$ARTICLES_SCREEN_DESTINATION_ROUTE/$articleUrl"

    }

}