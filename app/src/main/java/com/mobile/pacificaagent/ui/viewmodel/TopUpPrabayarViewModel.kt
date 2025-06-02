package com.mobile.pacificaagent.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.pacificaagent.data.repository.prabayar.TopUpPrabayarRepository
import com.mobile.pacificaagent.data.request.prabayar.TopUpDataRequest
import com.mobile.pacificaagent.data.request.prabayar.TopUpPulsaRequest
import com.mobile.pacificaagent.data.response.prabayar.TopUpPulsaResponse
import com.mobile.pacificaagent.utils.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TopUpPrabayarViewModel (private val repository: TopUpPrabayarRepository) : ViewModel() {

    private val _topupPulsaState = MutableStateFlow<ResultState<TopUpPulsaResponse>>(ResultState.Loading)
    val topupPulsaState: StateFlow<ResultState<TopUpPulsaResponse>> = _topupPulsaState

    private val _topupDataState = MutableStateFlow<ResultState<TopUpPulsaResponse>>(ResultState.Loading)
    val topupDataState: StateFlow<ResultState<TopUpPulsaResponse>> = _topupDataState

    fun topupPulsa(request: TopUpPulsaRequest) {
        viewModelScope.launch {
            _topupPulsaState.value = ResultState.Loading
            try {
                val response = repository.topupPulsa(request)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _topupPulsaState.value = ResultState.Success(it)
                    } ?: run {
                        _topupPulsaState.value = ResultState.Error("Empty response body")
                    }
                } else {
                    val errorMessage = parseErrorMessage(response.errorBody()?.string())
                    _topupPulsaState.value = ResultState.Error(errorMessage)
                }
            } catch (e: Exception) {
                _topupPulsaState.value = ResultState.Error(e.message.toString())
            }
        }
    }

    fun topupData(request: TopUpDataRequest) {
        viewModelScope.launch {
            _topupDataState.value = ResultState.Loading
            try {
                val response = repository.topupData(request)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _topupDataState.value = ResultState.Success(it)
                    } ?: run {
                        _topupDataState.value = ResultState.Error("Empty response body")
                    }
                } else {
                    val errorMessage = parseErrorMessage(response.errorBody()?.string())
                    _topupDataState.value = ResultState.Error(errorMessage)
                }
            } catch (e: Exception) {
                _topupDataState.value = ResultState.Error(e.message.toString())
            }
        }
    }

    private fun parseErrorMessage(jsonString: String?): String {
        return try {
            val json = org.json.JSONObject(jsonString ?: "")
            json.getString("errors") // sesuaikan dengan struktur JSON dari server kamu
        } catch (e: Exception) {
            "Terjadi kesalahan"
        }
    }

}