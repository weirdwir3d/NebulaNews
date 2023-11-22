package nl.aldera.newsapp721447.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableStateFlow
import nl.aldera.newsapp721447.data.model.SharedPreferencesManager
import nl.aldera.newsapp721447.presentation.viewModels.ui.navigation.AppNavigation
import nl.aldera.newsapp721447.presentation.viewModels.ui.theme.NewsApp721447Theme


class MainActivity : ComponentActivity() {
    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        SharedPreferencesManager.init(this)
        super.onCreate(savedInstanceState)

        setContent {

            var isDarkMode by remember { mutableStateOf(SharedPreferencesManager.isDarkMode()) }
            NewsApp721447Theme(darkTheme = isDarkMode) {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(applicationContext)
//                    Log.e("INFO", "main activity")
//                    AccountPage()
//                    ArticlesPage(
//                        onItemClick = {}
//                    )



















//                    val isPopupShown: MutableState<Boolean> = mutableStateOf(false)
                    val popupMutableState = MutableStateFlow<Boolean>(false)
//                    val state: StateFlow<ArticleContainerState> = mutableState

//                    val registerState by userViewModel.registerState.collectAsState()
//                    val sessionState by userViewModel.sessionState.collectAsState()
                    var isPopupVisible by remember { mutableStateOf(false) }
//                    val userViewModelSuccess = userViewModel.registerState.value

                    val snackBarHostState = remember { SnackbarHostState() }

//                    ArticleDetailsPage()

//                    when (val updatedPopupState = popupMutableState.value) {
//                        true -> {
//                            Log.i("POPUP INFO", "show popup")
//                        } else -> {
//                        Log.i("POPUP INFO", "hide popup")
//                        }
//                    }

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


