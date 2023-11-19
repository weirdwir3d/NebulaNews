package nl.aldera.newsapp721447.presentation.viewModels.ui.pages

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import nl.aldera.newsapp721447.presentation.viewModels.AllArticlesContainerViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffold
import androidx.navigation.NavController
import com.wearetriple.exercise6.ui.page.main.component.ArticleList
import com.wearetriple.exercise6.ui.page.main.component.ErrorMessage
import com.wearetriple.exercise6.ui.page.main.component.LoadingIndicator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import nl.aldera.newsapp721447.data.model.Article
import nl.aldera.newsapp721447.data.model.SharedPreferencesManager
import nl.aldera.newsapp721447.presentation.viewModels.AllFavoriteArticlesContainerViewModel
import nl.aldera.newsapp721447.presentation.viewModels.FavArticlesListViewModel
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.DetailState
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.FavoriteListState
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.FavoritePageState
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.HomePageState

@SuppressLint("ServiceCast")
@Composable
fun ArticlesPage(
    navController : NavController,
    articlesViewModel: AllArticlesContainerViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    allFavoriteArticlesContainerViewModel: AllFavoriteArticlesContainerViewModel,
    favArticlesListViewModel: FavArticlesListViewModel,
    userViewModel: UserViewModel,
    onItemClick: (Article) -> Unit
) {

    val favArticlesState by allFavoriteArticlesContainerViewModel.state.collectAsState()
    SharedPreferencesManager.getAuthToken()?.let { Log.d("token: ", it) }
    val articlesState by articlesViewModel.state.collectAsState()

    val favArticlesMutableState = MutableStateFlow<FavoriteListState>(FavoriteListState())
//    val favArticlesState: StateFlow<FavoriteListState> = favArticlesMutableState

    LaunchedEffect(Unit) {
        articlesViewModel.getArticles()
        if (SharedPreferencesManager.getAuthToken() != null) {
//            Log.d("favorite", "method in main page")
            allFavoriteArticlesContainerViewModel.refresh(SharedPreferencesManager.getAuthToken())
//            Log.d("favorite list", favArticlesListViewModel.fetchFavoriteArticles().toString())
        }
    }

    AppScaffold(
        title = "Home",
        navController = navController
    ) {
        Column(Modifier.padding(it)) {

            when (val articlesState = articlesState) {
                is HomePageState.Loading -> LoadingIndicator()
                is HomePageState.Success -> ArticleList(
                    allArticlesContainer = articlesState.allArticlesContainer,
                    favArticlesListViewModel,
                    onItemClick = onItemClick,
                    userViewModel,
                    isDisplaying = true
                )

                is HomePageState.Error -> ErrorMessage()
            }

            if (SharedPreferencesManager.getAuthToken() != null) {
                Log.d("favorites", "showing fav articles")
                when (val favArticlesState = favArticlesState) {
                    is FavoritePageState.Loading -> LoadingIndicator()
                    is FavoritePageState.Success -> ArticleList(
                        allArticlesContainer = favArticlesState.allArticlesContainer,
                        favArticlesListViewModel,
                        onItemClick = onItemClick,
                        userViewModel,
                        isDisplaying = false
                    )

                    is FavoritePageState.Error -> ErrorMessage()
                }
            }


        }

    }

    LaunchedEffect(Unit) {
        if (SharedPreferencesManager.getAuthToken() != null) {
            Log.d("favorite", "method in main page")
            allFavoriteArticlesContainerViewModel.refresh(SharedPreferencesManager.getAuthToken())
            Log.d("favorite list", favArticlesListViewModel.fetchFavoriteArticles().toString())
        }
    }

}


