package com.mobile.pacificaagent.data.repository

import com.mobile.pacificaagent.data.network.retrofit.ApiConfig
import com.mobile.pacificaagent.data.request.UpdateProfileRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository (private val apiConfig: ApiConfig) {

    suspend fun getProfile() = withContext(Dispatchers.IO) {
        apiConfig.getApiService().getProfile()
    }

    suspend fun updateProfile(request: UpdateProfileRequest) = withContext(Dispatchers.IO) {
        apiConfig.getApiService().updateProfile(request)
    }

    suspend fun getBalance() = withContext(Dispatchers.IO) {
        apiConfig.getApiService().getBalance()
    }
}