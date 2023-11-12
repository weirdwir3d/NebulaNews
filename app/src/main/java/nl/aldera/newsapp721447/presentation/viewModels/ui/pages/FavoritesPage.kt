package nl.aldera.newsapp721447.presentation.viewModels.ui.pages

import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffold


@Composable
@Preview
fun FavoritesPage(
//    navController : NavController
) {

    AppScaffold(
        title = "Favorites",
        ) {
        Column(Modifier.padding(it)) {
            Text(text = "hello")
            Text(text = "hello")
            Text(text = "hello")
        }
    }

}