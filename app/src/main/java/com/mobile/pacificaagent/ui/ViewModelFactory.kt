package com.mobile.pacificaagent.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.pacificaagent.data.repository.AuthRepository
import com.mobile.pacificaagent.data.repository.DepositRepository
import com.mobile.pacificaagent.data.repository.UserRepository
import com.mobile.pacificaagent.di.Injection
import com.mobile.pacificaagent.ui.auth.AuthViewModel
import com.mobile.pacificaagent.ui.auth.UserViewModel
import com.mobile.pacificaagent.ui.topupsaldo.DepositViewModel
import com.mobile.pacificaagent.utils.UserPreference

class ViewModelFactory(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val userPreference: UserPreference,
    private val depositRepository: DepositRepository,

) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(authRepository) as T
            }

            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                UserViewModel(userRepository, userPreference) as T
            }

            modelClass.isAssignableFrom(DepositViewModel::class.java) -> {
                DepositViewModel(depositRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                val userPref = UserPreference(context)
                instance ?: ViewModelFactory(
                    Injection.provideAuthRepository(context),
                    Injection.provideUserRepository(context),
                    userPref,
                    Injection.provideDepositRepository(context))
            }.also { instance = it }
    }
}