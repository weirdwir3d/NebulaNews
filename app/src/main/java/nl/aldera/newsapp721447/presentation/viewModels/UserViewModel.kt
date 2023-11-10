package nl.aldera.newsapp721447.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.aldera.newsapp721447.RetrofitInstance
import nl.aldera.newsapp721447.data.model.Session
import nl.aldera.newsapp721447.data.model.Token
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.RegisterMessage

class UserViewModel : ViewModel() {

    private val registerMutableState = MutableStateFlow<RegisterMessage>(RegisterMessage(false, ""))
    val registerState: StateFlow<RegisterMessage> = registerMutableState

    private val sessionMutableState = MutableStateFlow<Session>(Session(null, null))
    val sessionState: StateFlow<Session> = sessionMutableState

    fun registerUser(UserName: String, Password: String) : String {
        var resultMessage : String = ""
        viewModelScope.launch {
//            val response = pushUser(UserName, Password)
            val response = RetrofitInstance.newsApi.registerUser(UserName, Password)
            val isSuccessful = response.body()?.Success
            val message = response.body()?.Message

            if (isSuccessful == true) {
                Log.i("INFO", "REGISTRATION SUCCESSFUL")
                registerMutableState.tryEmit(RegisterMessage(true, "User registered"))
                resultMessage = "User registered. Please log in to access your account"
            } else {
                Log.e("ERROR", "error while registering")
                registerMutableState.tryEmit(RegisterMessage(false, "UserName already exists"))
                Log.e("STATE", registerState.value.Success.toString())
                resultMessage = "Username already exists"
            }
        }

        return resultMessage
    }

    fun loginUser(UserName: String, Password: String) : String {
        var resultMessage : String = ""
        viewModelScope.launch {
            val response = RetrofitInstance.newsApi.loginUser(UserName, Password)
            val authToken: String? = response.body()?.AuthToken

            if (authToken != null) {
                Log.i("INFO", "LOGIN SUCCESSFUL. Token =" + authToken.toString() + ", username =" + UserName)
                sessionMutableState.tryEmit(Session(UserName, authToken))
//                authToken?.let { Token(it) }?.let { tokenMutableState.tryEmit(it) }
                resultMessage = "Login successful"
            } else {
                Log.e("ERROR", "error while logging in")
                resultMessage = "Wrong username or password"
            }
        }

        return resultMessage
    }

    fun logoutUser() {
        viewModelScope.launch {
            sessionMutableState.tryEmit(Session(null, null))
            Log.i("INFO", "LOGOUT SUCCESSFUL" + sessionMutableState.toString())
        }
    }

//    suspend fun pushUser(UserName: String, Password: String): Result<RegisterMessage> {
//        return runCatching { RetrofitInstance.newsApi.registerUser(UserName, Password) }
//            .map(responseMapper::map).flatten()
////            .map(articleContainerMapper::mapList).flatten()
//    }
}