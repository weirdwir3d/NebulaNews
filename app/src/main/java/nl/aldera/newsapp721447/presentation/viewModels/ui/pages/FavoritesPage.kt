package nl.aldera.newsapp721447.presentation.viewModels.ui.pages

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.wearetriple.exercise6.ui.page.main.component.ArticleList
import com.wearetriple.exercise6.ui.page.main.component.ErrorMessage
import com.wearetriple.exercise6.ui.page.main.component.LoadingIndicator
import nl.aldera.newsapp721447.data.model.Article
import nl.aldera.newsapp721447.data.model.SharedPreferencesManager
import nl.aldera.newsapp721447.presentation.viewModels.AllFavoriteArticlesContainerViewModel
import nl.aldera.newsapp721447.presentation.viewModels.FavArticlesListViewModel
import nl.aldera.newsapp721447.presentation.viewModels.FeedsListViewModel
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffold
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.FavoritePageState


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FavoritesPage(
    navController: NavController,
    userViewModel: UserViewModel,
    feedsListViewModel: FeedsListViewModel,
    allFavoriteArticlesContainerViewModel: AllFavoriteArticlesContainerViewModel,
    favArticlesListViewModel: FavArticlesListViewModel,
    context: Context,
    onItemClick: (Article) -> Unit,
    function: () -> Boolean
) {
    val favArticlesState by allFavoriteArticlesContainerViewModel.state.collectAsState()
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    SharedPreferencesManager.getAuthToken()?.let { Log.d("manager", it) }
    SharedPreferencesManager.getUserName()?.let { Log.d("manager", it) }

    val sessionState by userViewModel.sessionState.collectAsState()
    val login = SharedPreferencesManager.isLoggedIn()

    LaunchedEffect(Unit) {
        allFavoriteArticlesContainerViewModel.fetchLikedArticles(SharedPreferencesManager.getAuthToken())
    }

    AppScaffold(
        title = "Favorite articles",
        navController = navController,
        context = context,
        content = {
            Column(Modifier.padding(it)) {
                if (SharedPreferencesManager.getAuthToken() != null) {
                    when (val favArticlesState = favArticlesState) {
                        is FavoritePageState.Loading -> LoadingIndicator()
                        is FavoritePageState.Success -> ArticleList(
                            allArticlesContainer = favArticlesState.allArticlesContainer,
                            favArticlesListViewModel,
                            onItemClick = onItemClick,
                            userViewModel = userViewModel,
                            feedsListViewModel = feedsListViewModel,
                            isDisplaying = true,
                            isFavouritesPage = true
                        )
                        is FavoritePageState.Error -> ErrorMessage()
                    }



//                CoroutineScope(Dispatchers.IO).launch {
//
//                    allFavoriteArticlesContainerViewModel.refresh(SharedPreferencesManager.getAuthToken())
//
//                }

                } else {
                    Text("Log in to see your favorite articles")
                }

            }
            //
        }
        )

}