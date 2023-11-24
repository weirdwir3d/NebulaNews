package nl.aldera.newsapp721447.presentation.viewModels

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.aldera.newsapp721447.RetrofitInstance
import nl.aldera.newsapp721447.data.model.Session
import nl.aldera.newsapp721447.data.model.SharedPreferencesManager.sharedPreferences
import nl.aldera.newsapp721447.data.model.Token
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.RegisterMessage

class UserViewModel : ViewModel() {
    private val registerMutableState = MutableStateFlow<RegisterMessage>(RegisterMessage(false, ""))
    val registerState: StateFlow<RegisterMessage> = registerMutableState

    private val sessionMutableState = MutableStateFlow<Session>(Session(null, null))
    val sessionState: StateFlow<Session> get() = sessionMutableState

    fun registerUser(UserName: String, Password: String) : Int {
        var codeResult : Int = -1
        viewModelScope.launch {
            val response = RetrofitInstance.newsApi.registerUser(UserName, Password)
            val isSuccessful = response.body()?.Success
            val message = response.body()?.Message

            if (isSuccessful == true) {
                Log.d("Registration", "registration SUCCESSFUL")
                registerMutableState.tryEmit(RegisterMessage(true, "User registered"))
                codeResult = 1
            } else {
                Log.e("Registration", "error while registering registration")
                registerMutableState.tryEmit(RegisterMessage(false, "UserName already exists"))
                codeResult = 2
            }
            Log.d("Registration", "registration state from userViewModel: " + registerState.value.toString())
        }
        return codeResult
    }

    suspend fun loginUser(UserName: String, Password: String) : String? {
        var placeholder: String? = null
        withContext(Dispatchers.IO) {
            val response = RetrofitInstance.newsApi.loginUser(UserName, Password)
            val authToken: String? = response.body()?.AuthToken
            placeholder = authToken

            if (authToken != null) {
                Log.d("session", "LOGIN SUCCESSFUL. Token =" + authToken.toString() + ", username =" + UserName)
                sessionMutableState.tryEmit(Session(UserName, authToken))
                Log.i("INFO", sessionMutableState.value.UserName.toString())

                val preferencesEditor = sharedPreferences.edit()
                preferencesEditor.putBoolean("isLoggedIn", true)
                preferencesEditor.putString("AuthToken", placeholder)
                preferencesEditor.putString("UserName", UserName)
                preferencesEditor.apply()

            } else {
                Log.e("ERROR", "error while logging in")
            }
        }
        return placeholder
    }

}