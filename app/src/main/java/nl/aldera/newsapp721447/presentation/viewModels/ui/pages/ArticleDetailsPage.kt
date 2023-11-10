package nl.aldera.newsapp721447.presentation.viewModels.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.wearetriple.exercise6.ui.page.main.component.ArticleList
import com.wearetriple.exercise6.ui.page.main.component.ErrorMessage
import com.wearetriple.exercise6.ui.page.main.component.LoadingIndicator
import nl.aldera.newsapp721447.presentation.viewModels.ArticleViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffold
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.ArticleContainerState
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.MainPageState

@Composable
fun ArticleDetailsPage(
//    navController: NavController,
//    Id: Int
    viewModel: ArticleViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getArticle()
    }

    AppScaffold(
        title = "Details",
    ) {
        Column(Modifier.padding(it)) {

            when (val state = state) {
                is ArticleContainerState.Loading -> LoadingIndicator()
                is ArticleContainerState.Success -> ArticleList(
                    allArticlesContainer = state.allArticlesContainer,
                    onItemClick = {}
                )

                is ArticleContainerState.Error -> ErrorMessage()
                else -> {}
            }

        }
    }


}