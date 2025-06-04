package com.mobile.pacificaagent.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.pacificaagent.data.repository.ProdukPrabayarRepository
import com.mobile.pacificaagent.data.response.DetailProdukPrabayarResponse
import com.mobile.pacificaagent.data.response.ProdukPrabayarResponse
import com.mobile.pacificaagent.utils.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProdukPrabayarViewModel(private val repository: ProdukPrabayarRepository) : ViewModel() {

    private val _produkPulsaState = MutableStateFlow<ResultState<ProdukPrabayarResponse>>(ResultState.Loading)
    val produkPulsaState: StateFlow<ResultState<ProdukPrabayarResponse>> = _produkPulsaState

    private val _produkPaketDataState = MutableStateFlow<ResultState<ProdukPrabayarResponse>>(ResultState.Loading)
    val produkPaketDataState: StateFlow<ResultState<ProdukPrabayarResponse>> = _produkPaketDataState

    private val _detailProdukState = MutableStateFlow<ResultState<DetailProdukPrabayarResponse>>(ResultState.Loading)
    val detailProdukState: StateFlow<ResultState<DetailProdukPrabayarResponse>> = _detailProdukState

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber

    fun setPhoneNumber(number: String) {
        _phoneNumber.value = number
    }

    fun loadProdukPulsa(number: String) {
        viewModelScope.launch {
            _produkPulsaState.value = ResultState.Loading
            try {
                val response = repository.produkPrabayar("KT-00001", number)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _produkPulsaState.value = ResultState.Success(it)
                    } ?: run {
                        _produkPulsaState.value = ResultState.Error("Empty response body")
                    }
                } else {
                    _produkPulsaState.value = ResultState.Error(response.message())
                }
            } catch (e: Exception) {
                _produkPulsaState.value = ResultState.Error(e.message.toString())
            }
        }
    }

    fun loadProdukPaketData(number: String) {
        viewModelScope.launch {
            _produkPaketDataState.value = ResultState.Loading
            try {
                val response = repository.produkPrabayar("KT-00002", number)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _produkPaketDataState.value = ResultState.Success(it)
                    } ?: run {
                        _produkPaketDataState.value = ResultState.Error("Empty response body")
                    }
                } else {
                    _produkPaketDataState.value = ResultState.Error(response.message())
                }
            } catch (e: Exception) {
                _produkPaketDataState.value = ResultState.Error(e.message.toString())
            }
        }
    }

    fun detailProdukPrabayar(productId: String) {
        viewModelScope.launch {
            _detailProdukState.value = ResultState.Loading
            try {
                val response = repository.detailProdukPrabayar(productId)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _detailProdukState.value = ResultState.Success(data)
                    } else {
                        _detailProdukState.value = ResultState.Error("Empty response body")
                    }
                } else {
                    _detailProdukState.value = ResultState.Error(response.message())
                }
            } catch (e: Exception) {
                _detailProdukState.value = ResultState.Error(e.message ?: "Unknown error")
            }
        }
    }
}