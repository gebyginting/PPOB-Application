package com.mobile.pacificaagent.data.repository.pascabayar

import com.mobile.pacificaagent.data.network.retrofit.ApiConfig
import com.mobile.pacificaagent.data.request.pascabayar.TopUpWifiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProdukPascabayarRepository(private val apiConfig: ApiConfig) {

    suspend fun getWifiProviders() = withContext(Dispatchers.IO) {
        apiConfig.getApiService().getWifiProviders()
    }

    suspend fun getTagihanWifi(operatorId: String, pelangganId: String) = withContext(Dispatchers.IO) {
        apiConfig.getApiService().getTagihanWifi(operatorId, pelangganId)
    }

    suspend fun topupWifi(request: TopUpWifiRequest) = withContext(Dispatchers.IO) {
        apiConfig.getApiService().topupWifi(request)
    }
}