package com.mobile.pacificaagent.di
import ApiConfig
import android.content.Context
import com.mobile.pacificaagent.data.repository.AuthRepository
import com.mobile.pacificaagent.data.repository.UserRepository
import com.mobile.pacificaagent.utils.UserPreference

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val userPref = UserPreference(context)
        val apiConfig = ApiConfig(userPref)
        return AuthRepository(apiConfig)
    }
    fun provideUserRepository(context: Context): UserRepository{
        val userPref = UserPreference(context)
        val apiConfig = ApiConfig(userPref)
        return UserRepository(apiConfig)
    }
}
