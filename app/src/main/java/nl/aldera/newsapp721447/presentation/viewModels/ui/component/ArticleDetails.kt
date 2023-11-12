package nl.aldera.newsapp721447.presentation.viewModels.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import nl.aldera.newsapp721447.data.model.Article

@Composable
fun ArticleDetails(
    article : Article
) {
    Text(text = article.Title)
}