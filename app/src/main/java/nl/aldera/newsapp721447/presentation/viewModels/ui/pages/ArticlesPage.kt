package nl.aldera.newsapp721447.presentation.viewModels.ui.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import nl.aldera.newsapp721447.presentation.viewModels.AllArticlesContainerViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffold
import androidx.navigation.NavController
import com.wearetriple.exercise6.ui.page.main.component.ArticleList
import com.wearetriple.exercise6.ui.page.main.component.ErrorMessage
import com.wearetriple.exercise6.ui.page.main.component.LoadingIndicator
import nl.aldera.newsapp721447.data.model.Article
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.MainPageState

@SuppressLint("ServiceCast")
@Composable
fun ArticlesPage(
    navController : NavController,
    articlesViewModel: AllArticlesContainerViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onItemClick: (Article) -> Unit
) {

    val articlesState by articlesViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        articlesViewModel.getArticles()
    }

    AppScaffold(
        title = "Home",
        navController = navController
    ) {
        Column(Modifier.padding(it)) {


            when (val articlesState = articlesState) {
                is MainPageState.Loading -> LoadingIndicator()
                is MainPageState.Success -> ArticleList(
                    allArticlesContainer = articlesState.allArticlesContainer,
                    onItemClick = onItemClick
                )

                is MainPageState.Error -> ErrorMessage()
            }

        }
    }


}


