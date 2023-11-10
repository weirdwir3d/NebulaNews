package nl.aldera.newsapp721447.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.aldera.newsapp721447.data.api.NewsApi
import nl.aldera.newsapp721447.data.mapper.ResponseMapper
import nl.aldera.newsapp721447.data.model.AllArticlesContainer
import nl.aldera.newsapp721447.extension.flatten
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.ArticleContainerState
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.MainPageState
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class ArticleViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://inhollandbackend.azurewebsites.net/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    private val api = retrofit.create<NewsApi>()

    private val responseMapper = ResponseMapper()
    private val mutableState = MutableStateFlow<ArticleContainerState>(ArticleContainerState.Loading)
    val state: StateFlow<ArticleContainerState> = mutableState

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            mutableState.tryEmit(ArticleContainerState.Loading)
            val articleContainer = getArticle()
            Log.e("DIOPORCO", articleContainer.toString())
//            Log.w("WARNING", articlesContainer.toString())
            mutableState.tryEmit(ArticleContainerState.Success(articleContainer.getOrNull()!!))
            if (articleContainer.isSuccess) {
                mutableState.tryEmit(ArticleContainerState.Success(articleContainer.getOrNull()!!))
            } else {
                mutableState.tryEmit(
                    ArticleContainerState.Error(articleContainer.exceptionOrNull()?.message ?: "Unknown error")
                )
            }
        }
    }

    suspend fun getArticle(): Result<AllArticlesContainer> {
        return runCatching { api.getArticle(134068) }
            .map(responseMapper::map).flatten()
    }
}