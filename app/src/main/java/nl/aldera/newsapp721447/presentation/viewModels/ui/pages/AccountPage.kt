package nl.aldera.newsapp721447.presentation.viewModels.ui.pages

import android.text.Layout
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import kotlinx.coroutines.delay
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountPage(
    userViewModel: UserViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    ) {

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

    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()

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
                            Log.e("INFO", "launched")
                            if (!sessionState.AuthToken.isNullOrBlank()) {
                                loginResultMessage = "Login successful!"
                                UserName = ""
                                Password = ""
                            } else {
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
                                    //TODO: Log out
                                })
                    }

    }


    //TODO: start form
//    Row(
//        modifier = Modifier
//            .fillMaxHeight(0.2f)
//            .fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceEvenly
//    ){
//        Box(
//            modifier = Modifier
//                .fillMaxHeight(0.2f)
//                .fillMaxWidth(0.5f)
//                .background(Color.Green)
//                .clickable {
//                    buttonText = "Log in"
//                }
//        ) {
//
//            Text("Log in")
//        }
//
//        Box(
//            modifier = Modifier
//                .fillMaxHeight()
//                .fillMaxWidth(0.5f)
//                .background(Color.Yellow)
//                .clickable {
//                    buttonText = "Register"
//                }
//        ) {
//
//            Text("Register")
//        }
//    }
//
//    Spacer(modifier = Modifier.height(16.dp))
//
//    OutlinedTextField(
//        value = UserName,
//        onValueChange = { UserName = it },
//        label = { Text("username") },
//        keyboardOptions = KeyboardOptions.Default
//    )
//
//    Spacer(modifier = Modifier.height(16.dp))
//
//    OutlinedTextField(
//        value = Password,
//        onValueChange = { Password = it },
//        label = { Text("password") },
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//        visualTransformation = PasswordVisualTransformation()
//    )
//
//    Spacer(modifier = Modifier.height(16.dp))
//
//    if (resultMessage.isNotEmpty()) {
//        var color : Color
//        if (resultMessage.startsWith("Wrong") or resultMessage.startsWith("Username")) {
//            Log.i("INFO", "IS ERROR")
//            color = Color.Red
//        } else {
//            Log.i("INFO", "IS NOT ERROR")
//            color = Color.Green
//        }
//        Text(
//            resultMessage,
//            color = color,
////                style = MaterialTheme.typography.h6
//        )
//    }
//
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