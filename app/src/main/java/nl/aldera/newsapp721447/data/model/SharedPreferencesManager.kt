package nl.aldera.newsapp721447.data.model

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    lateinit var sharedPreferences: SharedPreferences
    fun init(context: Context){
        sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    fun isDarkMode(): Boolean {
        val isDarkMode = sharedPreferences.getBoolean("isDarkMode", false)
        return isDarkMode
    }

    fun getUserName() : String? {
        val UserName = sharedPreferences.getString("UserName", null)
        return UserName
    }

    fun getAuthToken() : String? {
        val AuthToken = sharedPreferences.getString("AuthToken", null)
        return AuthToken
    }

    fun isLoggedIn(): Boolean {
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        return isLoggedIn
    }

}