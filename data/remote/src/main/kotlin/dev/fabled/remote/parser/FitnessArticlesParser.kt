package dev.fabled.remote.parser

import dev.fabled.remote.dto.articles.ArticleDto
import org.jsoup.Jsoup
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class FitnessArticlesParser : FitnessApi {

    companion object {
        private const val FITNESS_API_REFERENCE =
            "https://www.everydayhealth.com/fitness/all-articles/"
    }

    override suspend fun getDailyArticle(): ArticleDto {
        val doc = Jsoup.connect(FITNESS_API_REFERENCE).get()

        val primaryArticleContainer =
            doc.getElementsByClass("category-index-article__container").first()

        val articleImagePath = primaryArticleContainer
            ?.getElementsByClass("eh-image__img")
            ?.attr("data-src")
            .orEmpty()

        val articleUrl = primaryArticleContainer
            ?.getElementsByClass("cr-anchor")
            ?.attr("href")
            .orEmpty()

        val articleName = primaryArticleContainer
            ?.getElementsByClass("category-index-article__title")
            ?.text()
            .orEmpty()

        val shortArticleText = primaryArticleContainer
            ?.getElementsByClass("category-index-article__dek")
            ?.text()
            .orEmpty()

        val articleDate = primaryArticleContainer
            ?.getElementsByClass("category-index-article__date")
            ?.text()
            .orEmpty()

        val articleEpochDay = if (articleDate.isEmpty()) {
            LocalDate.parse(
                articleDate,
                DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH)
            ).toEpochDay()
        } else {
            LocalDate.now().toEpochDay()
        }

        return ArticleDto(
            url = articleUrl,
            imagePath = articleImagePath,
            articleName = articleName,
            shortText = shortArticleText,
            articleDate = articleEpochDay
        )
    }

}