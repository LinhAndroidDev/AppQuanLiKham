package com.example.appkhambenh.ui.ui.user.doctor

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityInfoDoctorBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.model.Hour
import com.example.appkhambenh.ui.model.Time
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.doctor.adapter.HourWorkingAdapter
import com.example.appkhambenh.ui.ui.user.doctor.adapter.TimeWorkingAdapter
import com.example.appkhambenh.ui.utils.collapseText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InfoDoctorActivity : BaseActivity<EmptyViewModel, ActivityInfoDoctorBinding>() {

    private val times by lazy { arrayListOf<Time>() }
    private val timeAdapter by lazy { TimeWorkingAdapter(this@InfoDoctorActivity, times) }
    private var isExpandText = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    @SuppressLint("SetTextI18n")
    private fun initUi() {
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
            spannable = collapseText(this, info.substring(0, 120) + "... Xem thêm")
        }

        binding.tvInforDoctor.text = spannable
        binding.tvInforDoctor.setOnClickListener {
            isExpandText = !isExpandText
            binding.tvInforDoctor.text = if (isExpandText) info else spannable
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun createTime() {
        times.add(Time("Thứ 2", "27/11", Hour(arrayListOf("11:00", "11:15", "11:30", "11:45"))))
        times.add(
            Time(
                "Thứ 3",
                "28/11",
                Hour(
                    arrayListOf(
                        "09:00",
                        "09:15",
                        "09:30",
                        "09:45",
                        "10:00",
                        "10:15",
                        "10:30",
                        "10:45",
                        "11:00",
                        "11:15",
                        "11:30",
                        "11:45"
                    )
                )
            )
        )
        times.add(
            Time(
                "Thứ 4",
                "29/11",
                Hour(arrayListOf("13:00", "13:15", "13:30", "13:45", "14:00", "14:15", "14:30"))
            )
        )
        times.add(
            Time(
                "Thứ 5",
                "30/11",
                Hour(arrayListOf("10:00", "10:15", "10:30", "10:45", "11:00", "11:15"))
            )
        )
        times.add(
            Time(
                "Thứ 6",
                "01/12",
                Hour(arrayListOf("08:00", "08:15", "08:30", "08:45", "09:00", "09:15", "09:30"))
            )
        )
        times.add(Time("Thứ 7", "02/12", Hour(arrayListOf("11:00", "11:15", "11:30", "11:45"))))
        times.add(Time("Chủ Nhật", "03/12", Hour(arrayListOf("16:00", "16:15", "16:30", "16:45"))))

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
        binding.rcvHourWorking.apply {
            layoutManager =
                LinearLayoutManager(this@InfoDoctorActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = HourWorkingAdapter(this@InfoDoctorActivity, hours)
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityInfoDoctorBinding.inflate(inflater)
}