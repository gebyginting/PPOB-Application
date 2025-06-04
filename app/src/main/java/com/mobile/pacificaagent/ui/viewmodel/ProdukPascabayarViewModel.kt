package com.mobile.pacificaagent.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.pacificaagent.data.repository.pascabayar.ProdukPascabayarRepository
import com.mobile.pacificaagent.data.request.pascabayar.TopUpWifiRequest
import com.mobile.pacificaagent.data.response.pascabayar.GetWifiProvidersResponse
import com.mobile.pacificaagent.data.response.pascabayar.TagihanWifiResponse
import com.mobile.pacificaagent.data.response.pascabayar.TopUpWifiResponse
import com.mobile.pacificaagent.utils.Helper.parseErrorMessage
import com.mobile.pacificaagent.utils.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProdukPascabayarViewModel(private val repository: ProdukPascabayarRepository) : ViewModel() {

    private val _wifiProvidersState = MutableStateFlow<ResultState<GetWifiProvidersResponse>>(ResultState.Loading)
    val wifiProvidersState: StateFlow<ResultState<GetWifiProvidersResponse>> = _wifiProvidersState

    private val _tegiahanWifiState = MutableStateFlow<ResultState<TagihanWifiResponse>>(ResultState.Loading)
    val tegiahanWifiState: StateFlow<ResultState<TagihanWifiResponse>> = _tegiahanWifiState

    private val _topupWifiState = MutableStateFlow<ResultState<TopUpWifiResponse>>(ResultState.Loading)
    val topupWifiState: StateFlow<ResultState<TopUpWifiResponse>> = _topupWifiState

    fun loadWifiProviders() {
        viewModelScope.launch {
            _wifiProvidersState.value = ResultState.Loading
            try {
                val response = repository.getWifiProviders()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _wifiProvidersState.value = ResultState.Success(it)
                    } ?: run {
                        _wifiProvidersState.value = ResultState.Error("Empty response body")
                    }
                } else {
                    val errorMessage = parseErrorMessage(response.errorBody()?.string())
                    _wifiProvidersState.value = ResultState.Error(errorMessage)
                }
            } catch (e: Exception) {
                _wifiProvidersState.value = ResultState.Error(e.message.toString())
            }
        }
    }

    fun getTagihanWifi(operatorId: String, pelangganId: String) {
        viewModelScope.launch {
            _tegiahanWifiState.value = ResultState.Loading
            try {
                val response = repository.getTagihanWifi(operatorId, pelangganId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _tegiahanWifiState.value = ResultState.Success(it)
                    } ?: run {
                        _tegiahanWifiState.value = ResultState.Error("Empty response body")
                    }
                } else {
                    val errorMessage = parseErrorMessage(response.errorBody()?.string())
                    _tegiahanWifiState.value = ResultState.Error(errorMessage)
                }
            } catch (e: Exception) {
                _tegiahanWifiState.value = ResultState.Error(e.message.toString())
            }
        }
    }

    fun topupWifi(request: TopUpWifiRequest) {
        viewModelScope.launch {
            _topupWifiState.value = ResultState.Loading
            try {
                val response = repository.topupWifi(request)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _topupWifiState.value = ResultState.Success(it)
                    } ?: run {
                        _topupWifiState.value = ResultState.Error("Empty response body")
                    }
                } else {
                    val errorMessage = parseErrorMessage(response.errorBody()?.string())
                    _topupWifiState.value = ResultState.Error(errorMessage)
                }
            } catch (e: Exception) {
                _topupWifiState.value = ResultState.Error(e.message.toString())
            }
        }
    }
}