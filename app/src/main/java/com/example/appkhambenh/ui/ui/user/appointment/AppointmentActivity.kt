package com.example.appkhambenh.ui.ui.user.appointment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityAppointmentBinding
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.user.appointment.time.FragmentTimeWorking

@Suppress("DEPRECATION")
class AppointmentActivity : BaseActivity<EmptyViewModel, ActivityAppointmentBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        replaceFragment(FragmentTimeWorking())
    }

    private fun replaceFragment(fm: Fragment){
        val fg : FragmentTransaction = supportFragmentManager.beginTransaction()
        fg.add(R.id.changeIdAppointment, fm, "FragmentTimeWorking").addToBackStack(null)
            .commit()
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityAppointmentBinding.inflate(inflater)

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val fm = supportFragmentManager.findFragmentById(R.id.changeIdAppointment)
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