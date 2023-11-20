package com.example.appkhambenh.ui.ui.user.csyt

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityConnectCsytBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel

@Suppress("DEPRECATION")
class ConnectCsytActivity : BaseActivity<EmptyViewModel, ActivityConnectCsytBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        replaceFragment(FragmentConnectCsyt())
    }

    private fun replaceFragment(fm: Fragment){
        val fg = supportFragmentManager.beginTransaction()
        fg.replace(R.id.changeIdCsyt, fm).commit()
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityConnectCsytBinding.inflate(inflater)

    override fun onBackPressed() {
        val fm = supportFragmentManager.findFragmentById(R.id.changeIdCsyt)
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