package nl.aldera.newsapp721447.presentation.viewModels.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nl.aldera.newsapp721447.presentation.viewModels.ui.pages.ArticleDetailsPage
import nl.aldera.newsapp721447.presentation.viewModels.ui.pages.ArticlesPage
import nl.aldera.newsapp721447.presentation.viewModels.ui.pages.FavoritesPage

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "articles"
    ) {
        composable("articles") {
            ArticlesPage(
                onItemClick = {
                    navController.navigate("articles/${it.Id}")
                }
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
                Id = it.arguments?.getInt("Id") ?: -1
            )
        }
        composable(
            route = "favorites",
            arguments = listOf(
                navArgument("username") {type = NavType.StringType}
            )
        ) {
            FavoritesPage(
//                navController = navController,
//                username = it.arguments?.getString("username") ?: ""
            )
        }
    }
}