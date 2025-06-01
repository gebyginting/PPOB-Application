package com.mobile.pacificaagent.data.repository

import ApiConfig
import com.mobile.pacificaagent.data.request.DepositRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DepositRepository(private val apiConfig: ApiConfig) {

    suspend fun deposit(request: DepositRequest) = withContext(Dispatchers.IO) {
        apiConfig.getApiService().deposit(request)
    }
}