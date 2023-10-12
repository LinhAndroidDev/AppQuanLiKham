package com.example.appkhambenh.ui.ui.user.manage_appointment

import android.os.Bundle
import android.view.LayoutInflater
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityManageAppointmentBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel

class ManageAppointmentActivity : BaseActivity<EmptyViewModel, ActivityManageAppointmentBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragmentManageAppointment = FragmentManageAppointment()
        val fm = supportFragmentManager.beginTransaction()
        fm.replace(R.id.changeIdManageAppointment, fragmentManageAppointment)
            .addToBackStack(null).commit()
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityManageAppointmentBinding.inflate(inflater)

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val fm = supportFragmentManager.findFragmentById(R.id.changeIdManageAppointment)
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