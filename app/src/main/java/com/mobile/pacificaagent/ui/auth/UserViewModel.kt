package com.mobile.pacificaagent.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.pacificaagent.data.repository.UserRepository
import com.mobile.pacificaagent.data.request.UpdateProfileRequest
import com.mobile.pacificaagent.data.response.GetBalanceResponse
import com.mobile.pacificaagent.data.response.RegisterUpdateResponse
import com.mobile.pacificaagent.data.response.UserProfileResponse
import com.mobile.pacificaagent.utils.ResultState
import com.mobile.pacificaagent.utils.UserPreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository, private val userPreference: UserPreference
) : ViewModel() {

    // profile
    private val _profileState = MutableStateFlow<ResultState<UserProfileResponse>>(ResultState.Loading)
    val profileState: StateFlow<ResultState<UserProfileResponse>> = _profileState

    // update profile
    private val _updateProfileState = MutableStateFlow<ResultState<RegisterUpdateResponse>>(ResultState.Loading)
    val updateProfileState: StateFlow<ResultState<RegisterUpdateResponse>> = _updateProfileState

    // get balance
    private val _getBalanceState = MutableStateFlow<ResultState<GetBalanceResponse>>(ResultState.Loading)
    val getBalanceState: StateFlow<ResultState<GetBalanceResponse>> = _getBalanceState
    private var cachedBalance: GetBalanceResponse? = null

    fun getProfile() {
        viewModelScope.launch {
            _profileState.value = ResultState.Loading
            try {
                val response = repository.getProfile()
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _profileState.value = ResultState.Success(data)
                    } else {
                        _profileState.value = ResultState.Error("Empty response body")
                    }
                } else {
                    _profileState.value = ResultState.Error(response.message())
                }
            } catch (e: Exception) {
                _profileState.value = ResultState.Error(e.message.toString())
            }
        }
    }

    fun updateProfile(request: UpdateProfileRequest) {
        viewModelScope.launch {
            _updateProfileState.value = ResultState.Loading
            try {
                val response = repository.updateProfile(request)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _updateProfileState.value = ResultState.Success(data)
                    } else {
                        _updateProfileState.value = ResultState.Error("Empty response body")
                    }
                } else {
                    _updateProfileState.value = ResultState.Error(response.message())
                }
            } catch (e: Exception) {
                _updateProfileState.value = ResultState.Error(e.message.toString())
            }
        }
    }

    fun getBalance(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            // Pakai cache kalau ada dan tidak dipaksa refresh
            if (!forceRefresh && cachedBalance != null) {
                _getBalanceState.value = ResultState.Success(cachedBalance!!)
                return@launch
            }

            _getBalanceState.value = ResultState.Loading
            try {
                val response = repository.getBalance()
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        cachedBalance = data // Simpan ke cache
                        _getBalanceState.value = ResultState.Success(data)
                    } else {
                        _getBalanceState.value = ResultState.Error("Empty response body")
                    }
                } else {
                    _getBalanceState.value = ResultState.Error(response.message())
                }
            } catch (e: Exception) {
                _getBalanceState.value = ResultState.Error(e.message.toString())
            }
        }
    }

    fun clearBalanceCache() {
        cachedBalance = null
    }

    fun refreshAndStoreUserProfile() {
        viewModelScope.launch {
            try {
                val response = repository.getProfile()
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        userPreference.saveUser(it)
                        _profileState.value = ResultState.Success(response.body()!!)
                    }
                } else {
                    _profileState.value = ResultState.Error(response.message())
                }
            } catch (e: Exception) {
                _profileState.value = ResultState.Error(e.message.toString())
            }
        }
    }

}