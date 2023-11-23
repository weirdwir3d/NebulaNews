package nl.aldera.newsapp721447.presentation.viewModels.ui.pages

import android.content.Context
import android.util.Log
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
import com.wearetriple.exercise6.ui.page.main.component.ArticleList
import com.wearetriple.exercise6.ui.page.main.component.ErrorMessage
import com.wearetriple.exercise6.ui.page.main.component.LoadingIndicator
import nl.aldera.newsapp721447.R
import nl.aldera.newsapp721447.data.model.Article
import nl.aldera.newsapp721447.presentation.viewModels.ArticleViewModel
import nl.aldera.newsapp721447.presentation.viewModels.FavArticlesListViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffold
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffoldBottomBar
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffoldTopBar
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.ArticleDetailsBox
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.NavigationType
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.toggleDarkMode
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.DetailState
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.HomePageState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsPage(
    navController : NavController,
    Id: Int,
    viewModel: ArticleViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    context : Context,
    favArticlesListViewModel : FavArticlesListViewModel,
    onBackPressed: () -> Unit
) {

    val favArticlesState by favArticlesListViewModel.favArticlesList.collectAsState()
    var article : Article? = null
    val articleState by viewModel.state.collectAsState()

    when (val articleState = articleState) {
        is DetailState.Loading -> null
        is DetailState.Success -> article = articleState.article
        is DetailState.Error -> null
    }

    Log.d("deets", favArticlesState.toString())

    LaunchedEffect(Unit) {
        viewModel.getArticle(Id)
    }

    if (article != null) {
        AppScaffold(
            title = article.Title ?: "",
            navigation = NavigationType.Back(onBackPressed),
            navController = navController,
            context = context,
            content = {
                Column(Modifier.padding(it)) {
                    when (val articleState = articleState) {
                        is DetailState.Loading -> LoadingIndicator()
                        is DetailState.Success -> {
                            var isFavorite = articleState.article.Id?.let { id ->
                                favArticlesListViewModel.contains(
                                    id
                                )
                            }
                            Log.d("deets", isFavorite.toString())
                            if (isFavorite != null) {
                                ArticleDetailsBox(articleState.article, isFavorite, favArticlesListViewModel)
                            }
                        }
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
}