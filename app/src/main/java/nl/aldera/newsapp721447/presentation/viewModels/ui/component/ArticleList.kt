package com.wearetriple.exercise6.ui.page.main.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import nl.aldera.newsapp721447.data.model.AllArticlesContainer
import nl.aldera.newsapp721447.data.model.Article
import nl.aldera.newsapp721447.presentation.viewModels.FavArticlesListViewModel
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.FavoriteListState

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ArticleList(
    allArticlesContainer: AllArticlesContainer,
    favArticlesListViewModel : FavArticlesListViewModel,
    onItemClick: (Article) -> Unit,
    userViewModel: UserViewModel,
    isDisplaying : Boolean,
    isFavouritesPage : Boolean
) {

    val favArticlesMutableState = MutableStateFlow<List<Int>>(emptyList())
//    val favArticlesState: StateFlow<FavoriteListState> = favArticlesMutableState
//    val favArticlesMutableState = MutableStateFlow<FavoriteListState>(FavoriteListState())
//    val favArticlesState: StateFlow<FavoriteListState> = favArticlesMutableState

    if (isDisplaying) {
        LazyColumn(contentPadding = PaddingValues(8.dp)) {

            items(allArticlesContainer.Results.size) { index ->
                val article = allArticlesContainer.Results[index]

                if (article.IsLiked == true && article.Id?.let {
                        favArticlesListViewModel.contains(
                            it
                        )
                    } == false) {
                    article.Id.let { favArticlesListViewModel.addFavArticle(it) }
                }
                //            else if (article.IsLiked == true)

                ArticleItem(userViewModel,
                    favArticlesListViewModel,
                    isFavouritesPage = isFavouritesPage,
                    article) {
                    onItemClick(article)
                }


            }
        }
    } else {
        for (article in allArticlesContainer.Results) {
            if (article.IsLiked == true && article.Id?.let {
                    favArticlesListViewModel.contains(
                        it
                    )
                } == false) {
                Log.d("favorite list added from ArticleList: ", favArticlesListViewModel.favArticlesList.value.toString())
                article.Id.let { favArticlesListViewModel.addFavArticle(it) }
            }
        }

    }

    Log.d("favorite list", favArticlesListViewModel.favArticlesList.value.toString())
}

//IconButton(
//onClick = { /* Handle click event */ },
//modifier = Modifier.size(48.dp)
//) {
//    Icon(
//        imageVector = Icons.Filled.Favorite,
//        contentDescription = "Favorite"
//    )
//}

//@Composable
//fun ArticleList(allArticlesContainer: AllArticlesContainer) {
//    var article = allArticlesContainer.Results[0]
//    Column(
//        modifier = Modifier
//            .padding(8.dp)
//    ) {
//        ArticleItem(userViewModel, article) {
//            Log.i("article info", "article id: " + article.Id + "article title: " + article.Title)
//        }
//    }
//}