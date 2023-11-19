package nl.aldera.newsapp721447.presentation.viewModels.ui.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FavoriteListState(initialValue: List<Int> = emptyList()) {
    private val _favArticlesList = MutableStateFlow(initialValue)
    val favArticlesList: StateFlow<List<Int>> = _favArticlesList

    fun addFavArticle(articleId: Int) {
        val newList = _favArticlesList.value.toMutableList()
        newList.add(articleId)
        _favArticlesList.value = newList
    }

    fun removeFavArticle(articleId: Int) {
        if (articleId in _favArticlesList.value) {
            val newList = _favArticlesList.value.toMutableList()
            newList.remove(articleId)
            _favArticlesList.value = newList
        }
    }

    fun clear() {
        _favArticlesList.value = emptyList()
    }

    fun getFavArticlesList(): List<Int> {
        return _favArticlesList.value
    }
}