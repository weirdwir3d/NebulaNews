package nl.aldera.newsapp721447.presentation.viewModels.ui.pages

import android.accounts.AccountManager
import androidx.lifecycle.ViewModel
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import nl.aldera.newsapp721447.R
import nl.aldera.newsapp721447.presentation.viewModels.AllArticlesContainerViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.getSystemService
import com.wearetriple.exercise6.ui.page.main.component.ArticleList
import com.wearetriple.exercise6.ui.page.main.component.ErrorMessage
import com.wearetriple.exercise6.ui.page.main.component.LoadingIndicator
import nl.aldera.newsapp721447.RetrofitInstance
import nl.aldera.newsapp721447.data.api.NewsApi
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.MainPageState
import okhttp3.FormBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@SuppressLint("ServiceCast")
@Composable
fun ArticlesPage(
    onArticlesPageClick: () -> Unit,
    viewModel: AllArticlesContainerViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onItemClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getArticles()
    }

    AppScaffold(
        title = "Home",
    ) {
        Column(Modifier.padding(it)) {

            when (val state = state) {
                is MainPageState.Loading -> LoadingIndicator()
                is MainPageState.Success -> ArticleList(
                    allArticlesContainer = state.allArticlesContainer,
                    onItemClick = { article -> onItemClick(article.Id) }
                )

                is MainPageState.Error -> ErrorMessage()
            }

        }
    }


}


