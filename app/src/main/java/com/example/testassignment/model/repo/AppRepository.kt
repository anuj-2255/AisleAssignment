package com.example.testassignment.model.repo

import androidx.lifecycle.MutableLiveData
import com.example.testassignment.model.bean.*
import com.example.testassignment.network.ApiResponse
import com.example.testassignment.network.ApiServices
import com.example.testassignment.network.DataFetchCall
import kotlinx.coroutines.*
import retrofit2.Response

class AppRepository(private val apiServices: ApiServices) {


    fun verifyOtp(request: VerifyOtpRequest,response: MutableLiveData<ApiResponse<VerifyOtpResponse>>) {
        object : DataFetchCall<VerifyOtpResponse>(response) {
            override suspend fun createCallAsync(): Deferred<Response<VerifyOtpResponse>> {
                return apiServices.verifyOtpAsync(request)
            }
        }.execute()
    }

    fun verifyNumber(request: PhoneNumberLoginRequest,response: MutableLiveData<ApiResponse<PhoneVerifyResponse>>) {
        object : DataFetchCall<PhoneVerifyResponse>(response) {
            override suspend fun createCallAsync(): Deferred<Response<PhoneVerifyResponse>> {
                return apiServices.verifyNumberAsync(request)
            }
        }.execute()
    }

    fun getNotesAsync(response: MutableLiveData<ApiResponse<NotesResponse>>) {
        object : DataFetchCall<NotesResponse>(response) {
            override suspend fun createCallAsync(): Deferred<Response<NotesResponse>> {
                return apiServices.getNotesAsync()
            }
        }.execute()
    }


}


