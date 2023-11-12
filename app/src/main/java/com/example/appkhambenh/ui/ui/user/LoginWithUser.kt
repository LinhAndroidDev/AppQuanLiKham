package com.example.appkhambenh.ui.ui.user

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityLoginWithUserBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.user.navigation.information.FragmentInformation
import com.example.appkhambenh.ui.ui.user.navigation.notification.FragmentNotification
import com.example.appkhambenh.ui.ui.user.navigation.setting.FragmentSetting

@Suppress("DEPRECATION")
class LoginWithUser : BaseActivity<LoginWithUserViewModel, ActivityLoginWithUserBinding>() {
    var isRotateAdd = false

    companion object {
        const val RESULT = "RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    private fun initUi() {
        replaceFragment(FragmentLoginWithUser())

        binding.bottomNvg.setOnItemSelectedListener {
            rotateIconRegister()
            val fmCurrent = supportFragmentManager.findFragmentById(R.id.changeIdLoginWithUser)
            when(it.itemId){
                R.id.home -> if(fmCurrent !is FragmentLoginWithUser) replaceFragment(FragmentLoginWithUser())
                R.id.profile -> if(fmCurrent !is FragmentInformation) replaceFragment(FragmentInformation())
                R.id.notification -> if(fmCurrent !is FragmentNotification) replaceFragment(FragmentNotification())
                R.id.setting -> if(fmCurrent !is FragmentSetting) replaceFragment(FragmentSetting())
            }
            true
        }

        binding.registerAppoint.setOnClickListener {
            rotateIconRegister()
        }
    }

    fun rotateIconRegister(){
        isRotateAdd = !isRotateAdd
        binding.imgAdd.rotation = if(isRotateAdd) 45f else 0f
        binding.bottomNvg.menu.findItem(R.id.register).isChecked = true
    }

    private fun replaceFragment(fm: Fragment){
        val fg = supportFragmentManager.beginTransaction()
        fg.replace(R.id.changeIdLoginWithUser, fm).commit()
        isRotateAdd = false
        binding.imgAdd.rotation = 0f
    }

    internal fun goToEditProfile(){
        replaceFragment(FragmentInformation())
        binding.bottomNvg.menu.findItem(R.id.profile).isChecked = true
    }

    override fun onBackPressed() {
        val fmCurrent = supportFragmentManager.findFragmentById(R.id.changeIdLoginWithUser)
        if(fmCurrent is FragmentLoginWithUser){
            super.onBackPressed()
        }else {
            replaceFragment(FragmentLoginWithUser())
            binding.bottomNvg.menu.findItem(R.id.home).isChecked = true
            isRotateAdd = false
            binding.imgAdd.rotation = 0f
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityLoginWithUserBinding.inflate(inflater)
}