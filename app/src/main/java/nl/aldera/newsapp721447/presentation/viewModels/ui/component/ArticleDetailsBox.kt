package nl.aldera.newsapp721447.presentation.viewModels.ui.component

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.aldera.newsapp721447.R
import nl.aldera.newsapp721447.data.api.NewsApi
import nl.aldera.newsapp721447.data.model.Article
import nl.aldera.newsapp721447.data.model.SharedPreferencesManager
import nl.aldera.newsapp721447.presentation.viewModels.FavArticlesListViewModel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.time.format.TextStyle

@Composable
fun ArticleDetailsBox(
    article: Article,
    isFavoriteOnFirstAccess: Boolean,
    favArticlesListViewModel : FavArticlesListViewModel,
) {

    var isFavorite by remember { mutableStateOf(isFavoriteOnFirstAccess) }
//    Log.d("favorite list main page", favArticlesListViewModel.fetchFavoriteArticles().toString())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .size(420.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                placeholder = painterResource(R.drawable.placeholder),
                model = article.Image,
                contentDescription = stringResource(R.string.article_image_description),
                contentScale = ContentScale.Crop
            )
        }

        article.Title?.let {
            Text(
                text = it,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(16.dp)
            )
        }

        Text(
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Justify,
            text = article.Summary ?: "",
            style = MaterialTheme.typography.bodyLarge
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),// Expand the column to fill its parent container
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (SharedPreferencesManager.getAuthToken() != null) {
                IconButton(onClick = {

                        CoroutineScope(Dispatchers.IO).launch {
                            isFavorite = SharedPreferencesManager.getAuthToken()?.let {
                                toggleFavourite(
                                    it, article, isFavorite, favArticlesListViewModel)
                            } == true
                        }

                }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Liked",
                        tint = MaterialTheme.colorScheme.tertiary, // Set the color of the icon
                        modifier = Modifier
                            .size(140.dp) // Set the size of the icon as needed
                    )
                }
            }

        }

        var mAnnotatedLinkString: AnnotatedString? = null
        Box(modifier = Modifier.padding(start = 16.dp)) {
            mAnnotatedLinkString = buildAnnotatedString {
                val url = article.Url ?: ""

                // append the clickable text without a Text element
                // this avoids duplicating the text and simplifies the code
                pushStringAnnotation(
                    tag = "URL",
                    annotation = url
                )

                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(url)
                }

                pop()
            }
        }


// UriHandler parse and opens URI inside
// AnnotatedString Item in Browse
        val mUriHandler = LocalUriHandler.current

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // ClickableText with the annotated string
            mAnnotatedLinkString?.let {
                ClickableText(
                    text = it,
                    onClick = { offset ->
                        mAnnotatedLinkString!!.getStringAnnotations("URL", offset, offset)
                            .firstOrNull()?.let { stringAnnotation ->
                                mUriHandler.openUri(stringAnnotation.item)
                            }
                    }
                )
            }
        }


    }
}

suspend fun toggleFavourite(
    authToken: String,
    article: Article,
    isFavorite : Boolean,
    favArticlesListViewModel : FavArticlesListViewModel
) : Boolean {
    var articleId = article.Id

    var newFavValue : Boolean

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

    if (isFavorite) {
        Log.d("debug", "disliked")
        response = articleId?.let { api.dislikeArticle(it).awaitResponse() }
        val responseStatusCode = response?.code()
        Log.d("debug delete", "DELETE response status code: $responseStatusCode")
        if (articleId != null) {
            favArticlesListViewModel.removeFavArticle(articleId)
        }
        newFavValue = false
    } else {
        response = articleId?.let { api.likeArticle(it).awaitResponse() }
        val responseStatusCode = response?.code()
        if (articleId != null) {
            favArticlesListViewModel.addFavArticle(articleId)
        }
        Log.d("debug", "liked")
        Log.d("debug", "response status code: $responseStatusCode")
        Log.d("debug", favArticlesListViewModel.fetchFavoriteArticles().toString())
        newFavValue = true
    }
    return newFavValue
}