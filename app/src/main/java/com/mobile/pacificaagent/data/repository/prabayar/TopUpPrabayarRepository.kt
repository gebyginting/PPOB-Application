package com.mobile.pacificaagent.data.repository.prabayar

import com.mobile.pacificaagent.data.network.retrofit.ApiConfig
import com.mobile.pacificaagent.data.request.prabayar.TopUpDataRequest
import com.mobile.pacificaagent.data.request.prabayar.TopUpPulsaRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TopUpPrabayarRepository(private val apiConfig: ApiConfig) {

    suspend fun topupPulsa(request: TopUpPulsaRequest) = withContext(Dispatchers.IO) {
        apiConfig.getApiService().topupPulsa(request)
    }

    suspend fun topupData(request: TopUpDataRequest) = withContext(Dispatchers.IO) {
        apiConfig.getApiService().topupData(request)
    }

}