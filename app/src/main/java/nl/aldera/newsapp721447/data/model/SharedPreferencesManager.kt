package nl.aldera.newsapp721447.data.model

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    lateinit var sharedPreferences: SharedPreferences
//    fun init(context: Context){
//        sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
//    }

    fun getAuthToken() : String?{
        val AuthToken = sharedPreferences.getString("AuthToken", null)
        return AuthToken
    }

    fun LoginStatus(): Boolean {
        val loggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        return loggedIn
    }

}