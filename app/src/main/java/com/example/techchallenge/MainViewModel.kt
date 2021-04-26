package com.example.techchallenge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.techchallenge.data.Resource
import com.example.techchallenge.data.RestApi
import com.example.techchallenge.data.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val restApi: RestApi
) : ViewModel() {

    private val _verifyPhoneNumber by lazy { MutableSharedFlow<Resource<PhoneNumberLoginResponse>>() }
    val verifyPhoneNumber = _verifyPhoneNumber.asSharedFlow()

    private val _verifyOtp by lazy { MutableSharedFlow<Resource<VerifyOtpResponse>>() }
    val verifyOtp = _verifyOtp.asSharedFlow()

    private val _profileResponse by lazy { MutableLiveData<Resource<GetProfileResponse>>() }
    val profileResponse
        get() = _profileResponse

    fun verifyPhoneNumber(number: String) {
        viewModelScope.launch {
            _verifyPhoneNumber.emit(Resource.Loading())
            try {
                val response = restApi.phoneNumberLogin(PhoneNumber(number = number))
                _verifyPhoneNumber.emit(Resource.Success(response))
            } catch (e: Exception) {
                _verifyPhoneNumber.emit(Resource.Error(e.message))
            }
        }
    }

    fun verifyOtp(phoneNumber: String, otp: String) {
        viewModelScope.launch {
            _verifyOtp.emit(Resource.Loading())
            try {
                val response = restApi.verifyOtp(
                    Otp(
                        number = phoneNumber,
                        otp = otp
                    )
                )
                _verifyOtp.emit(Resource.Success(response))
            } catch (e: Exception) {
                _verifyOtp.emit(Resource.Error(e.message))
            }
        }
    }

    fun getProfile(token: String) {
        viewModelScope.launch {
            _profileResponse.postValue(Resource.Loading())
            try {
                val response = restApi.getProfileList(
                    authToken = token
                )
                _profileResponse.postValue(Resource.Success(response))
            } catch (e: Exception) {
                _profileResponse.postValue(Resource.Error(e.message))
            }
        }
    }

}