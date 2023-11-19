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
import nl.aldera.newsapp721447.data.model.Session
import nl.aldera.newsapp721447.extension.flatten
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.PageState
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class AllFavoriteArticlesContainerViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://inhollandbackend.azurewebsites.net/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    private val api = retrofit.create<NewsApi>()

    private val responseMapper = ResponseMapper()

    private val mutableState = MutableStateFlow<PageState>(PageState.Loading)
    val state: StateFlow<PageState> = mutableState

    init {
//        setApiCall(sessionState)
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            mutableState.tryEmit(PageState.Loading)
            val articlesContainer = getLikedArticles()
            try {
                mutableState.tryEmit(PageState.Success(articlesContainer.getOrNull()!!))
            } catch (e : Exception) {
                Log.i("errorino", "cant load articles")
            }

            if (articlesContainer.isSuccess) {
                mutableState.tryEmit(PageState.Success(articlesContainer.getOrNull()!!))
            } else {
                mutableState.tryEmit(
                    PageState.Error(articlesContainer.exceptionOrNull()?.message ?: "Unknown error")
                )
            }
        }
    }

    fun setApiCall(sessionState : Session) {
        var client : OkHttpClient
        if (sessionState.AuthToken != null) {
            client = OkHttpClient.Builder().addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("x-authtoken", sessionState.AuthToken.toString())
                    .build()
                chain.proceed(newRequest)
            }
                .build()
        } else {
            client = OkHttpClient.Builder().build()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://inhollandbackend.azurewebsites.net/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(NewsApi::class.java)
        api.getLikedArticles()
    }


    fun getLikedArticles(): Result<AllArticlesContainer> {
        return runCatching { api.getLikedArticles() }
            .map(responseMapper::map).flatten()
//            .map(articleContainerMapper::mapList).flatten()
    }

    //    suspend fun getArticles(): Result<AllArticlesContainer> {
//        return runCatching { api.getArticles() }
//    }

}