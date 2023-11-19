package nl.aldera.newsapp721447.presentation.viewModels.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nl.aldera.newsapp721447.presentation.viewModels.AllFavoriteArticlesContainerViewModel
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.pages.AccountPage
import nl.aldera.newsapp721447.presentation.viewModels.ui.pages.ArticleDetailsPage
import nl.aldera.newsapp721447.presentation.viewModels.ui.pages.ArticlesPage
import nl.aldera.newsapp721447.presentation.viewModels.ui.pages.FavoritesPage

@Composable
fun AppNavigation(applicationContext: Context) {
    val navController = rememberNavController()
    val userViewModel : UserViewModel = viewModel()
    var allFavoriteArticlesContainerViewModel : AllFavoriteArticlesContainerViewModel = viewModel()

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
                userViewModel = userViewModel
            )
        }
        composable(
            route = "articles/{Id}",
            arguments = listOf(
                navArgument("Id") {type = NavType.IntType}
            )
        ) {
            ArticleDetailsPage(
                Id = it.arguments?.getInt("Id") ?: -1,
                navController = navController
            ) {
                navController.popBackStack()
            }
        }
        composable(
            route = "favorites",
//            arguments = listOf(
//                navArgument("username") {type = NavType.StringType},
//                navArgument("token") {type = androidx.navigation.NavType.StringType}
//            )
        ) {
            FavoritesPage(
                navController = navController,
                userViewModel = userViewModel,
                allFavoriteArticlesContainerViewModel,
                applicationContext
//                username = it.arguments?.getString("username") ?: ""
            )
        }
        composable(
            route = "account",
        ) {
            AccountPage(
                navController = navController,
                userViewModel = userViewModel,
                applicationContext
//                username = it.arguments?.getString("username") ?: ""
            )
        }
    }
}