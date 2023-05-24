package com.example.testassignment.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.testassignment.BR
import com.example.testassignment.R
import com.example.testassignment.databinding.ActivityVerifyOtpBinding
import com.example.testassignment.utils.showToast
import com.example.testassignment.view.navigator.VerifyOtpNavigator
import com.example.testassignment.view_model.VerifyOtpViewModel
import com.happytaxidriver.view.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class VerifyOtpActivity : BaseActivity<ActivityVerifyOtpBinding, VerifyOtpViewModel>
    (VerifyOtpViewModel::class), VerifyOtpNavigator {

    override val bindingVariable: Int get() = BR.viewModel
    override val layoutId: Int get() = R.layout.activity_verify_otp
    override val viewModel: VerifyOtpViewModel by viewModel()


    companion object {

        fun open(context: Context) {
            context.startActivity(Intent(context, VerifyOtpActivity::class.java))
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)

    }

    override fun showMsg(msg: String) {
        showToast(msg)
    }

    override fun moveToNotesActivity() {
        NotesActivity.open(this)
    }


}