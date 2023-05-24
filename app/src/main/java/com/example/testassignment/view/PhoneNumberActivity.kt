package com.example.testassignment.view

import android.os.Bundle
import com.example.testassignment.R
import com.example.testassignment.BR
import com.example.testassignment.databinding.ActivityPhoneNumberScreenBinding
import com.example.testassignment.utils.showToast
import com.example.testassignment.view.navigator.PhoneNumberScreenNavigator
import com.example.testassignment.view_model.PhoneNumberViewModel
import com.happytaxidriver.view.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhoneNumberActivity : BaseActivity<ActivityPhoneNumberScreenBinding, PhoneNumberViewModel>
    (PhoneNumberViewModel::class),PhoneNumberScreenNavigator {

    override val bindingVariable: Int get() = BR.viewModel
    override val layoutId: Int get() = R.layout.activity_phone_number_screen
    override val viewModel: PhoneNumberViewModel by viewModel()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)

    }

    override fun showMsg(msg: String) {
        showToast(msg)
    }

    override fun moveToOtpActivity() {
        VerifyOtpActivity.open(this)
    }
}