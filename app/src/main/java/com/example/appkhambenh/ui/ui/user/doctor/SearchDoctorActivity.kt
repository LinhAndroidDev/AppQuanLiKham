package com.example.appkhambenh.ui.ui.user.doctor

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivitySearchDoctorBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel

class SearchDoctorActivity : BaseActivity<EmptyViewModel, ActivitySearchDoctorBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        replaceFragment(FragmentSearchDoctor())
    }

    private fun replaceFragment(fm: Fragment){
        val fg = supportFragmentManager.beginTransaction()
        fg.add(R.id.changeIdDoctor, fm, "FragmentSearchDoctor")
            .addToBackStack(null).commit()
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivitySearchDoctorBinding.inflate(inflater)

    override fun onBackPressed() {
        val fm = supportFragmentManager.findFragmentById(R.id.changeIdDoctor)
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