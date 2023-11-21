@file:JvmName("ArticleItemKt")

package com.wearetriple.exercise6.ui.page.main.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.aldera.newsapp721447.R
import nl.aldera.newsapp721447.data.api.NewsApi
import nl.aldera.newsapp721447.data.model.Article
import nl.aldera.newsapp721447.data.model.Session
import nl.aldera.newsapp721447.data.model.SharedPreferencesManager
import nl.aldera.newsapp721447.presentation.viewModels.FavArticlesListViewModel
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.FavoriteListState
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleItem(
    userViewModel: UserViewModel,
    favArticlesListViewModel: FavArticlesListViewModel,
    isFavouritesPage: Boolean,
    item: Article,
    onClick: () -> Unit
) {
    var isFavorite by remember { mutableStateOf(item.IsLiked) }
    var authToken = SharedPreferencesManager.getAuthToken()
    val sessionState by userViewModel.sessionState.collectAsState()

    Card(
        onClick = onClick,
        modifier = Modifier
//            .background(MaterialTheme.colorScheme.tertiary)
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        )
//            .background(MaterialTheme.colorScheme.tertiary)
    ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        placeholder = painterResource(R.drawable.placeholder),
                        model = item.Image,
                        contentDescription = stringResource(R.string.article_image_description),
                        contentScale = ContentScale.Crop
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(245.dp)
                ){
                    item.Title?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 3,
                            overflow = TextOverflow.Clip
                        )
                    }
                }



//                IconButton(onClick = {
//                    //todo
//                }) {
//                    Icon(imageVector = Icons.Filled.Favorite,
//                        contentDescription = stringResource(R.string.favorite_icon)
//                    )
//                }


                if (!isFavouritesPage && SharedPreferencesManager.getAuthToken() != null) {
                    IconButton(onClick = {
                        if (authToken != null) {
                            CoroutineScope(Dispatchers.IO).launch {
                                toggleFavorite(authToken, item, favArticlesListViewModel) { success ->
                                if (success) {
                                    // Change State from isFavorite
                                    isFavorite = item.IsLiked

//                                    if (!isFavorite) {
//                                        viewModel.removeLikedArticle(article)
//                                    }
                                } else {
                                    Log.d("fav debug", "error trying to like/dislike")
                                }


                                }
                            }
                        }
                    }) {
                        Icon(
                            imageVector = when {
                                favArticlesListViewModel.favArticlesList.value.contains(item.Id) -> {
                                    // Use the filled favorite icon
                                    Icons.Filled.Favorite
                                }
                                else -> {
                                    // Use the outlined favorite border icon
                                    Icons.Outlined.FavoriteBorder
                                }
                            },
                            contentDescription = stringResource(R.string.favorite_icon)
                        )
                    }
                }

            }


    }
}

suspend fun toggleFavorite(
    authToken: String,
    article: Article,
    favArticlesListViewModel: FavArticlesListViewModel,
    callback: (Boolean) -> Unit
) {
    var Id: Int = -1
    if (article.Id != null) {
        Id = article.Id
    }
    Log.d("debug", "toggling fav")
    val client: OkHttpClient

    if (SharedPreferencesManager.getAuthToken() != null) {
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

    var response: Response<Unit>? = null

    if (favArticlesListViewModel.contains(Id)) {
        Log.d("debug", "disliked")
        response = api.dislikeArticle(Id).awaitResponse()
        val responseStatusCode = response.code()
        Log.d("debug delete", "DELETE response status code: $responseStatusCode")
        favArticlesListViewModel.removeFavArticle(Id)
    } else {
        response = api.likeArticle(Id).awaitResponse()
        val responseStatusCode = response.code()
        Log.d("debug", "liked")
        Log.d("debug", "response status code: $responseStatusCode")
        Log.d("debug", favArticlesListViewModel.fetchFavoriteArticles().toString())
    }

    if (response?.code() == 200) {
        callback(true)
    } else {
        callback(false)
    }
}



//@Composable
//suspend fun AddToFavorites(articleId: Int, authToken : String) {
//
//    RetrofitInstance.newsApi.likeArticle(getHeaderMap(authToken), articleId)
//
//    var okHttpClient = OkHttpClient.Builder().apply {
//        addInterceptor(
//            Interceptor { chain ->
//                val builder = chain.request().newBuilder()
//                builder.header("x-authtoken", authToken)
//                return@Interceptor chain.proceed(builder.build())
//            }
//        )
//    }.build()
//
////    Retrofit.Builder()
////        .baseUrl("https://inhollandbackend.azurewebsites.net/")
////        .addConverterFactory(GsonConverterFactory.create())
////        .addHeader("x-authtoken", "Bearer $authToken")
////        .build()
////
////        .put(requestBody)
////        .build()
//
//    LaunchedEffect(key1 = Unit) {
//        try {
//            val response = okHttpClient.execute(request)
//            if (response.isSuccessful) {
//                // Handle successful PUT request
//                Log.i("INFO", "Article added to favorites")
//            } else {
//                val error = "Error: ${response.statusCode}"
//                Log.e("ERROR", error)
//            }
//        } catch (e: Exception) {
//            val error = "Error: ${e.message}"
//            Log.e("ERROR", error)
//        }
//    }
//}