package com.example.appkhambenh.ui.ui.doctor.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appkhambenh.ui.ui.doctor.treatment.FragmentBloodTest
import com.example.appkhambenh.ui.ui.doctor.treatment.FragmentCT
import com.example.appkhambenh.ui.ui.doctor.treatment.FragmentClinicalExamination
import com.example.appkhambenh.ui.ui.doctor.treatment.FragmentDiagnose
import com.example.appkhambenh.ui.ui.doctor.treatment.FragmentListOfService
import com.example.appkhambenh.ui.ui.doctor.treatment.FragmentMRI
import com.example.appkhambenh.ui.ui.doctor.treatment.FragmentSuperSonic
import com.example.appkhambenh.ui.ui.doctor.treatment.FragmentVitalChart
import com.example.appkhambenh.ui.ui.doctor.treatment.FragmentXray

class PagerTreatmentAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private val fragments by lazy {
        mutableListOf(
            FragmentListOfService(),
            FragmentVitalChart(),
            FragmentClinicalExamination(),
            FragmentBloodTest(),
            FragmentSuperSonic(),
            FragmentXray(),
            FragmentMRI(),
            FragmentCT(),
            FragmentDiagnose(),
        )
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun getFragment(position: Int): Fragment? {
        return fragments.getOrNull(position)
    }

}