package nl.aldera.newsapp721447.presentation.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList

class FavArticlesListViewModel : ViewModel() {
    private val _favArticlesList = mutableListOf<Int>()

    private val mutableFavArticlesList = MutableStateFlow<List<Int>>(emptyList())
    val favArticlesList: StateFlow<List<Int>> = mutableFavArticlesList

//    private val mutableOpenedEntry = MutableStateFlow<Todo?>(null)
//    val openedEntry: StateFlow<Todo?> = mutableOpenedEntry

    fun contains(articleId : Int) : Boolean {
        return _favArticlesList.contains(articleId)
    }
    fun addFavArticle(articleId : Int) {
        _favArticlesList.add(articleId)
        mutableFavArticlesList.tryEmit(_favArticlesList.toList())
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

//    fun onEntryClicked(model: Todo) {
//        mutableOpenedEntry.tryEmit(
//            if (openedEntry.value == model) null else model
//        )
//    }
}