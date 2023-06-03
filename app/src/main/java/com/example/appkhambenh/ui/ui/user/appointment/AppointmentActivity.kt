package com.example.appkhambenh.ui.ui.user.appointment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityAppointmentBinding
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.utils.PreferenceKey

@Suppress("DEPRECATION")
class AppointmentActivity : BaseActivity<EmptyViewModel, ActivityAppointmentBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        replaceFragment(FragmentTimeWorking())
    }

    private fun replaceFragment(fm: Fragment){
        val fg : FragmentTransaction = supportFragmentManager.beginTransaction()
        fg.replace(R.id.changeIdAppointment, fm).addToBackStack(null)
            .commit()
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityAppointmentBinding.inflate(inflater)
}