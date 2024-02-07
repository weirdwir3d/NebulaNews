package nl.aldera.newsapp721447.presentation.viewModels.ui.pages

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.aldera.newsapp721447.data.model.AllArticlesContainer
import nl.aldera.newsapp721447.data.model.Article
import nl.aldera.newsapp721447.data.model.SharedPreferencesManager
import nl.aldera.newsapp721447.presentation.viewModels.AllFavoriteArticlesContainerViewModel
import nl.aldera.newsapp721447.presentation.viewModels.FavArticlesListViewModel
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffold
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.toggleFavourite
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.FavoritePageState

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountPage(
    navController: NavHostController,
    userViewModel: UserViewModel,
    favArticlesListViewModel: FavArticlesListViewModel,
    context: Context
) {

    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
    val authToken = SharedPreferencesManager.getAuthToken()
    val username = SharedPreferencesManager.getUserName()

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

    if (isLoggedIn) {
        AppScaffold(
            title = "Account",
            navController = navController,
            context = context,
            content = {
                LoggedInView(navController, context, favArticlesListViewModel)
            }
        )

    } else {
        navController.navigate("login")
    }


}

@Composable
fun LoggedInView(
    navController: NavHostController,
    context: Context,
    favArticlesListViewModel: FavArticlesListViewModel
) {
    val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val username = SharedPreferencesManager.getUserName()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // show username
        Icon(
            imageVector = Icons.Filled.AccountBox,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )

        Row {
            Text(text = "Hello, $username", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Logout-Button
        Button(
            onClick = {
//                CoroutineScope(Dispatchers.Main).launch {
                    val editor = sharedPref.edit()
                    editor.putBoolean("isLoggedIn", false)
                    editor.remove("AuthToken")
                    editor.remove("UserName")
                    editor.apply()

                    favArticlesListViewModel.clearList()

                        navController.navigate("account")


                    if (username != null) {
                        Log.d("fanculo", username)
                    } else {
                        Log.d("fanculo", "username null")
                    }

                    Log.d("fanculo isloggedin", SharedPreferencesManager.isLoggedIn().toString())
//                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
        ) {
            Text(text = "Logout")
        }


    }

}

@Composable
fun LogoutButton(
    navController: NavHostController,
    sharedPref: SharedPreferences,
    favArticlesListViewModel: FavArticlesListViewModel
) {
    Button(
        onClick = {
            val editor = sharedPref.edit()
            editor.putBoolean("isLoggedIn", false)
            editor.remove("AuthToken")
            editor.remove("UserName")
            editor.apply()
            favArticlesListViewModel.clearList()

            // Use LaunchedEffect to navigate after the state changes have taken effect

                navController.navigate("account")

        },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
    ) {
        Text(text = "Logout")
    }
}

@Composable
fun toast(context: Context) {
    Column {
        Button(
            onClick = {
                Toast.makeText(context, "This is a toast", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("show toast")
        }
    }
}
