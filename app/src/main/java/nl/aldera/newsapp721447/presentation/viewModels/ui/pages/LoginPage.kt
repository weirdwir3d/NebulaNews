package nl.aldera.newsapp721447.presentation.viewModels.ui.pages

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.aldera.newsapp721447.data.model.SharedPreferencesManager
import nl.aldera.newsapp721447.presentation.viewModels.FavArticlesListViewModel
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.component.AppScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    navController: NavHostController,
    userViewModel: UserViewModel,
    context: Context,
    favArticlesListViewModel: FavArticlesListViewModel
) {

    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
    var authToken = SharedPreferencesManager.getAuthToken()
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


    if (!SharedPreferencesManager.isLoggedIn()) {
        AppScaffold(
            title = "account",
            navController = navController,
            context = context,
            content = {
                //content starts here
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = UserName,
                            onValueChange = { UserName = it },
                            label = { Text("Username") }
                        )
                        OutlinedTextField(
                            value = Password,
                            onValueChange = { Password = it },
                            label = { Text("Password") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            visualTransformation = PasswordVisualTransformation(),
                        )

                        Spacer(modifier = Modifier.size(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(onClick = {
                                userViewModel.registerUser(UserName, Password)

                                if (!registerState.Success) {
                                    Toast.makeText(
                                        context,
                                        "Registration failed, try with another username",
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Registration successful!",
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                }

                            }) {
                                Text("Register")
                            }

                            Button(
                                onClick = {
                                    val preferencesEditor = SharedPreferencesManager.sharedPreferences.edit()
                                    CoroutineScope(Dispatchers.Main).launch {
                                        Log.d("geovadiocane", UserName + Password)
                                        authToken = userViewModel.loginUser(UserName, Password)
                                        val preferencesEditor = SharedPreferencesManager.sharedPreferences.edit()
                                        preferencesEditor.putBoolean("isLoggedIn", true)
                                        preferencesEditor.putString("AuthToken", authToken)
                                        preferencesEditor.putString("UserName", UserName)
                                        preferencesEditor.apply()
                                    }

                                    preferencesEditor.putBoolean("isLoggedIn", true)
                                    preferencesEditor.putString("AuthToken", authToken)
                                    preferencesEditor.putString("UserName", UserName)
                                    preferencesEditor.apply()

                                    if (authToken != null) {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            Toast.makeText(context, "Login successful!", Toast.LENGTH_LONG).show()
                                            navController.navigate("login")
                                        }
                                    }
                                    else {
                                        Toast.makeText(
                                            context,
                                            "Login failed, incorrect username/password",
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                    }

                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                            ) {
                                Text("Login")
                            }

                        }

                    }


                }
            }
        )
    } else {
        AppScaffold(
            title = "account",
            navController = navController,
            context = context,
            content = {
                LoggedInView(navController, context, favArticlesListViewModel)
            }
        )
    }

}