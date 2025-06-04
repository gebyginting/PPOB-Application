package com.mobile.pacificaagent.di

import android.content.Context
import com.mobile.pacificaagent.data.network.retrofit.ApiConfig
import com.mobile.pacificaagent.data.repository.AuthRepository
import com.mobile.pacificaagent.data.repository.DepositRepository
import com.mobile.pacificaagent.data.repository.ProdukPrabayarRepository
import com.mobile.pacificaagent.data.repository.UserRepository
import com.mobile.pacificaagent.data.repository.pascabayar.ProdukPascabayarRepository
import com.mobile.pacificaagent.data.repository.prabayar.TopUpPrabayarRepository
import com.mobile.pacificaagent.utils.UserPreference

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val userPref = UserPreference(context)
        val apiConfig = ApiConfig(userPref)
        return AuthRepository(apiConfig)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val userPref = UserPreference(context)
        val apiConfig = ApiConfig(userPref)
        return UserRepository(apiConfig)
    }

    fun provideProdukPrabayarRepository(context: Context): ProdukPrabayarRepository {
        val userPref = UserPreference(context)
        val apiConfig = ApiConfig(userPref)
        return ProdukPrabayarRepository(apiConfig)
    }

    fun provideDepositRepository(context: Context): DepositRepository {
        val userPref = UserPreference(context)
        val apiConfig = ApiConfig(userPref)
        return DepositRepository(apiConfig)
    }

    fun provideTopUpPrabayarRepository(context: Context): TopUpPrabayarRepository {
        val userPref = UserPreference(context)
        val apiConfig = ApiConfig(userPref)
        return TopUpPrabayarRepository(apiConfig)
    }

    fun provideProdukPascabayarRepository(context: Context): ProdukPascabayarRepository {
        val userPref = UserPreference(context)
        val apiConfig = ApiConfig(userPref)
        return ProdukPascabayarRepository(apiConfig)
    }
}
