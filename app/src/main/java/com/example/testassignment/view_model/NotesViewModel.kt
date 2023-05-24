package com.example.testassignment.view_model

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.testassignment.PHONE_NUMBER
import com.example.testassignment.model.bean.NotesResponse
import com.example.testassignment.model.bean.PhoneNumberLoginRequest
import com.example.testassignment.model.bean.PhoneVerifyResponse
import com.example.testassignment.model.repo.AppRepository
import com.example.testassignment.network.ApiResponse
import com.example.testassignment.view.base.BaseViewModel
import com.example.testassignment.view.navigator.NotesNavigator
import com.orhanobut.hawk.Hawk

class NotesViewModel(private val repo : AppRepository) :BaseViewModel<NotesNavigator>() {

    /*@Bindable
    var tab = -1
        set(value) {
            if (value != field) {
                field = value
                getNavigator()?.switchTab(value)
                notifyChange()
            }
        }*/



    private val notesResponse by lazy {
        MutableLiveData<ApiResponse<NotesResponse>>()
    }

    private fun getNotesResponse(): LiveData<ApiResponse<NotesResponse>> {
        return notesResponse
    }

    private val notesObserver: Observer<ApiResponse<NotesResponse>> by lazy {
        Observer<ApiResponse<NotesResponse>> {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    getNavigator()?.showLoader()
                }
                ApiResponse.Status.SUCCESS -> {
                    getNavigator()?.hideLoader()
                    it.response?.likes?.let {likes->


                    }?: run {
                        getNavigator()?.showMsg("run block of notes api")
                    }

                }
                ApiResponse.Status.ERROR -> {
                    getNavigator()?.hideLoader()
                    getNavigator()?.showMsg(
                        it.error?.message ?: "error block of notes api"//res.getString(R.string.something_went_wrong)
                    )
                }
            }
        }
    }

    fun getNotes(){

        repo.getNotesAsync(notesResponse)
        getNotesResponse().observeForever(notesObserver)

    }


    override fun onCleared() {
        super.onCleared()
        getNotesResponse().removeObserver(notesObserver)
    }



}