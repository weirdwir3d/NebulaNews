package nl.aldera.newsapp721447.presentation.viewModels.ui.model

import nl.aldera.newsapp721447.data.model.AllArticlesContainer

sealed class PageState {
    data class Success(val allArticlesContainer: AllArticlesContainer) : PageState()
    data class Error(val message: String) : PageState()
    object Loading : PageState()
}