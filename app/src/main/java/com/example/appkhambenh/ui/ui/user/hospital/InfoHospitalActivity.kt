package com.example.appkhambenh.ui.ui.user.hospital

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityInfoHospitalBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.data.remote.model.Hospital
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.common.dialog.DialogHourWorking
import com.example.appkhambenh.ui.ui.user.hospital.adapter.ServiceHospitalAdapter
import com.example.appkhambenh.ui.ui.user.doctor.InfoDoctorActivity
import com.example.appkhambenh.ui.ui.user.hospital.adapter.DoctorHospitalAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InfoHospitalActivity : BaseActivity<EmptyViewModel, ActivityInfoHospitalBinding>() {
    private lateinit var hospital: Hospital

    private val fadeIn by lazy {
        AnimationUtils.loadAnimation(
            this@InfoHospitalActivity,
            R.anim.anim_show_view
        )
    }

    companion object {
        const val OBJECT_DOCTOR = "OBJECT_DOCTOR"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    private fun initUi() {

        hospital = intent.getParcelableExtra<Hospital>(HospitalActivity.OBJECT_HOSPITAL) as Hospital

        binding.headerInfoHospital.setTitle(getString(R.string.info_hospital))
        binding.nameHospital.text = hospital.name

        lifecycleScope.launch {
            delay(300L)
            withContext(Dispatchers.Main) {
                setTabLayout()
            }
            delay(300L)
            withContext(Dispatchers.Main) {
                binding.rcvBookHospital.visibility = View.VISIBLE
                binding.rcvBookHospital.startAnimation(fadeIn)
                getDoctors()
            }
        }

        binding.seeSchedule.setOnClickListener {
            val dialogHourWorking = DialogHourWorking()
            dialogHourWorking.show(supportFragmentManager, "")
        }
    }

    private fun setTabLayout() {
        binding.tabHospital.addTab(
            binding.tabHospital.newTab().setText(getString(R.string.order_with_doctor))
        )
        binding.tabHospital.addTab(
            binding.tabHospital.newTab().setText(getString(R.string.order_by_service))
        )

        binding.tabHospital.startAnimation(fadeIn)

        binding.tabHospital.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    getDoctors()
                } else if (tab.position == 1) {
                    getServices()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    private fun getDoctors() {
        val adapter = DoctorHospitalAdapter()
        adapter.items = hospital.specialist
        binding.rcvBookHospital.adapter = adapter
        adapter.onClickItem = {
            val intent = Intent(this@InfoHospitalActivity, InfoDoctorActivity::class.java)
            intent.putExtra(OBJECT_DOCTOR, it)
            startActivity(intent)
        }
    }

    private fun getServices() {
        val adapter = ServiceHospitalAdapter()
        adapter.items = hospital.specialist
        binding.rcvBookHospital.adapter = adapter
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityInfoHospitalBinding.inflate(inflater)
}