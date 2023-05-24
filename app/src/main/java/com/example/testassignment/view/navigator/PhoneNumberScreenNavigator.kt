package com.example.testassignment.view.navigator

interface PhoneNumberScreenNavigator {
    fun showMsg(msg:String)
    fun showLoader()
    fun hideLoader()
    fun moveToOtpActivity()
}