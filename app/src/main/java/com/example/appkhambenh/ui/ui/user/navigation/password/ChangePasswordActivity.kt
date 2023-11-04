package com.example.appkhambenh.ui.ui.user.navigation.password

import android.os.Bundle
import android.view.LayoutInflater
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityChangePasswordBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel

class ChangePasswordActivity : BaseActivity<EmptyViewModel, ActivityChangePasswordBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragmentCheckPassword = FragmentCheckPassword()
        val fg = supportFragmentManager.beginTransaction()
        fg.add(R.id.changeIdPassword, fragmentCheckPassword, "FragmentCheckPassword")
            .addToBackStack(null).commit()
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityChangePasswordBinding.inflate(inflater)

    override fun onBackPressed() {
        val fm = supportFragmentManager.findFragmentById(R.id.changeIdPassword)
        if(fm != null && fm is BaseFragment<*, *>){
            if(fm.onFragmentBack()){
                finish()
            }else{
                super.onBackPressed()
            }
        }else{
            super.onBackPressed()
        }
    }
}