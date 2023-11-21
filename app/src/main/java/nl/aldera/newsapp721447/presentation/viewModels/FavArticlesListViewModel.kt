package nl.aldera.newsapp721447.presentation.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList

class FavArticlesListViewModel : ViewModel() {
    private val _favArticlesList = mutableListOf<Int>()

    private val mutableFavArticlesList = MutableStateFlow<List<Int>>(emptyList())
    val favArticlesList: StateFlow<List<Int>> = mutableFavArticlesList

    fun getSize() : Int {
        return _favArticlesList.size
    }

    fun contains(articleId : Int) : Boolean {
        return _favArticlesList.contains(articleId)
    }
    fun addFavArticle(articleId : Int) : Boolean {
        if (!_favArticlesList.contains(articleId)) {
            _favArticlesList.add(articleId)
            mutableFavArticlesList.tryEmit(_favArticlesList.toList())
            return true
        }
        return false
    }

    fun removeFavArticle(articleId : Int) {
        _favArticlesList.remove(articleId)
        mutableFavArticlesList.tryEmit(_favArticlesList.toList())
    }

    suspend fun fetchFavoriteArticles(): List<Int> {
        // Make the API call to fetch the list of favorite articles
        // ...
        return _favArticlesList.toList()// Replace with actual fetched data
    }

    fun clearList() {
        mutableFavArticlesList.value = emptyList()
    }
}