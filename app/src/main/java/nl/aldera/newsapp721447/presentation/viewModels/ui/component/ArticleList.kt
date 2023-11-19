package com.wearetriple.exercise6.ui.page.main.component

import android.util.Log
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
import nl.aldera.newsapp721447.data.model.AllArticlesContainer
import nl.aldera.newsapp721447.data.model.Article
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel

@Composable
fun ArticleList(
    allArticlesContainer: AllArticlesContainer,
    onItemClick: (Article) -> Unit,
    userViewModel: UserViewModel
) {

    LazyColumn(contentPadding = PaddingValues(8.dp)) {

        items(allArticlesContainer.Results.size) { index ->
            val article = allArticlesContainer.Results[index]
            ArticleItem(userViewModel, article) {
                onItemClick(article)
            }


        }
    }
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