package nl.aldera.newsapp721447.presentation.viewModels.ui.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import nl.aldera.newsapp721447.R
import nl.aldera.newsapp721447.data.model.Article
import java.time.format.TextStyle

@Composable
fun ArticleDetailsBox(
    article: Article
) {
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

        var mAnnotatedLinkString : AnnotatedString? = null
        Box(modifier = Modifier.padding(16.dp)) {
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
            Modifier.fillMaxSize().padding(16.dp),
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