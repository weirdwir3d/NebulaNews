package com.wearetriple.exercise6.ui.page.main.component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.aldera.newsapp721447.data.model.AllArticlesContainer
import nl.aldera.newsapp721447.data.model.Article

@Composable
fun ArticleList(allArticlesContainer: AllArticlesContainer, onItemClick: (Article) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(8.dp)) {

        items(allArticlesContainer.Results.size) { index ->
            val article = allArticlesContainer.Results[index]
            ArticleItem(article) {
                onItemClick(article)
            }
        }
    }
}

@Composable
fun ArticleList(allArticlesContainer: AllArticlesContainer) {
    var article = allArticlesContainer.Results[0]
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        ArticleItem(article) {
            Log.i("article info", "article id: " + article.Id + "article title: " + article.Title)
        }
    }
}