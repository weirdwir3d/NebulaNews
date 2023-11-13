package nl.aldera.newsapp721447.presentation.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.aldera.newsapp721447.RetrofitInstance
import nl.aldera.newsapp721447.data.api.NewsApi
//import nl.aldera.newsapp721447.data.mapper.ArticleContainerMapper
import nl.aldera.newsapp721447.data.mapper.ResponseMapper
import nl.aldera.newsapp721447.data.model.AllArticlesContainer
import nl.aldera.newsapp721447.data.model.Article
import nl.aldera.newsapp721447.domain.repository.AllArticlesContainerRepository
import nl.aldera.newsapp721447.extension.flatten
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.MainPageState
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class AllArticlesContainerViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://inhollandbackend.azurewebsites.net/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    private val api = retrofit.create<NewsApi>()

    private val repository = AllArticlesContainerRepository()

    private val responseMapper = ResponseMapper()

    private val mutableState = MutableStateFlow<MainPageState>(MainPageState.Loading)
    val state: StateFlow<MainPageState> = mutableState


    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            mutableState.tryEmit(MainPageState.Loading)
            val articlesContainer = getArticles()
            Log.e("DIOPORCO", articlesContainer.toString())
//            Log.w("WARNING", articlesContainer.toString())
            mutableState.tryEmit(MainPageState.Success(articlesContainer.getOrNull()!!))
            if (articlesContainer.isSuccess) {
                mutableState.tryEmit(MainPageState.Success(articlesContainer.getOrNull()!!))
            } else {
                mutableState.tryEmit(
                    MainPageState.Error(articlesContainer.exceptionOrNull()?.message ?: "Unknown error")
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



