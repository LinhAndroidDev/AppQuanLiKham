package com.example.appkhambenh.ui.ui.user.doctor

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityInfoDoctorBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.data.remote.model.Specialist
import com.example.appkhambenh.ui.model.Hour
import com.example.appkhambenh.ui.model.Time
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.appointment.MakeAppointActivity
import com.example.appkhambenh.ui.ui.user.appointment.OnlineConsultationActivity
import com.example.appkhambenh.ui.ui.user.doctor.adapter.HourWorkingAdapter
import com.example.appkhambenh.ui.ui.user.doctor.adapter.TimeWorkingAdapter
import com.example.appkhambenh.ui.ui.user.hospital.InfoHospitalActivity
import com.example.appkhambenh.ui.utils.collapseText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class InfoDoctorActivity : BaseActivity<EmptyViewModel, ActivityInfoDoctorBinding>() {

    private val times by lazy { arrayListOf<Time>() }
    private val timeAdapter by lazy { TimeWorkingAdapter(this@InfoDoctorActivity, times) }
    private var isExpandText = false
    private var formatDay = SimpleDateFormat("EE", Locale("vi", "VN"))
    private var formatDayOfMonth = SimpleDateFormat("dd/MM", Locale("vi", "VN"))
    private lateinit var doctor: Specialist

    companion object {
        const val INFORMATION_DOCTOR = "INFORMATION_DOCTOR"
        const val HOUR_INFORMATION_DOCTOR = "HOUR_INFORMATION_DOCTOR"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    @SuppressLint("SetTextI18n")
    private fun initUi() {

        intent.getSerializableExtra(InfoHospitalActivity.OBJECT_DOCTOR).let {
            if(it != null) {
                doctor = it as Specialist
                binding.nameDoctor.text = doctor.userCreateName
                binding.specialistDoctor.text = doctor.nameSpecial
            }
        }


        binding.headerInforDoctor.setTitle(getString(R.string.infor_doctor))

        val str = "Bác Sĩ Ơi - Phòng Khám 020"
        val content = SpannableString(str)
        content.setSpan(UnderlineSpan(), 0, str.length, 0)
        content.setSpan(StyleSpan(Typeface.BOLD), 0, str.length, 0)
        binding.txtClinic.text = content

        lifecycleScope.launch {
            delay(300L)
            withContext(Dispatchers.Main) {
                createTime()
            }
        }

        val info =
            "BS.PSG.TS \nBác sĩ nội trú \n25 năm trong chuyên ngành Tai Mũi Họng \n- Chữa các bệnh vùng Tai - Mũi - Họng: viêm tai, viêm mũi xoang, viêm họng, viêm loét họng, miệng, tăng tiết nước bọt vùng khoang miệng... bệnh lý các khối u vùng đầu mặt cổ \n- Chuyên gia về trị liệu giọng nói cho những người làm các nghề liên quan đến giọng như phát thanh viên, ca sĩ, giáo viên... \n- Các rối loạn giọng sau phẫu thuật \n- Chuyên gia điều trị chóng mặt, rối loạn thăng bằng, ù tai \n- Điều trị các đau mạn tính vùng sọ mặt, vùng lưỡi"
        var spannable = SpannableStringBuilder()
        if (info.length > 120) {
            spannable = collapseText(this, info.substring(0, 120) + "... ${getString(R.string.see_more)}")
        }

        binding.tvInforDoctor.text = spannable
        binding.tvInforDoctor.setOnClickListener {
            isExpandText = !isExpandText
            binding.tvInforDoctor.text = if (isExpandText) info else spannable
        }

        binding.scroll.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
                if ((scrollY + 20) >= binding.scroll.getChildAt(0).measuredHeight - binding.scroll.measuredHeight)
                    enableOnlineConsult() else disableOnlineConsult()
            })

        binding.seeMoreDate.setOnClickListener {
            val intent = Intent(this@InfoDoctorActivity, OnlineConsultationActivity::class.java)
            startActivity(intent)
        }

        binding.onlineConsult.setOnClickListener {
            val intent = Intent(this@InfoDoctorActivity, OnlineConsultationActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun createTime() {
        val hours = arrayListOf(
            Hour(arrayListOf("11:00", "11:15", "11:30", "11:45")),
            Hour(arrayListOf("09:00", "09:15", "09:30", "09:45", "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45")),
            Hour(arrayListOf("13:00", "13:15", "13:30", "13:45", "14:00", "14:15", "14:30")),
            Hour(arrayListOf("10:00", "10:15", "10:30", "10:45", "11:00", "11:15")),
            Hour(arrayListOf("08:00", "08:15", "08:30", "08:45", "09:00", "09:15", "09:30")),
            Hour(arrayListOf("11:00", "11:15", "11:30", "11:45")),
            Hour(arrayListOf("16:00", "16:15", "16:30", "16:45"))
        )
        for(i in 0 until 7) {
            val calendar: Calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, i)
            times.add(
                Time(
                    formatDay.format(calendar.time),
                    formatDayOfMonth.format(calendar.time),
                    hours[i]
                )
            )
        }

        setChangeTime(times[0].hours?.hour!!)

        binding.rcvTimeWorking.apply {
            layoutManager =
                LinearLayoutManager(this@InfoDoctorActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = timeAdapter
        }

        timeAdapter.onClickTime = {
            binding.rcvHourWorking.postDelayed(Runnable {
                binding.rcvHourWorking.smoothScrollToPosition(0)
            }, 300)
            setChangeTime(times[it].hours?.hour!!)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setChangeTime(hours: ArrayList<String>) {
        val hourAdapter = HourWorkingAdapter(this@InfoDoctorActivity, hours, INFORMATION_DOCTOR)
        binding.rcvHourWorking.apply {
            layoutManager =
                LinearLayoutManager(this@InfoDoctorActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = hourAdapter
        }

        hourAdapter.onClickTime = {
            val intent = Intent(this@InfoDoctorActivity, MakeAppointActivity::class.java)
            intent.putExtra(HOUR_INFORMATION_DOCTOR, it)
            startActivity(intent)
        }

    }

    private fun enableOnlineConsult() {
        binding.onlineConsult.visibility = View.VISIBLE
        binding.onlineConsult.isEnabled = true
    }

    private fun disableOnlineConsult() {
        binding.onlineConsult.visibility = View.INVISIBLE
        binding.onlineConsult.isEnabled = false
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityInfoDoctorBinding.inflate(inflater)
}