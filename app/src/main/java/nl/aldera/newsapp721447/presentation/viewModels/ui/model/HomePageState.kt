package nl.aldera.newsapp721447.presentation.viewModels.ui.model

import nl.aldera.newsapp721447.data.model.AllArticlesContainer

sealed class HomePageState {
    data class Success(val allArticlesContainer: AllArticlesContainer) : HomePageState()
    data class Error(val message: String) : HomePageState()
    object Loading : HomePageState()
}