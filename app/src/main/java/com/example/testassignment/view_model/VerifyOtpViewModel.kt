package com.example.testassignment.view_model

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.testassignment.ACCESS_TOKEN
import com.example.testassignment.PHONE_NUMBER
import com.example.testassignment.model.bean.PhoneNumberLoginRequest
import com.example.testassignment.model.bean.PhoneVerifyResponse
import com.example.testassignment.model.bean.VerifyOtpRequest
import com.example.testassignment.model.bean.VerifyOtpResponse
import com.example.testassignment.model.repo.AppRepository
import com.example.testassignment.network.ApiResponse
import com.example.testassignment.view.base.BaseViewModel
import com.example.testassignment.view.navigator.VerifyOtpNavigator
import com.orhanobut.hawk.Hawk

class VerifyOtpViewModel(private val repo : AppRepository) :BaseViewModel<VerifyOtpNavigator>() {


    var phoneNumber:String? = if (Hawk.contains(PHONE_NUMBER))Hawk.get(PHONE_NUMBER) else null
        set(value) {
            if (field != value) {
                field = value
                notifyChange()
            }
        }

    var otpStr: String? = null
        set(value) {
            if (field != value) {
                field = value
                notifyChange()
            }
        }

    private var timer: CountDownTimer? = null
    private val FIVE_SECOND_INTERVAL = 1000L


    fun startTimer(totalTimeInterval: Long) {
        timer = object : CountDownTimer(totalTimeInterval, FIVE_SECOND_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("<<Timer", "<<TimeRemaining--> " + millisUntilFinished.toString())
            }

            override fun onFinish() {


            }
        }

        timer?.start()
    }


    fun stopTimer() {
        timer?.cancel()
    }


    private val verifyOtpResponse by lazy {
        MutableLiveData<ApiResponse<VerifyOtpResponse>>()
    }

    private fun getVerifyOtpResponse(): LiveData<ApiResponse<VerifyOtpResponse>> {
        return verifyOtpResponse
    }

    private val verifyOtpObserver: Observer<ApiResponse<VerifyOtpResponse>> by lazy {
        Observer<ApiResponse<VerifyOtpResponse>> {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    getNavigator()?.showLoader()
                }
                ApiResponse.Status.SUCCESS -> {
                    getNavigator()?.hideLoader()
                    it.response?.token?.let {token->
                        Hawk.put(ACCESS_TOKEN,token)
                        getNavigator()?.moveToNotesActivity()

                    }?: run {
                        getNavigator()?.showMsg("run block of verifyOtp")
                    }

                }
                ApiResponse.Status.ERROR -> {
                    getNavigator()?.hideLoader()
                    getNavigator()?.showMsg(
                        it.error?.message ?: "error block of verifyotp"//res.getString(R.string.something_went_wrong)
                    )
                }
            }
        }
    }

    fun verifyOtp(){

        if(otpStr.isNullOrEmpty()){
            getNavigator()?.showMsg("OTP not found")
            return
        }

        val request = VerifyOtpRequest().also {
            it.number =  phoneNumber
            it.otp = otpStr
        }

        repo.verifyOtp(request,verifyOtpResponse)
        getVerifyOtpResponse().observeForever(verifyOtpObserver)

    }


    override fun onCleared() {
        super.onCleared()
        getVerifyOtpResponse().removeObserver(verifyOtpObserver)
    }


}