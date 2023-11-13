package nl.aldera.newsapp721447.presentation.viewModels.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import nl.aldera.newsapp721447.R
import nl.aldera.newsapp721447.data.model.Article

@Composable
fun ArticleDetailsBox(
    article: Article
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
//                .size(200.dp)
                .padding(8.dp),
            placeholder = painterResource(R.drawable.placeholder),
            model = article.Image,
            contentDescription = stringResource(R.string.article_image_description),
            contentScale = ContentScale.Fit
        )

        article.Title?.let {
            Text(text = it,
                style = MaterialTheme.typography.titleLarge)
        }

        Text(
            modifier = Modifier.padding(8.dp),
            text = article.Summary ?: "",
            style = MaterialTheme.typography.bodyLarge
        )

    }
}