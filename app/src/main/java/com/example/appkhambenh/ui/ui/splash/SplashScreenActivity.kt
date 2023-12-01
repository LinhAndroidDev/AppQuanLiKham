package com.example.appkhambenh.ui.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivitySplashScreenBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.HomeActivity
import com.example.appkhambenh.ui.ui.MainActivity
import com.example.appkhambenh.ui.utils.PreferenceKey

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity<EmptyViewModel, ActivitySplashScreenBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        initUi()
    }

    private fun initUi() {
        object : CountDownTimer(3000, 3000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                val isLogin = viewModel.mPreferenceUtil.defaultPref()
                    .getBoolean(PreferenceKey.CHECK_LOGIN, false)
                if (isLogin) {
                    val intent = Intent(this@SplashScreenActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }.start()
    }

    override fun onResume() {
        super.onResume()

        val intro = AnimationUtils.loadAnimation(this, R.anim.intro)
        binding.txtIntro.startAnimation(intro)
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivitySplashScreenBinding.inflate(inflater)
}