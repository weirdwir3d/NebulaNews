package nl.aldera.newsapp721447.presentation.viewModels.ui.pages

import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import nl.aldera.newsapp721447.data.model.SharedPreferencesManager
import nl.aldera.newsapp721447.presentation.viewModels.AllFavoriteArticlesContainerViewModel
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffold


@Composable
fun FavoritesPage(
    navController : NavController,
    userViewModel: UserViewModel,
    allFavoriteArticlesContainerViewModel: AllFavoriteArticlesContainerViewModel
) {

    val sessionState by userViewModel.sessionState.collectAsState()
    val login = SharedPreferencesManager.LoginStatus()

    AppScaffold(
        title = "Favorite articles",
        navController = navController
        ) {
        Column(Modifier.padding(it)) {
            if (sessionState.AuthToken != null) {
                allFavoriteArticlesContainerViewModel.getLikedArticles()
            } else {
                Text("Log in to see your favorite articles")
            }

            LaunchedEffect(sessionState) {
                Log.e("INFO", "launched")
                if (!sessionState.UserName.isNullOrBlank()) {
                    Log.i("INFO", "LOGGED IN (FAV PAGE)")
                } else {
                    Log.i("INFO", "NOT LOGGED IN (FAV PAGE)")
                }
            }
        }
    }

}