package nl.aldera.newsapp721447.presentation.viewModels.ui.pages

import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffold


@Composable
fun FavoritesPage(
    navController : NavController,
    userViewModel: UserViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val sessionState by userViewModel.sessionState.collectAsState()

    AppScaffold(
        title = "Favorites",
        navController = navController
        ) {
        Column(Modifier.padding(it)) {
            Text(text = "hello")
            Text(text = "hello")
            Text(text = "hello")
        }
    }

}