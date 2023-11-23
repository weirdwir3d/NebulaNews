package nl.aldera.newsapp721447.presentation.viewModels.ui.pages

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import nl.aldera.newsapp721447.presentation.viewModels.AllArticlesContainerViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffold
import androidx.navigation.NavController
import com.wearetriple.exercise6.ui.page.main.component.ArticleList
import com.wearetriple.exercise6.ui.page.main.component.ErrorMessage
import com.wearetriple.exercise6.ui.page.main.component.LoadingIndicator
import kotlinx.coroutines.flow.MutableStateFlow
import nl.aldera.newsapp721447.data.model.Article
import nl.aldera.newsapp721447.data.model.SharedPreferencesManager
import nl.aldera.newsapp721447.presentation.viewModels.AllFavoriteArticlesContainerViewModel
import nl.aldera.newsapp721447.presentation.viewModels.FavArticlesListViewModel
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.FavoriteListState
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.FavoritePageState
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.HomePageState

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("ServiceCast")
@Composable
fun ArticlesPage(
    navController : NavController,
    articlesViewModel: AllArticlesContainerViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    allFavoriteArticlesContainerViewModel: AllFavoriteArticlesContainerViewModel,
    favArticlesListViewModel: FavArticlesListViewModel,
    userViewModel: UserViewModel,
    isFavouritesPage : Boolean,
    onItemClick: (Article) -> Unit,
    context : Context
) {
    val favArticlesState by favArticlesListViewModel.favArticlesList.collectAsState()
    val favArticlesSize by remember { mutableStateOf(favArticlesListViewModel.getSize()) }
    var nrFavArticles by remember { mutableStateOf(favArticlesSize) }
//    val favArticlesState by favArticlesListViewModel.favArticlesList.collectAsState()
//    val favArticlesSize = favArticlesListViewModel.getSize()
//    var nrFavArticles by remember { mutableIntStateOf(favArticlesState.size) }
    Log.d("state main", favArticlesState.toString())
    SharedPreferencesManager.getAuthToken()?.let { Log.d("token: ", it) }
    val articlesState by articlesViewModel.state.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing(articlesState,
            favArticlesListViewModel,
            onItemClick = {
                navController.navigate("articles/${it.Id}")
            }, userViewModel),
        onRefresh = { articlesViewModel.refresh() }
    )

    LaunchedEffect(favArticlesSize) {
        Log.d("favorite list changed", favArticlesSize.toString())
        nrFavArticles = favArticlesSize
        // Your code to execute when favArticlesSize changes
    }


    val favArticlesMutableState = MutableStateFlow<FavoriteListState>(FavoriteListState())
//    val favArticlesState: StateFlow<FavoriteListState> = favArticlesMutableState

    LaunchedEffect(Unit) {
        articlesViewModel.getArticles()
        if (SharedPreferencesManager.getAuthToken() != null) {
//            Log.d("favorite", "method in main page")
//            allFavoriteArticlesContainerViewModel.fetchLikedArticles(SharedPreferencesManager.getAuthToken())
//            Log.d("favorite list", favArticlesListViewModel.fetchFavoriteArticles().toString())
        }
    }

    AppScaffold(
        title = "Home",
        navController = navController,
        context = context,
        content = {
            Column(Modifier
                .padding(it)
                .pullRefresh(pullRefreshState)
            ) {

                when (val articlesState = articlesState) {
                    is HomePageState.Loading -> LoadingIndicator()
                    is HomePageState.Success -> ArticleList(
                        allArticlesContainer = articlesState.allArticlesContainer,
                        favArticlesListViewModel,
                        onItemClick = onItemClick,
                        userViewModel,
                        isDisplaying = true,
                        isFavouritesPage = false
                    )

                    is HomePageState.Error -> ErrorMessage()
                }

//            if (SharedPreferencesManager.getAuthToken() != null) {
//                Log.d("favorites", "showing fav articles")
//                when (val favArticlesState = favArticlesState) {
//                    is FavoritePageState.Loading -> LoadingIndicator()
//                    is FavoritePageState.Success -> ArticleList(
//                        allArticlesContainer = favArticlesState.allArticlesContainer,
//                        favArticlesListViewModel,
//                        onItemClick = onItemClick,
//                        userViewModel,
//                        isDisplaying = false,
//                        isFavouritesPage = false
//                    )
//
//                    is FavoritePageState.Error -> ErrorMessage()
//                }
//            }


            }

            PullRefreshIndicator(
                refreshing = true,
                state = pullRefreshState,
                modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.TopCenter)
            )

        }
    )

    LaunchedEffect(Unit) {
        if (SharedPreferencesManager.getAuthToken() != null) {
            var result = allFavoriteArticlesContainerViewModel.fetchAllArticlesContainer(SharedPreferencesManager.getAuthToken())
            var articles : List<Article>? = result?.getOrNull()?.Results
            if (articles != null) {
                for (article in articles) {
                    article.Id?.let { id ->
                        favArticlesListViewModel.addFavArticle(
                            id
                        )
                    }
                }
            }
//            Log.d("dioporco", articles.toString())
//            Log.d("dioporco", favArticlesListViewModel.favArticlesList.value.toString())
            Log.d("favorite list main page", favArticlesListViewModel.fetchFavoriteArticles().toString())
        }
    }

}
@Composable
fun refreshing(
    articlesState : HomePageState,
    favArticlesListViewModel: FavArticlesListViewModel,
    onItemClick: (Article) -> Unit,
    userViewModel: UserViewModel
) : Boolean {
    when (val articlesState = articlesState) {
        is HomePageState.Loading -> return true
        is HomePageState.Success -> {ArticleList(
            allArticlesContainer = articlesState.allArticlesContainer,
            favArticlesListViewModel,
            onItemClick = onItemClick,
            userViewModel,
            isDisplaying = true,
            isFavouritesPage = false
        )
            return false
        }
        is HomePageState.Error -> return false
    }
}


