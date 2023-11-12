package nl.aldera.newsapp721447.presentation.viewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nl.aldera.newsapp721447.data.api.NewsApi
import nl.aldera.newsapp721447.data.mapper.ResponseMapper
import nl.aldera.newsapp721447.data.model.AllArticlesContainer
import nl.aldera.newsapp721447.data.model.Article
import nl.aldera.newsapp721447.extension.flatten
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.ArticleContainerState
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.MainPageState
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Inject

@HiltViewModel

class ArticleViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://inhollandbackend.azurewebsites.net/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    private val api = retrofit.create<NewsApi>()

    private val responseMapper = ResponseMapper()
    private val mutableState = MutableStateFlow<ArticleContainerState>(ArticleContainerState.Loading)
    val state: StateFlow<ArticleContainerState> = mutableState

    init {
        viewModelScope.launch {
            savedStateHandle.getStateFlow("articleId", -1)
                .collectLatest { articleId ->
//                    getArticle(articleId)
                }
        }
    }

//    init {
//        refresh()
//    }

    private fun refresh() {
//        viewModelScope.launch {
//            mutableState.tryEmit(ArticleContainerState.Loading)
////            val articleContainer = getArticle(articleId)
//            Log.e("DIOPORCO", articleContainer.toString())
////            Log.w("WARNING", articlesContainer.toString())
//            mutableState.tryEmit(ArticleContainerState.Success(articleContainer.getOrNull()!!))
//            if (articleContainer.isSuccess) {
//                mutableState.tryEmit(ArticleContainerState.Success(articleContainer.getOrNull()!!))
//            } else {
//                mutableState.tryEmit(
//                    ArticleContainerState.Error(articleContainer.exceptionOrNull()?.message ?: "Unknown error")
//                )
//            }
//        }
    }

//    suspend fun getArticle(articleId : Int): Result<Article> {
//        return runCatching { api.getArticle(articleId)
//            .onSuccess()
//        }
//            .map(responseMapper::map).flatten()
//    }
}

private fun <T> Response<T>.onSuccess() {
    TODO("Not yet implemented")
}
