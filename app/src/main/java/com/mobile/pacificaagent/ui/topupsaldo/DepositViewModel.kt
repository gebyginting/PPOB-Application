package com.mobile.pacificaagent.ui.topupsaldo

import DepositResponse
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.pacificaagent.data.repository.DepositRepository
import com.mobile.pacificaagent.data.request.DepositRequest
import com.mobile.pacificaagent.utils.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DepositViewModel(private val repository: DepositRepository) : ViewModel() {

    private val _depositState = MutableStateFlow<ResultState<DepositResponse>>(ResultState.Loading)
    val depositState: MutableStateFlow<ResultState<DepositResponse>> = _depositState

    fun deposit(request: DepositRequest) {
        viewModelScope.launch {
            _depositState.value = ResultState.Loading
            try {
                val response = repository.deposit(request)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _depositState.value = ResultState.Success(data)
                    } else {
                        _depositState.value = ResultState.Error("Empty response body")
                    }
                    Log.d("Response Register;:", data.toString())
                } else {
                    _depositState.value = ResultState.Error(response.message())
                }
            } catch (e: Exception) {
                _depositState.value = ResultState.Error(e.message.toString())
            }
        }
    }

}