package com.mobile.pacificaagent.data.repository

import com.mobile.pacificaagent.data.network.retrofit.ApiConfig
import com.mobile.pacificaagent.data.request.DepositRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DepositRepository(private val apiConfig: ApiConfig) {

    suspend fun deposit(request: DepositRequest) = withContext(Dispatchers.IO) {
        apiConfig.getApiService().deposit(request)
    }
}