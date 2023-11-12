package nl.aldera.newsapp721447.presentation.viewModels.ui.component

import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import nl.aldera.newsapp721447.presentation.viewModels.UserViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AuthenticationForm(
//    userViewModel: UserViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
//    onItemClick: (Int) -> Unit
) {
//
//    val sessionState by userViewModel.sessionState.collectAsState()
//
//    var UserName by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var Password by remember { mutableStateOf("") }
//    var buttonText by remember { mutableStateOf("Log in") }
//    var resultMessage by remember { mutableStateOf("") }
//
//
//    Column(
//        modifier = Modifier.padding(16.dp)
//    ) {
//
//        //TODO: start form
//        Row(
//            modifier = Modifier
//                .fillMaxHeight(0.2f)
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ){
//            Box(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .fillMaxWidth(0.5f)
//                    .background(Color.Green)
//                    .clickable {
//                        buttonText = "Log in"
//                    }
//            ) {
//
//                Text("Log in")
//            }
//
//            Box(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .fillMaxWidth(0.5f)
//                    .background(Color.Yellow)
//                    .clickable {
//                        buttonText = "Register"
//                    }
//            ) {
//
//                Text("Register")
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = UserName,
//            onValueChange = { UserName = it },
//            label = { Text("username") },
//            keyboardOptions = KeyboardOptions.Default
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = Password,
//            onValueChange = { Password = it },
//            label = { Text("password") },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//            visualTransformation = PasswordVisualTransformation()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        if (resultMessage.isNotEmpty()) {
//            var color : Color
//            if (resultMessage.startsWith("Wrong") or resultMessage.startsWith("Username")) {
//                Log.i("INFO", "IS ERROR")
//                color = Color.Red
//            } else {
//                Log.i("INFO", "IS NOT ERROR")
//                color = Color.Green
//            }
//            Text(
//                resultMessage,
//                color = color,
////                style = MaterialTheme.typography.h6
//            )
//        }
//
//        Button(onClick = {
//            if (buttonText == "Log in") {
//                resultMessage = userViewModel.loginUser(UserName, Password)
//            } else {
//                resultMessage = userViewModel.registerUser(UserName, Password)
//            }
//
//        }) {
//            Text(text = buttonText)
//        }
//        //TODO: end form
//    }
}


//@Composable
//fun Form() {
//    //
//}