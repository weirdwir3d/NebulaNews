package nl.aldera.newsapp721447.presentation.viewModels.ui.navigation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nl.aldera.newsapp721447.presentation.viewModels.AllFavoriteArticlesContainerViewModel
import nl.aldera.newsapp721447.presentation.viewModels.FavArticlesListViewModel
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.pages.AccountPage
import nl.aldera.newsapp721447.presentation.viewModels.ui.pages.ArticleDetailsPage
import nl.aldera.newsapp721447.presentation.viewModels.ui.pages.ArticlesPage
import nl.aldera.newsapp721447.presentation.viewModels.ui.pages.FavoritesPage

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AppNavigation(context: Context) {
    val navController = rememberNavController()
    val userViewModel : UserViewModel = viewModel()
    val favArticlesListViewModel : FavArticlesListViewModel = viewModel()
    var allFavoriteArticlesContainerViewModel : AllFavoriteArticlesContainerViewModel = viewModel()
    var isFavouritesPage by remember { mutableStateOf(false) }

//    if (SharedPreferencesManager.getAuthToken() != null) {
//        allFavoriteArticlesContainerViewModel.fetchLikedArticles(SharedPreferencesManager.getAuthToken())
//    }
//    Log.d("favorite articles navigation", favArticlesListViewModel.favArticlesList.value.toString())

    NavHost(
        navController = navController,
        startDestination = "articles"
    ) {
        composable("articles") {
            ArticlesPage(
                onItemClick = {
                    navController.navigate("articles/${it.Id}")
                },
                navController = navController,
                allFavoriteArticlesContainerViewModel = allFavoriteArticlesContainerViewModel,
                favArticlesListViewModel = favArticlesListViewModel,
                userViewModel = userViewModel,
                isFavouritesPage = isFavouritesPage,
                context = context
            )
        }
        composable(
            route = "articles/{Id}",
            arguments = listOf(
                navArgument("Id") {type = NavType.IntType}
            )
        ) {
            ArticleDetailsPage(
                navController = navController,
                Id = it.arguments?.getInt("Id") ?: -1,
                context = context
            ) {
                navController.popBackStack()
            }
        }
        composable(
            route = "favorites",
        ) {
            FavoritesPage(
                navController,
                userViewModel,
                allFavoriteArticlesContainerViewModel,
                favArticlesListViewModel,
                context,
                onItemClick = {
                    navController.navigate("articles/${it.Id}")
                }
            ) {
                navController.popBackStack()
            }
        }
        composable(
            route = "account",
        ) {
            AccountPage(
                navController = navController,
                userViewModel = userViewModel,
                allFavoriteArticlesContainerViewModel = allFavoriteArticlesContainerViewModel,
                favArticlesListViewModel = favArticlesListViewModel,
                context
//                username = it.arguments?.getString("username") ?: ""
            )
        }
    }
}