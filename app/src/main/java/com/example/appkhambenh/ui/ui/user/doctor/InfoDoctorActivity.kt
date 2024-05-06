package com.example.appkhambenh.ui.ui.user.doctor

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.postDelayed
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityInfoDoctorBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.data.remote.model.DoctorModel
import com.example.appkhambenh.ui.data.remote.model.HourModel
import com.example.appkhambenh.ui.model.Time
import com.example.appkhambenh.ui.ui.user.appointment.MakeAppointActivity
import com.example.appkhambenh.ui.ui.user.appointment.OnlineConsultationActivity
import com.example.appkhambenh.ui.ui.user.doctor.adapter.HourWorkingAdapter
import com.example.appkhambenh.ui.ui.user.doctor.adapter.TimeWorkingAdapter
import com.example.appkhambenh.ui.utils.DateUtils
import com.example.appkhambenh.ui.utils.collapseText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class InfoDoctorActivity : BaseActivity<InfoDoctorViewModel, ActivityInfoDoctorBinding>() {
    private val times by lazy { arrayListOf<Time>() }
    private val timeAdapter by lazy { TimeWorkingAdapter(this@InfoDoctorActivity) }
    private lateinit var hourAdapter: HourWorkingAdapter
    private var isExpandText = false
    private var formatDay = SimpleDateFormat("EE", Locale("vi", "VN"))
    private var formatDayOfMonth = SimpleDateFormat("dd/MM", Locale("vi", "VN"))
    @SuppressLint("SimpleDateFormat")
    private var formatDate = SimpleDateFormat(DateUtils.DAY_OF_YEAR)
    private var doctor: DoctorModel? = null
    private var timeWorking = arrayListOf<ArrayList<HourModel>>()
    private var date = ""

    companion object {
        const val INFORMATION_DOCTOR = "INFORMATION_DOCTOR"
        const val TIME_WORKING_DOCTOR = "TIME_WORKING_DOCTOR"
        const val HOUR_WORKING_DOCTOR = "HOUR_WORKING_DOCTOR"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
        onClickView()
    }

    private fun initTimeWorking() {
        for(i in 0 until 7) {
            val calendar: Calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, i)
            times.add(
                Time(
                    day = formatDay.format(calendar.time),
                    time = formatDayOfMonth.format(calendar.time),
                    hours = timeWorking[0],
                    date = formatDate.format(calendar.time)
                )
            )
        }

        setChangeTime(timeWorking[0])
        date = times[0].date.toString()
        timeAdapter.apply {
            items = times
            binding.rcvTimeWorking.adapter = this
            onClickTime = {
                if (it <= timeWorking.size - 1) {
                    date = times[it].date.toString()
                    binding.rcvHourWorking.postDelayed(300L) {
                        binding.rcvHourWorking.smoothScrollToPosition(0)
                    }
                    setChangeTime(timeWorking[it])
                } else {
                    hourAdapter.clearList()
                }
            }
        }
    }

    private fun onClickView() {
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

    @SuppressLint("SetTextI18n")
    private fun initUi() {

//        intent.getParcelableExtra<Service>(InfoHospitalActivity.OBJECT_DOCTOR).let {
//            if(it != null) {
//                binding.nameDoctor.text = it.userCreateName
//                binding.specialistDoctor.text = it.nameSpecial
//            }
//        }

        intent.getParcelableExtra<DoctorModel>(SearchDoctorActivity.OBJECT_DOCTOR).let {
            if(it != null) {
                doctor = it
                binding.nameDoctor.text = it.name
                binding.tvHospital.text = it.hospitalName

                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.getTimeWorking(it.id, DateUtils.getDateCurrentToLong())
                    viewModel.timeWorking.collect { time ->
                        time?.forEach { timeModel ->
                            timeModel.timeWorking?.let { tml ->
                                timeWorking.add(tml)
                            }
                        }
                        time?.let { tm ->
                            if(tm.size > 0) initTimeWorking()
                        }
                    }
                }
            }
        }

        binding.headerInforDoctor.setTitle(getString(R.string.infor_doctor))

        val str = "Bác Sĩ Ơi - Phòng Khám 020"
        val content = SpannableString(str).apply {
            setSpan(UnderlineSpan(), 0, str.length, 0)
            setSpan(StyleSpan(Typeface.BOLD), 0, str.length, 0)
        }
        binding.txtClinic.text = content

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
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setChangeTime(hours: ArrayList<HourModel>) {
        hourAdapter = HourWorkingAdapter(this@InfoDoctorActivity, INFORMATION_DOCTOR).apply {
            items = hours
            binding.rcvHourWorking.adapter = this
            onClickTime = {
                val intent = Intent(this@InfoDoctorActivity, MakeAppointActivity::class.java).apply {
                    putExtra(INFORMATION_DOCTOR, doctor)
                    putExtra(HOUR_WORKING_DOCTOR, it)
                    putExtra(TIME_WORKING_DOCTOR, date)
                }
                startActivity(intent)
            }
        }
    }

    private fun enableOnlineConsult() {
        binding.onlineConsult.apply {
            visibility = View.VISIBLE
            isEnabled = true
        }
    }

    private fun disableOnlineConsult() {
        binding.onlineConsult.apply {
            visibility = View.INVISIBLE
            isEnabled = false
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityInfoDoctorBinding.inflate(inflater)
}