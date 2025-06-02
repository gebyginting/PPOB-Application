package com.mobile.pacificaagent.data.repository.prabayar

import com.mobile.pacificaagent.data.network.retrofit.ApiConfig
import com.mobile.pacificaagent.data.request.prabayar.TopUpDataRequest
import com.mobile.pacificaagent.data.request.prabayar.TopUpEWalletRequest
import com.mobile.pacificaagent.data.request.prabayar.TopUpPulsaRequest
import com.mobile.pacificaagent.data.request.prabayar.TopUpTokenRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TopUpPrabayarRepository(private val apiConfig: ApiConfig) {

    suspend fun topupPulsa(request: TopUpPulsaRequest) = withContext(Dispatchers.IO) {
        apiConfig.getApiService().topupPulsa(request)
    }

    suspend fun topupData(request: TopUpDataRequest) = withContext(Dispatchers.IO) {
        apiConfig.getApiService().topupData(request)
    }

    suspend fun topupToken(request: TopUpTokenRequest) = withContext(Dispatchers.IO) {
        apiConfig.getApiService().topupToken(request)
    }

    suspend fun topupEwallet(request: TopUpEWalletRequest) = withContext(Dispatchers.IO) {
        apiConfig.getApiService().topupEWallet(request)
    }

}