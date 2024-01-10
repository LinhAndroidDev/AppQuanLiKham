package com.example.appkhambenh.ui.ui.user.avatar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivitySeeAvatarBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.EmptyViewModel

class SeeAvatarActivity : BaseActivity<EmptyViewModel, ActivitySeeAvatarBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.un_blur_view,R.anim.blur_view)

        initUi()
    }

    private fun initUi() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        sharePrefer.getUserAvatar().let {
            if(it.isNotEmpty()) {
                Glide.with(this)
                    .load(it)
                    .error(R.drawable.user_ad)
                    .into(binding.imgSeeAvatar)
            }
        }

        binding.backSeeAvatar.setOnClickListener { back() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.un_blur_view,R.anim.blur_view)
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivitySeeAvatarBinding.inflate(inflater)
}