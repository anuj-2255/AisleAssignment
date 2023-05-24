package com.example.testassignment.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.testassignment.R
import com.example.testassignment.BR
import com.example.testassignment.databinding.ActivityNotesBinding
import com.example.testassignment.model.bean.ProfileX
import com.example.testassignment.utils.showToast
import com.example.testassignment.view.navigator.NotesNavigator
import com.example.testassignment.view_model.NotesViewModel
import com.happytaxidriver.view.base.BaseActivity
import jp.wasabeef.glide.transformations.BlurTransformation
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesActivity : BaseActivity<ActivityNotesBinding, NotesViewModel>
    (NotesViewModel::class), NotesNavigator {

    override val bindingVariable: Int get() = BR.viewModel
    override val layoutId: Int get() = R.layout.activity_notes
    override val viewModel: NotesViewModel by viewModel()


    companion object {

        fun open(context: Context) {
            context.startActivity(Intent(context, NotesActivity::class.java))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
        viewModel.getNotes()
        initViews()



    }

    override fun showMsg(msg: String) {
        showToast(msg)
    }

    fun initViews() {

        /** All data is static on this screen instructions were not clear.
         * I am not sure that i have bind the response or not.
         * Also bottom nav has same icon because icons were not generating in valid svg format from figma.*/

        binding.apply {
            Glide.with(this@NotesActivity).load(R.drawable.dummy_photo)
                .placeholder(R.drawable.dummy_photo)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                .into(ivBlurOne)

            Glide.with(this@NotesActivity).load(R.drawable.dummy_photo)
                .placeholder(R.drawable.dummy_photo)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                .into(ivBlurTwo)
        }

    }


}