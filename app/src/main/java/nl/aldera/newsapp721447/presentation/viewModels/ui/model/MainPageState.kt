package nl.aldera.newsapp721447.presentation.viewModels.ui.model

import nl.aldera.newsapp721447.data.model.AllArticlesContainer
import nl.aldera.newsapp721447.data.model.Article

sealed class MainPageState {
    data class Success(val allArticlesContainer: AllArticlesContainer) : MainPageState()
    data class Error(val message: String) : MainPageState()
    object Loading : MainPageState()
}