package com.example.appkhambenh.ui.ui.user.appointment

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityOnlineConsultationBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.model.Hour
import com.example.appkhambenh.ui.model.Time
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.doctor.adapter.HourWorkingAdapter
import com.example.appkhambenh.ui.ui.user.doctor.adapter.TimeWorkingAdapter
import com.example.appkhambenh.ui.utils.ConvertUtils.dpToPx
import com.example.appkhambenh.ui.utils.setBgViewTint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class OnlineConsultationActivity :
    BaseActivity<EmptyViewModel, ActivityOnlineConsultationBinding>() {
    private val times by lazy { arrayListOf<Time>() }
    private val timeAdapter by lazy { TimeWorkingAdapter(this@OnlineConsultationActivity, times) }
    private var isEditTime = false
    private var formatDay = SimpleDateFormat("EE", Locale("vi", "VN"))
    private var formatDayOfMonth = SimpleDateFormat("dd/MM", Locale("vi", "VN"))

    companion object {
        const val ONLINE_CONSULT = "ONLINE_CONSULT"
        const val HOUR_ONLINE_CONSULT = "HOUR_ONLINE_CONSULT"
        const val TIME_EDIT = "TIME_EDIT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    private fun initUi() {

        intent.getIntExtra(MakeAppointActivity.EDIT_TIME, 0).let {
            if(it > 0) {
                isEditTime = true
            }
        }

        binding.header.setTitle(getString(R.string.select_time_checking))
        binding.footView.tvComplete.let {
            it.text = getString(R.string.confirm)
            val param = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            param.setMargins(50.dpToPx(this), 15.dpToPx(this), 50.dpToPx(this), 15.dpToPx(this))
            it.layoutParams = param
        }
        disableFootView()

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
                LinearLayoutManager(
                    this@OnlineConsultationActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            adapter = timeAdapter
        }

        timeAdapter.onClickTime = {
            setChangeTime(times[it].hours?.hour!!)
            disableFootView()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setChangeTime(hours: ArrayList<String>) {
        val hourAdapter = HourWorkingAdapter(this@OnlineConsultationActivity, hours, ONLINE_CONSULT)
        binding.rcvHourWorking.apply {
            adapter = hourAdapter
        }

        hourAdapter.onClickTime = { hour ->
            enableFootView()

            binding.footView.tvComplete.setOnClickListener {
                val intent = Intent(this@OnlineConsultationActivity, MakeAppointActivity::class.java)
                if(!isEditTime) {
                    intent.putExtra(HOUR_ONLINE_CONSULT, hour)
                    startActivity(intent)
                } else {
                    val resultIntent = Intent()
                    resultIntent.putExtra(TIME_EDIT, hour)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }
        }

    }

    private fun enableFootView() {
        binding.footView.tvComplete.let {
            setBgViewTint(it, getColor(R.color.orange))
            it.setTextColor(getColor(R.color.white))
            it.alpha = 1f
            it.isEnabled = true
            it.elevation = resources.getDimension(R.dimen.dimen_10)
        }
    }

    private fun disableFootView() {
        binding.footView.tvComplete.let {
            it.backgroundTintList =
                ColorStateList.valueOf(getColor(R.color.brown))
            it.setTextColor(getColor(R.color.grey_1))
            it.alpha = 0.5f
            it.isEnabled = false
            it.elevation = 0f
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityOnlineConsultationBinding.inflate(layoutInflater)
}