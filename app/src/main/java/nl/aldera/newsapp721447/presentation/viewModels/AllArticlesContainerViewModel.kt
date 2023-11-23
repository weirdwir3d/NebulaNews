package nl.aldera.newsapp721447.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.aldera.newsapp721447.data.api.NewsApi
//import nl.aldera.newsapp721447.data.mapper.ArticleContainerMapper
import nl.aldera.newsapp721447.data.mapper.ResponseMapper
import nl.aldera.newsapp721447.data.model.AllArticlesContainer
import nl.aldera.newsapp721447.extension.flatten
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.HomePageState
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class AllArticlesContainerViewModel: ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://inhollandbackend.azurewebsites.net/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    private val api = retrofit.create<NewsApi>()

    private val responseMapper = ResponseMapper()

    private val mutableState = MutableStateFlow<HomePageState>(HomePageState.Loading)
    val state: StateFlow<HomePageState> = mutableState


    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            mutableState.tryEmit(HomePageState.Loading)
            val articlesContainer = getArticles()
            Log.d("errorino", "mutable state Main P: " + state.value.toString())
            try {
                mutableState.tryEmit(HomePageState.Success(articlesContainer.getOrNull()!!))
            } catch (e : Exception) {
                Log.i("errorino", "cant load articles")
            }

            if (articlesContainer.isSuccess) {
                mutableState.tryEmit(HomePageState.Success(articlesContainer.getOrNull()!!))
            } else {
                mutableState.tryEmit(
                    HomePageState.Error(articlesContainer.exceptionOrNull()?.message ?: "Unknown error")
                )
            }
        }
    }


    suspend fun getArticles(): Result<AllArticlesContainer> {
        return runCatching { api.getArticles() }
            .map(responseMapper::map).flatten()
//            .map(articleContainerMapper::mapList).flatten()
    }

    //    suspend fun getArticles(): Result<AllArticlesContainer> {
//        return runCatching { api.getArticles() }
//    }

}



