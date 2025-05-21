package com.mobile.pacificaagent.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.pacificaagent.data.repository.AuthRepository
import com.mobile.pacificaagent.data.request.LoginRequest
import com.mobile.pacificaagent.data.request.RegisterRequest
import com.mobile.pacificaagent.data.response.LoginResponse
import com.mobile.pacificaagent.data.response.RegisterUpdateResponse
import com.mobile.pacificaagent.utils.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _registerState = MutableStateFlow<ResultState<RegisterUpdateResponse>>(ResultState.Loading)
    val registerState: MutableStateFlow<ResultState<RegisterUpdateResponse>> = _registerState

    private val _loginState = MutableStateFlow<ResultState<LoginResponse>>(ResultState.Loading)
    val loginState: MutableStateFlow<ResultState<LoginResponse>> = _loginState

    fun register(request: RegisterRequest) {
        viewModelScope.launch {
            _registerState.value = ResultState.Loading
            try {
                val response = repository.register(request)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _registerState.value = ResultState.Success(data)
                    } else {
                        _registerState.value = ResultState.Error("Empty response body")
                    }
                    Log.d("Response Register;:", data.toString())
                } else {
                    _registerState.value = ResultState.Error(response.message())
                }
            } catch (e: Exception) {
                _registerState.value = ResultState.Error(e.message.toString())
            }
        }
    }

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            _loginState.value = ResultState.Loading
            try {
                val response = repository.login(request)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _loginState.value = ResultState.Success(data)
                    } else {
                        _loginState.value = ResultState.Error("Empty response body")
                    }
                    Log.d("Response Register;:", data.toString())
                } else {
                    _loginState.value = ResultState.Error(response.message())
                }
            } catch (e: Exception) {
                _loginState.value = ResultState.Error(e.message.toString())
            }
        }
    }
}