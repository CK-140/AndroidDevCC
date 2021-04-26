package com.example.techchallenge.data

import com.example.techchallenge.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RestApi {

    @POST("phone_number_login")
    suspend fun phoneNumberLogin(
        @Body number: PhoneNumber
    ): PhoneNumberLoginResponse

    @POST("verify_otp")
    suspend fun verifyOtp(
        @Body otp: Otp
    ): VerifyOtpResponse

    @GET("test_profile_list")
    suspend fun getProfileList(
        @Header("Authorization") authToken: String
    ): GetProfileResponse

}