package com.example.appkhambenh.ui.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityMainBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.login.FragmentLogin

@Suppress("DEPRECATION")
class MainActivity : BaseActivity<EmptyViewModel, ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        replaceFragment(FragmentLogin())
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityMainBinding.inflate(inflater)

    private fun replaceFragment(fm: Fragment) {
        val fragment: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.changeIdLogin, fm).addToBackStack(null).commit()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val fm = supportFragmentManager.findFragmentById(R.id.changeIdLogin)
        if (fm != null && fm is BaseFragment<*, *>) {
            if (fm.onFragmentBack()) {
                finish()
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }
}