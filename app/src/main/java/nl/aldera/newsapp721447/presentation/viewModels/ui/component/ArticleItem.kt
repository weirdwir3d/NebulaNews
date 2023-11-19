@file:JvmName("ArticleItemKt")

package com.wearetriple.exercise6.ui.page.main.component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import nl.aldera.newsapp721447.R
import nl.aldera.newsapp721447.data.api.NewsApi
import nl.aldera.newsapp721447.data.model.Article
import nl.aldera.newsapp721447.data.model.Session
import nl.aldera.newsapp721447.data.model.SharedPreferencesManager
import nl.aldera.newsapp721447.presentation.viewModels.FavArticlesListViewModel
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.FavoriteListState
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleItem(
    userViewModel: UserViewModel,
    favArticlesListViewModel: FavArticlesListViewModel,
    item: Article,
    onClick: () -> Unit
) {

    val sessionState by userViewModel.sessionState.collectAsState()
//    var isFavorite by remember { mutableStateOf(false) }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(8.dp),
                    placeholder = painterResource(R.drawable.placeholder),
                    model = item.Image,
                    contentDescription = stringResource(R.string.article_image_description),
                    contentScale = ContentScale.Fit
                )

                Column(Modifier.padding(end = 8.dp)) {
                    item.Title?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
//                    Text(
//                        text = stringResource(
//                            R.string.exercise6_price,
//                            DecimalFormat("##.00").format(item.price)
//                        ),
//                        style = MaterialTheme.typography.bodyMedium,
//                        fontStyle = FontStyle.Italic
//                    )
                }
            }

            Text(
                modifier = Modifier.padding(8.dp),
                text = item.Summary ?: "",
                style = MaterialTheme.typography.bodyMedium
            )

            IconButton(onClick = {
                Log.i("INFO", "clicked on fav")
                toggleFavorite(sessionState, item)
            }) {
                Icon(
                    imageVector = if (item.Id?.let { favArticlesListViewModel.contains(it) } == true) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    contentDescription = "Favorites"
                )
            }
            item.Id?.let { favArticlesListViewModel.contains(it).toString() }
                ?.let { Log.d("does it contain favorite", it) }


        }
    }
}

fun toggleFavorite(session: Session, article: Article) {
    val client: OkHttpClient

    if (SharedPreferencesManager.getAuthToken() != null) {
        client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("x-authtoken", session.AuthToken.toString())
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


    Log.d("favorites", "token: " + session.AuthToken)
    Log.d("favorites", "IsLiked: " + article.IsLiked)
    if (article.IsLiked == true) {
        Log.d("favorites", "disliked article")
    } else {
        Log.d("favorites", "liked article")
        article.Id?.let {
            api.likeArticle(article.Id)
            Log.d("favorites","IsLiked: " + article.IsLiked)
        }

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

private fun getHeaderMap(authToken: String): Map<String, String> {
    val headerMap = mutableMapOf<String, String>()
    headerMap["x-authtoken"] = authToken
    return headerMap
}