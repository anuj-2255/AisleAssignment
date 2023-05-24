package com.example.testassignment.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.testassignment.PHONE_NUMBER
import com.example.testassignment.model.bean.PhoneNumberLoginRequest
import com.example.testassignment.model.bean.PhoneVerifyResponse
import com.example.testassignment.model.repo.AppRepository
import com.example.testassignment.network.ApiResponse
import com.example.testassignment.view.base.BaseViewModel
import com.example.testassignment.view.navigator.PhoneNumberScreenNavigator
import com.orhanobut.hawk.Hawk

class PhoneNumberViewModel(private val repo : AppRepository) : BaseViewModel<PhoneNumberScreenNavigator>() {

    var countryCode: String? = null
        set(value) {
            if (field != value) {
                field = value
                notifyChange()
            }
        }

    var phoneNumber: String? = null
        set(value) {
            if (field != value) {
                field = value
                notifyChange()
            }
        }


    private val phoneNumberResponse by lazy {
        MutableLiveData<ApiResponse<PhoneVerifyResponse>>()
    }

    private fun getPhoneNUmberResponse(): LiveData<ApiResponse<PhoneVerifyResponse>> {
        return phoneNumberResponse
    }

    private val phoneNumberObserver: Observer<ApiResponse<PhoneVerifyResponse>> by lazy {
        Observer<ApiResponse<PhoneVerifyResponse>> {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    getNavigator()?.showLoader()
                }
                ApiResponse.Status.SUCCESS -> {
                    getNavigator()?.hideLoader()
                    it.response?.status?.let {status->

                        if (status){
                            Hawk.put(PHONE_NUMBER,countryCode+phoneNumber)
                            getNavigator()?.moveToOtpActivity()
                        }else{
                            getNavigator()?.showMsg("Status is $status")
                        }

                    }?: run {
                        getNavigator()?.showMsg("Status not found")
                    }

                }
                ApiResponse.Status.ERROR -> {
                    getNavigator()?.hideLoader()
                    getNavigator()?.showMsg(
                        it.error?.message ?: "something went wrong"//res.getString(R.string.something_went_wrong)
                    )
                }
            }
        }
    }

    fun verifyPhoneNumber(){
        if(countryCode.isNullOrEmpty() || phoneNumber.isNullOrEmpty()){
            getNavigator()?.showMsg("Either Number is Empty")
           return
        }

        val request = PhoneNumberLoginRequest().also {
            it.number = countryCode+phoneNumber
        }

        repo.verifyNumber(request,phoneNumberResponse)
        getPhoneNUmberResponse().observeForever(phoneNumberObserver)


    }


    override fun onCleared() {
        super.onCleared()
        getPhoneNUmberResponse().removeObserver(phoneNumberObserver)
    }

}