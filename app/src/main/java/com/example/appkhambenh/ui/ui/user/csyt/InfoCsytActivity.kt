package com.example.appkhambenh.ui.ui.user.csyt

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityInfoCsytBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.csyt.adapter.DoctorCsytAdapter
import com.example.appkhambenh.ui.ui.user.csyt.adapter.ServiceCsytAdapter
import com.example.appkhambenh.ui.ui.user.doctor.InfoDoctorActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InfoCsytActivity : BaseActivity<EmptyViewModel, ActivityInfoCsytBinding>() {

    private val fadeIn by lazy { AnimationUtils.loadAnimation(this@InfoCsytActivity, R.anim.anim_show_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    private fun initUi() {
        binding.headerInfoCsyt.setTitle(getString(R.string.info_csyt))

        lifecycleScope.launch {
            delay(300L)
            withContext(Dispatchers.Main){
                setTabLayout()
            }
            delay(300L)
            withContext(Dispatchers.Main){
                binding.rcvBookCsyt.visibility = View.VISIBLE
                binding.rcvBookCsyt.startAnimation(fadeIn)
                getDoctors()
            }
        }
    }

    private fun setTabLayout() {
        binding.tabCsyt.addTab(binding.tabCsyt.newTab().setText(getString(R.string.order_with_doctor)))
        binding.tabCsyt.addTab(binding.tabCsyt.newTab().setText(getString(R.string.order_by_service)))

        binding.tabCsyt.startAnimation(fadeIn)

        binding.tabCsyt.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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

    private fun getDoctors(){
        val doctors = arrayListOf<Int>()
        for(i in 0 until 10){
            doctors.add(1)
        }
        val adapter = DoctorCsytAdapter(doctors)
        binding.rcvBookCsyt.adapter = adapter
        adapter.onClickItem = {
            val intent = Intent(this@InfoCsytActivity, InfoDoctorActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getServices(){
        val services = arrayListOf<Int>()
        for (i in 0 until 10){
            services.add(1)
        }
        val adapter = ServiceCsytAdapter(services)
        binding.rcvBookCsyt.adapter = adapter
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityInfoCsytBinding.inflate(inflater)
}