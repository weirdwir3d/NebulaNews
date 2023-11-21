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
import nl.aldera.newsapp721447.data.model.SharedPreferencesManager
import nl.aldera.newsapp721447.extension.flatten
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.FavoritePageState
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AllFavoriteArticlesContainerViewModel : ViewModel() {
    var authToken : String? = SharedPreferencesManager.getAuthToken()

//    private val retrofit = Retrofit.Builder()
//        .baseUrl("https://inhollandbackend.azurewebsites.net/")
//        .addConverterFactory(MoshiConverterFactory.create())
//        .build()
//    private val api = retrofit.create<NewsApi>()

    private val responseMapper = ResponseMapper()

    private val mutableState = MutableStateFlow<FavoritePageState>(FavoritePageState.Loading)
    val state: StateFlow<FavoritePageState> = mutableState

    fun fetchLikedArticles(authToken : String?) {
        Log.d("errorino", "refresh method called")
        viewModelScope.launch {
            mutableState.tryEmit(FavoritePageState.Loading)
            val articlesContainer = getLikedArticles(authToken)
            Log.d("errorino", "mutable state FAV P: " + state.value.toString())

            try {
                mutableState.tryEmit(FavoritePageState.Success(articlesContainer.getOrNull()!!))
            } catch (e : Exception) {
                Log.i("errorino", "cant load articles")
            }

            if (articlesContainer.isSuccess) {
                mutableState.tryEmit(FavoritePageState.Success(articlesContainer.getOrNull()!!))
            } else {
                mutableState.tryEmit(
                    FavoritePageState.Error(articlesContainer.exceptionOrNull()?.message ?: "Unknown error")
                )
            }
        }
    }

    suspend fun fetchAllArticlesContainer(authToken: String?) : Result<AllArticlesContainer>? {
            return getLikedArticles(authToken)
    }

//    fun setApiCall(authToken : String?) {
//        var client : OkHttpClient
//        if (authToken != null) {
//            client = OkHttpClient.Builder().addInterceptor { chain ->
//                val newRequest = chain.request().newBuilder()
//                    .addHeader("x-authtoken", authToken)
//                    .build()
//                chain.proceed(newRequest)
//            }
//                .build()
//        } else {
//            client = OkHttpClient.Builder().build()
//        }
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://inhollandbackend.azurewebsites.net/")
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val api = retrofit.create(NewsApi::class.java)
//        api.getLikedArticles()
//    }


    private suspend fun getLikedArticles(authToken : String?): Result<AllArticlesContainer> {
        var client : OkHttpClient
        if (authToken != null) {
            client = OkHttpClient.Builder().addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("x-authtoken", authToken)
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
//        api.getLikedArticles()
        return runCatching { api.getLikedArticles() }
            .map(responseMapper::map).flatten()
//            .map(articleContainerMapper::mapList).flatten()
    }

    //    suspend fun getArticles(): Result<AllArticlesContainer> {
//        return runCatching { api.getArticles() }
//    }

}