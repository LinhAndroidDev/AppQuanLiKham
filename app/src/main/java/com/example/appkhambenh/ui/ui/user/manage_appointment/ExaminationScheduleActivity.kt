package com.example.appkhambenh.ui.ui.user.manage_appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityExaminationScheduleBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.manage_appointment.adapter.ExaminationAdapter
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay

class ExaminationScheduleActivity :
    BaseActivity<EmptyViewModel, ActivityExaminationScheduleBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.headerExamination.setTitle("Lịch khám")

        binding.examinationSchedule.statusChecked()
        binding.reExaminationSchedule.statusUnChecked()

        initListExamination()

        binding.layoutContent.visibility = View.GONE

        lifecycleScope.launch {
            kotlinx.coroutines.delay(600L)
            binding.layoutContent.visibility = View.VISIBLE
            val anim = AnimationUtils.loadAnimation(
                this@ExaminationScheduleActivity,
                R.anim.enter_view_left
            )
            binding.layoutContent.startAnimation(anim)
        }

        binding.examinationSchedule.setOnClickListener {
            binding.examinationSchedule.statusChecked()
            binding.reExaminationSchedule.statusUnChecked()
        }

        binding.reExaminationSchedule.setOnClickListener {
            binding.examinationSchedule.statusUnChecked()
            binding.reExaminationSchedule.statusChecked()
        }

        binding.tabExamination.addTab(binding.tabExamination.newTab().setText(R.string.pending_ex))
        binding.tabExamination.addTab(binding.tabExamination.newTab().setText(R.string.approved_ex))
        binding.tabExamination.addTab(
            binding.tabExamination.newTab().setText(R.string.examining_ex)
        )
        binding.tabExamination.addTab(binding.tabExamination.newTab().setText(R.string.complete_ex))
        binding.tabExamination.addTab(binding.tabExamination.newTab().setText(R.string.too_late_ex))
        binding.tabExamination.addTab(binding.tabExamination.newTab().setText(R.string.canceled_ex))
    }

    private fun initListExamination() {
        val adapter = ExaminationAdapter(arrayListOf(1, 1, 1, 1, 1, 1, 1))
        binding.rcvExamination.adapter = adapter
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityExaminationScheduleBinding.inflate(inflater)
}