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
import nl.aldera.newsapp721447.data.model.Article
import nl.aldera.newsapp721447.data.model.SharedPreferencesManager
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.HomePageState

@SuppressLint("ServiceCast")
@Composable
fun ArticlesPage(
    navController : NavController,
    articlesViewModel: AllArticlesContainerViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    userViewModel: UserViewModel,
    onItemClick: (Article) -> Unit
) {

    SharedPreferencesManager.getAuthToken()?.let { Log.d("token: ", it) }
    val articlesState by articlesViewModel.state.collectAsState()
    val sessionState by userViewModel.sessionState.collectAsState()

    LaunchedEffect(Unit) {
        articlesViewModel.getArticles()
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
                        onItemClick = onItemClick,
                        userViewModel
                    )

                    is HomePageState.Error -> ErrorMessage()
                }




        }
    }


}


