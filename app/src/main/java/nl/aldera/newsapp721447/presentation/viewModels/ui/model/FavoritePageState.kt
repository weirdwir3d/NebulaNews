package nl.aldera.newsapp721447.presentation.viewModels.ui.model

import nl.aldera.newsapp721447.data.model.AllArticlesContainer

sealed class FavoritePageState {
    data class Success(val allArticlesContainer: AllArticlesContainer) : FavoritePageState()
    data class Error(val message: String) : FavoritePageState()
    object Loading : FavoritePageState()
}