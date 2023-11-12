package nl.aldera.newsapp721447.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.aldera.newsapp721447.R
import nl.aldera.newsapp721447.presentation.viewModels.ui.pages.FavoritesPage
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffold
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AuthenticationForm
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.ArticleContainerState
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.IsPopupShowingState
import nl.aldera.newsapp721447.presentation.viewModels.ui.navigation.AppNavigation
import nl.aldera.newsapp721447.presentation.viewModels.ui.pages.AccountPage
import nl.aldera.newsapp721447.presentation.viewModels.ui.pages.ArticleDetailsPage
import nl.aldera.newsapp721447.presentation.viewModels.ui.pages.ArticlesPage
import nl.aldera.newsapp721447.presentation.viewModels.ui.pages.FavoritesPage
import nl.aldera.newsapp721447.presentation.viewModels.ui.theme.NewsApp721447Theme


class MainActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsApp721447Theme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    AppNavigation()
                    Log.e("INFO", "main activity")
                    AccountPage()



















//                    val isPopupShown: MutableState<Boolean> = mutableStateOf(false)
                    val popupMutableState = MutableStateFlow<Boolean>(false)
//                    val state: StateFlow<ArticleContainerState> = mutableState

                    val registerState by userViewModel.registerState.collectAsState()
                    val sessionState by userViewModel.sessionState.collectAsState()
                    var isPopupVisible by remember { mutableStateOf(false) }
//                    val userViewModelSuccess = userViewModel.registerState.value

                    val snackBarHostState = remember { SnackbarHostState() }

//                    ArticleDetailsPage()

                    when (val updatedPopupState = popupMutableState.value) {
                        true -> {
                            Log.i("POPUP INFO", "show popup")
                        } else -> {
                        Log.i("POPUP INFO", "hide popup")
                        }
                    }

//                    ArticlesPage(
//                        onArticlesPageClick = {}
//                    ) {}

//                    AppScaffold(
//                        title = "NebulaNews",
//                        content = {
//
//                            Column(Modifier.padding(it)) {
//
//                                when(val popupViewModelState = popUpState) {
//                                    true -> {
//                                        Box(
//                                            modifier = Modifier
//                                                .fillMaxSize()
//                                                .clickable {
//                                                    popupViewModel.closePopup()
//                                                }
//                                        ) {
//                                            Card(
//                                                modifier = Modifier
//                                                    .fillMaxWidth()
//                                                    .height(300.dp)
//                                            ) {
//                                                when (val sessionViewModelState = sessionState.AuthToken) {
//                                                    null -> {
//                                                        Log.i("STATE UPDATED IN MAIN PAGE", "USER NOT LOGGED IN")
//                                                        AuthenticationForm(userViewModel){}
//                                                    }
//
//                                                    else -> {
//                                                        Log.e("STATE UPDATED IN MAIN PAGE", "USER LOGGED IN")
//                                                        Text(text = "hello, " + sessionState.UserName)
//                                                        Button(onClick = {
//                                                            userViewModel.logoutUser()
//                                                        }) {
//                                                            Text("Log out")
//                                                        }
//                                                    }
//
//                                                }
//
//                                            }
//                                        }
//                                    }
//                                    else -> "popup closed"
//                                }
//                                if (isPopupVisible) {
////                                    Box(
////                                        modifier = Modifier
////                                            .fillMaxSize()
////                                            .clickable {
////                                                isPopupVisible = false
////                                            }
////                                    ) {
////                                        Card(
////                                            modifier = Modifier
////                                                .fillMaxWidth()
////                                                .height(300.dp)
////                                        ) {
////                                            when (val sessionViewModelState = sessionState.AuthToken) {
////                                                null -> {
////                                                    Log.i("STATE UPDATED IN MAIN PAGE", "USER NOT LOGGED IN")
////                                                    AuthenticationForm(userViewModel){}
////                                                }
////
////                                                else -> {
////                                                    Log.e("STATE UPDATED IN MAIN PAGE", "USER LOGGED IN")
////                                                    Text(text = "hello, " + sessionState.UserName)
////                                                    Button(onClick = {
////                                                        userViewModel.logoutUser()
////                                                    }) {
////                                                        Text("Log out")
////                                                    }
////                                                }
////
////                                            }
////
////                                        }
////                                    }
//                                }
//
//
//
//                                val scope = rememberCoroutineScope()
//
//                                when (val userViewModelState = registerState.Success) {
//                                    true -> {
//                                        Log.i("STATE UPDATED IN MAIN PAGE", "USER REGISTERED")
//                                        LaunchedEffect(Unit) {
//                                            scope.launch {
//                                                snackBarHostState.showSnackbar("Registration successful. Please log in")
//                                            }
//                                        }
//                                    }
//
//                                    else -> {
//                                        Log.e("STATE UPDATED IN MAIN PAGE", "USER NOT REGISTERED")
//                                        LaunchedEffect(Unit) {
//                                            scope.launch {
//                                                snackBarHostState.showSnackbar("Registration failed, username already exists")
//                                            }
//                                        }
//                                    }
//
//                                }
//
//                            }
//
//                        }
////                        actions = {
////                            IconButton(onClick = {
////                                //TODO: do something when button is clicked
////                            }) {
////                                Icon(
////                                    imageVector = Icons.Filled.Refresh,
////                                    contentDescription = stringResource(R.string.network_refresh)
////                                )
////                            }
////
////                            IconButton(onClick = {
////                                isPopupVisible = true
////                            }) {
////                                Icon(
////                                    imageVector = Icons.Default.Person,
////                                    contentDescription = stringResource(R.string.user_account)
////                                )
////                            }
////                        }
//                    )
//
                }
            }
        }
    }
}


