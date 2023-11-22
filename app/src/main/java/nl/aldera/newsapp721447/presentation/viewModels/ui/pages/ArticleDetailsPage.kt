package nl.aldera.newsapp721447.presentation.viewModels.ui.pages

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.wearetriple.exercise6.ui.page.main.component.ErrorMessage
import com.wearetriple.exercise6.ui.page.main.component.LoadingIndicator
import nl.aldera.newsapp721447.R
import nl.aldera.newsapp721447.presentation.viewModels.ArticleViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffold
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffoldBottomBar
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffoldTopBar
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.ArticleDetailsBox
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.NavigationType
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.toggleDarkMode
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.DetailState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsPage(
    navController : NavController,
    Id: Int,
    viewModel: ArticleViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    context : Context,
    onBackPressed: () -> Unit
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getArticle(Id)
    }

//    Scaffold(
//        topBar = {
//            AppScaffoldTopBar(
//                title = "Details",
//                navigation = NavigationType.Back(onBackPressed),
//                actions = {
//                    IconButton(onClick = {
//                    }) {
//                        Icon(
//                            imageVector = Icons.Filled.Refresh,
//                            contentDescription = stringResource(R.string.network_refresh)
//                        )
//                    }
//                    IconButton(onClick = {
////                        isDarkMode = toggleDarkMode(sharedPreferences)
//                    }) {
////                        val iconResource = if (isDarkMode) R.drawable.dark_mode else R.drawable.light_mode
////                        val mode: Painter = painterResource(id = iconResource)
////                        Image(painter = mode, contentDescription = null)
//                    }
//                }
//
//            )
//        },
//        content = {
//            Column(Modifier.padding(it)) {
//                when (val state = state) {
//                    is DetailState.Loading -> LoadingIndicator()
//                    is DetailState.Success -> ArticleDetailsBox(article = state.article)
////                is DetailState.Success -> ArticleItem(item = state.article) {
////
////                }
//
//                    is DetailState.Error -> ErrorMessage()
//                    else -> {}
//                }
//            }
//
//        },
//
//        bottomBar = {
//            AppScaffoldBottomBar(
//                title = "Details",
//                actions = {
//                    IconButton(onClick = {
//                        navController.navigate("articles")
//                    }) {
//                        Icon(imageVector = Icons.Outlined.Home, contentDescription = stringResource(
//                            R.string.homepage_title)
//                        )
//                    }
//                    IconButton(onClick = {
//                        navController.navigate("favorites")
//                    }) {
//                        Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = stringResource(
//                            R.string.favourites_page_title)
//                        )
//                    }
//                    IconButton(onClick = {
//                        navController.navigate("account")
//                    }) {
//                        Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = stringResource(
//                            R.string.account_page_title)
//                        )
//                    }
//                }
//            )
//        }
//    )

    AppScaffold(
        title = "Details",
        navigation = NavigationType.Back(onBackPressed),
        navController = navController,
        context = context,
        content = {
            Column(Modifier.padding(it)) {
                when (val state = state) {
                    is DetailState.Loading -> LoadingIndicator()
                    is DetailState.Success -> ArticleDetailsBox(article = state.article)
//                is DetailState.Success -> ArticleItem(item = state.article) {
//
//                }

                    is DetailState.Error -> ErrorMessage()
                    else -> {}
                }


//            when (val state = state) {
//                is ArticleContainerState.Loading -> LoadingIndicator()
//                is ArticleContainerState.Success -> ArticleList(
//                    allArticlesContainer = state.allArticlesContainer
//                )
//
//                is ArticleContainerState.Error -> ErrorMessage()
//                else -> {}
//            }



            }
        }
    )
}