package com.mobile.pacificaagent.data.repository

import ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProdukPrabayarRepository(private val apiConfig: ApiConfig) {

    suspend fun produkPrabayar(categoryId: String, number: String) = withContext(Dispatchers.IO) {
        apiConfig.getApiService().produkPrabayar(categoryId, number)
    }
}