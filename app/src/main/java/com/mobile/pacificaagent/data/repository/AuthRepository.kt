package com.mobile.pacificaagent.data.repository

import ApiConfig
import com.mobile.pacificaagent.data.request.LoginRequest
import com.mobile.pacificaagent.data.request.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(private val apiConfig: ApiConfig) {

    suspend fun register(request: RegisterRequest) = withContext(Dispatchers.IO) {
        apiConfig.getApiService().register(request)
    }

    suspend fun login(request: LoginRequest) = withContext(Dispatchers.IO) {
        apiConfig.getApiService().login(request)
    }
}