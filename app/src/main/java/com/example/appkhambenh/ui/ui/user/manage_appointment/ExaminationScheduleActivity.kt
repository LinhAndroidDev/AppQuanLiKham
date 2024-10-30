package com.example.appkhambenh.ui.ui.user.manage_appointment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityExaminationScheduleBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.user.appointment.MakeAppointActivity
import com.example.appkhambenh.ui.ui.user.manage_appointment.adapter.ExaminationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExaminationScheduleActivity :
    BaseActivity<ExaminationScheduleViewModel, ActivityExaminationScheduleBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            headerExamination.setTitle("Lịch khám")
            examinationSchedule.statusChecked()
            reExaminationSchedule.statusUnChecked()
            layoutContent.visibility = View.GONE

            root.postDelayed(600L) {
                layoutContent.visibility = View.VISIBLE
                val anim = AnimationUtils.loadAnimation(
                    this@ExaminationScheduleActivity,
                    R.anim.enter_view_left
                )
                layoutContent.startAnimation(anim)
            }

            examinationSchedule.setOnClickListener {
                examinationSchedule.statusChecked()
                reExaminationSchedule.statusUnChecked()
            }

            reExaminationSchedule.setOnClickListener {
                examinationSchedule.statusUnChecked()
                reExaminationSchedule.statusChecked()
            }

            tabExamination.apply {
                addTab(this.newTab().setText(R.string.pending_ex))
                addTab(this.newTab().setText(R.string.approved_ex))
                addTab(this.newTab().setText(R.string.examining_ex))
                addTab(this.newTab().setText(R.string.complete_ex))
                addTab(this.newTab().setText(R.string.too_late_ex))
                addTab(this.newTab().setText(R.string.canceled_ex))
            }
        }
    }

    override fun bindData() {
        super.bindData()

        viewModel.loading.observe(this) {
            binding.show.loading.isVisible = it
        }

        viewModel.getListBooking()

        lifecycleScope.launch {
            viewModel.detailAppointments.collect { detailAppoint ->
                detailAppoint?.let {
                    ExaminationAdapter().apply {
                        items = it
                        binding.rcvExamination.adapter = this
                        onClickItem = {
                            val intent = Intent(this@ExaminationScheduleActivity, DetailAppointmentActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityExaminationScheduleBinding.inflate(inflater)
}