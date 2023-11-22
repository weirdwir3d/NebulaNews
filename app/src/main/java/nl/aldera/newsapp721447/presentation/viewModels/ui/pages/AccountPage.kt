package nl.aldera.newsapp721447.presentation.viewModels.ui.pages

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.wearetriple.exercise6.ui.page.main.component.ArticleList
import nl.aldera.newsapp721447.data.model.AllArticlesContainer
import nl.aldera.newsapp721447.data.model.Article
import nl.aldera.newsapp721447.data.model.SharedPreferencesManager
import nl.aldera.newsapp721447.presentation.viewModels.AllFavoriteArticlesContainerViewModel
import nl.aldera.newsapp721447.presentation.viewModels.FavArticlesListViewModel
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffold
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.FavoritePageState

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountPage(
    navController : NavController,
    userViewModel: UserViewModel,
    allFavoriteArticlesContainerViewModel: AllFavoriteArticlesContainerViewModel,
    favArticlesListViewModel : FavArticlesListViewModel,
    context : Context
) {

    val favArticlesState by allFavoriteArticlesContainerViewModel.state.collectAsState()
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
    if (isLoggedIn) {
        // display for user who is logged in
        Log.i("login", "is logged in")
//        LoggedInView(navController, context)
    } else {
        // show display for login
//        LoginPage(navController, viewModel, context, homeViewModel)
        Log.i("login", "not logged in")
    }

    val sessionState by userViewModel.sessionState.collectAsState()
    val registerState by userViewModel.registerState.collectAsState()

    var isRegisterState by remember { mutableStateOf(true) }
    var UserName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var Password by remember { mutableStateOf("") }
    var buttonText by remember { mutableStateOf("Log in") }
    var registerResultMessage by remember { mutableStateOf("") }
    var loginResultMessage by remember { mutableStateOf("") }
    var resultMessageColor by remember { mutableStateOf(Color.Green) }

    AppScaffold(
        title = "account",
        navController = navController,
        context = context,
        content = {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = UserName,
                        onValueChange = {UserName = it},
                        label = { Text("Username") }
                    )
                    OutlinedTextField(
                        value = Password,
                        onValueChange = {Password = it},
                        label = { Text("Password") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation(),
                    )

                    Spacer(modifier = Modifier.size(16.dp))
                    Button(onClick = {
                        if (isRegisterState) {
                            var codeResult = userViewModel.registerUser(UserName, Password)
                        } else if (!sessionState.AuthToken.isNullOrBlank()) {
                            loginResultMessage = "Log out before logging in again"
                        } else {
                            var codeResult = userViewModel.loginUser(UserName, Password)
                        }
                    }) {
                        Text(text = if (isRegisterState) "Register" else "Login")
                    }

                    LaunchedEffect(registerState) {
                        Log.e("INFO", "launched")
                        if (registerState.Success) {
                            registerResultMessage = "Registration successful! Log in to start using your account"
                        } else {
                            registerResultMessage = "Registration failed. Account with this username already exists"
                        }
                    }

                    Text(registerResultMessage)

                    LaunchedEffect(sessionState) {
                        if (!sessionState.AuthToken.isNullOrBlank()) {
                            loginResultMessage = "Login successful!"
                            UserName = ""
                            Password = ""

                            val preferencesEditor = sharedPreferences.edit()
                            preferencesEditor.putBoolean("isLoggedIn", true)
                            preferencesEditor.putString("AuthToken", sessionState.AuthToken)
                            preferencesEditor.putString("UserName", sessionState.UserName)
                            preferencesEditor.apply()

                            // fetch list containing ids of user's fav articles and update favArticlesListViewmodel,
                            // so that the list can be accessible across the whole project
//                            var result = allFavoriteArticlesContainerViewModel.fetchAllArticlesContainer(SharedPreferencesManager.getAuthToken())
//                            var articles : List<Article>? = result?.getOrNull()?.Results
//                            if (articles != null) {
//                                for (article in articles) {
//                                    article.Id?.let { id ->
//                                        favArticlesListViewModel.addFavArticle(
//                                            id
//                                        )
//                                    }
//                                }
//                            }
//                            Log.d("dioporco", articles.toString())
//                            Log.d("dioporco", favArticlesListViewModel.favArticlesList.value.toString())
//                            Log.d("favorite articles", "login successful. isLoggedIn: " + SharedPreferencesManager.isLoggedIn() + "token: " + SharedPreferencesManager.getAuthToken())
                            //            if (SharedPreferencesManager.getAuthToken() != null) {

                        }

                        else {
                            loginResultMessage = "Login failed. Username or password incorrect"
                        }
                    }

                    Text(loginResultMessage)
//                        resultMsg(number = 9)
//                        Text(text = if (registerState.Success) "Registration successful! Log in to start using your account"
//                            else "Registration failed. Account with this username already exists")

//                        Text(text = if (!registerState.Success && registerState.Message.isNotEmpty()) "Registration failed. Account with this username already exists"
//                        else "Registration successful! Log in to start using your account")
                    Log.e("INFO", "registration state from AccountPage: " + registerState.toString())
                    //    Button(onClick = {
//        if (buttonText == "Log in") {
//            resultMessage = userViewModel.loginUser(UserName, Password)
//        } else {
//            resultMessage = userViewModel.registerUser(UserName, Password)
//        }
//
//    }) {
//        Text(text = buttonText)
//    }


                    Spacer(modifier = Modifier.size(8.dp))
                    Text(color = Color.Blue,
                        text = if (isRegisterState) "Login" else "Register",
                        modifier = Modifier
                            .clickable {
                                isRegisterState = !isRegisterState
                            })

                    Spacer(modifier = Modifier.size(14.dp))
                    Text(text = "Log out",
                        modifier = Modifier
                            .clickable {
                                sessionState.AuthToken = null
                                sessionState.UserName = null
                                Log.i("favorite article", "üser logged out")

                                val editor = sharedPreferences.edit()
                                editor.putBoolean("isLoggedIn", false)
                                editor.remove("AuthToken")
                                editor.remove("UserName")
                                editor.apply()
                                favArticlesListViewModel.clearList()
                                Log.d("favorite articles empty list", favArticlesListViewModel.favArticlesList.value.toString())
                                Log.d("favorite articles empty list", "token: " + SharedPreferencesManager.getAuthToken())
                            })

                }



            }
        }
    )

}

@Composable
fun LoggedInView(navController: NavHostController, context: Context) {
    val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)
    val username = sharedPref.getString("UserName", "DefaultUsername")
    if (isLoggedIn) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // show username
            Text(text = "hello")
            Text(text = username.toString())
            Spacer(modifier = Modifier.height(16.dp))
            // Logout-Button
            Button(
                onClick = {
//                    val editor = sharedPref.edit()
//                    editor.putBoolean("isLoggedIn", false)
//                    editor.apply()
//                    // delete username and authToken
//                    UserData.username = ""
//                    clearAuth(context)
//                    navController.navigate(Screens.Login.route)
                }
            ) {
                Text(text = "Logout")
            }
        }
    } else {
        // If user is not logged in:
        Text(text = "Not logged in",
            modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun resultMsg(number : Int) : Unit {
    var msg = ""
    var color = Color.Green

    when (number) {
        1 -> {
            msg = "Registration successful! Log in to start using your account"
        }
        2 -> {
            msg = "Registration failed. Account with this username already exists"
            color = Color.Red
        }
        3 -> {
            msg = "Login successful!"
        }
        4 -> {
            msg = "Login failed. Username or password incorrect"
            color = Color.Red
        }

    }

    return Text(msg, color = color)
}