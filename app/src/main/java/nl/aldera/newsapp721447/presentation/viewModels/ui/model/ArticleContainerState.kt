package nl.aldera.newsapp721447.presentation.viewModels.ui.model

import nl.aldera.newsapp721447.data.model.AllArticlesContainer
import nl.aldera.newsapp721447.data.model.Article

sealed class ArticleContainerState {
    data class Success(
        val allArticlesContainer: AllArticlesContainer
    ): ArticleContainerState()

    object Loading: ArticleContainerState()

    data class Error(val message: String): ArticleContainerState()
}
