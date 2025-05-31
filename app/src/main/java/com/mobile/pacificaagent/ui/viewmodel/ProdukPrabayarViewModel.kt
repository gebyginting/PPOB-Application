package com.mobile.pacificaagent.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.pacificaagent.data.repository.ProdukPrabayarRepository
import com.mobile.pacificaagent.data.response.ProdukPrabayarResponse
import com.mobile.pacificaagent.utils.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProdukPrabayarViewModel(private val repository: ProdukPrabayarRepository) : ViewModel() {

    private val _produkPrabayarState = MutableStateFlow<ResultState<ProdukPrabayarResponse>>(ResultState.Loading)
    val produkPrabayarState: MutableStateFlow<ResultState<ProdukPrabayarResponse>> = _produkPrabayarState

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber

    fun setPhoneNumber(number: String) {
        _phoneNumber.value = number
    }

    fun produkPrabayar(categoryId: String, number: String) {
        viewModelScope.launch {
            _produkPrabayarState.value = ResultState.Loading
            try {
                val response = repository.produkPrabayar(categoryId, number)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _produkPrabayarState.value = ResultState.Success(data)
                    } else {
                        _produkPrabayarState.value = ResultState.Error("Empty response body")
                    }
                    Log.d("Response Produk Prabayar:", data.toString())
                } else {
                    _produkPrabayarState.value = ResultState.Error(response.message())
                }
            } catch (e: Exception) {
                _produkPrabayarState.value = ResultState.Error(e.message.toString())
            }
        }
    }
}