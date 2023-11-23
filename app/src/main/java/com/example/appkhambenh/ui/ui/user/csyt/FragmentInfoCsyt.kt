package com.example.appkhambenh.ui.ui.user.csyt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentInfoCsytBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.csyt.adapter.DoctorCsytAdapter
import com.example.appkhambenh.ui.ui.user.csyt.adapter.ServiceCsytAdapter
import com.example.appkhambenh.ui.ui.user.csyt.adapter.ViewpagerCsytAdapter
import com.google.android.material.tabs.TabLayout


@Suppress("DEPRECATION")
class FragmentInfoCsyt : BaseFragment<EmptyViewModel, FragmentInfoCsytBinding>(){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {
        binding.headerInfoCsyt.setTitle(getString(R.string.info_csyt))

        getDoctors()

        binding.tabCsyt.addTab(binding.tabCsyt.newTab().setText("ĐẶT THEO BÁC SĨ"))
        binding.tabCsyt.addTab(binding.tabCsyt.newTab().setText("ĐẶT THEO DỊCH VỤ"))

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

    fun getDoctors(){
        val doctors = arrayListOf<Int>()
        for(i in 0 until 10){
            doctors.add(1)
        }
        val adapter = DoctorCsytAdapter(doctors)
        binding.rcvBookCsyt.adapter = adapter
    }

    fun getServices(){
        val services = arrayListOf<Int>()
        for (i in 0 until 10){
            services.add(1)
        }
        val adapter = ServiceCsytAdapter(services)
        binding.rcvBookCsyt.adapter = adapter
    }

    override fun onFragmentBack(): Boolean {
        return false
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentInfoCsytBinding.inflate(inflater)

}