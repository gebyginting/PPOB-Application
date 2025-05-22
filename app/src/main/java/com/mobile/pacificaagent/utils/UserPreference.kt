package com.mobile.pacificaagent.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.mobile.pacificaagent.data.response.DataUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserPreference(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "user_pref"
        private const val KEY_TOKEN = "token"
        private const val KEY_USER = "user_data"

    }

    private val gson = Gson()

    // Backing mutable StateFlow
    private val _userFlow = MutableStateFlow<DataUser?>(getUser())
    // Public read-only flow
    val userFlow: StateFlow<DataUser?> = _userFlow

    fun saveUser(user: DataUser) {
        val json = gson.toJson(user)
        prefs.edit().putString(KEY_USER, json).apply()
        // Emit data terbaru supaya observer update UI
        _userFlow.value = user
    }

    fun getUser(): DataUser? {
        val json = prefs.getString(KEY_USER, null)
        return json?.let { gson.fromJson(it, DataUser::class.java) }
    }

    fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String = prefs.getString(KEY_TOKEN, "") ?: ""


    fun clear() {
        prefs.edit().clear().apply()
        // Emit null kalau sudah clear supaya UI juga update
        _userFlow.value = null
    }
}
