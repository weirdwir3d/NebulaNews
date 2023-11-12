package nl.aldera.newsapp721447.presentation.viewModels.ui.model

import nl.aldera.newsapp721447.data.model.Article

sealed class DetailState {
    data class Success(
        val article : Article
    ) : DetailState()

    object Loading: DetailState()

    data class Error(val message : String) : DetailState()
}
