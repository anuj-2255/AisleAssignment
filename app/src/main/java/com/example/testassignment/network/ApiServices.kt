package com.example.testassignment.network


import com.example.testassignment.model.bean.*
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {

    @POST("users/phone_number_login")
    fun verifyNumberAsync(@Body request: PhoneNumberLoginRequest): Deferred<Response<PhoneVerifyResponse>>

    @POST("users/verify_otp")
    fun verifyOtpAsync(@Body request: VerifyOtpRequest): Deferred<Response<VerifyOtpResponse>>

    @GET("users/test_profile_list")
    fun getNotesAsync():Deferred<Response<NotesResponse>>




}