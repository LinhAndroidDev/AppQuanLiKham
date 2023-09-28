package com.example.appkhambenh.ui.ui.user.appointment.time

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentTimeWorkingBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.user.appointment.register.FragmentAppointment
import com.example.appkhambenh.ui.ui.user.appointment.time.adapter.WorkingTimeAdapter
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.google.firebase.database.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class FragmentTimeWorking : BaseFragment<TimeWorkingViewModel, FragmentTimeWorkingBinding>() {
    lateinit var workingTimeAdapter: WorkingTimeAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    var formatDay = SimpleDateFormat("EEEE", Locale("vi", "VN"))
    var formatDayOfMonth = SimpleDateFormat("dd", Locale("vi", "VN"))
    var formatMonth = SimpleDateFormat("MM", Locale("vi", "VN"))
    var COUNT_CHANGE_DATE = 0

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar: Calendar = Calendar.getInstance()
        binding.txtTimeWorking.text = formatDay.format(calendar.time) +
                "," + formatDayOfMonth.format(calendar.time) +
                " tháng " + formatMonth.format(calendar.time)
        binding.imgBackTime.alpha = 0.3f
        binding.imgBackTime.isEnabled = false

        val date = binding.txtTimeWorking.text.toString()
        val requestBodyDay: RequestBody = date.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        viewModel.getListWorkingTime(requestBodyDay)

        getData()

        initUi()
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initUi() {

        binding.backTimeWorking.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.imgNextTime.setOnClickListener {
            binding.imgBackTime.alpha = 1f
            binding.imgBackTime.isEnabled = true
            val calendar: Calendar = Calendar.getInstance()
            COUNT_CHANGE_DATE++
            calendar.add(Calendar.DAY_OF_YEAR, COUNT_CHANGE_DATE)
            binding.txtTimeWorking.text = formatDay.format(calendar.time) +
                    "," + formatDayOfMonth.format(calendar.time) +
                    " tháng " + formatMonth.format(calendar.time)
            resetDataDate()
        }

        binding.imgBackTime.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            COUNT_CHANGE_DATE--
            calendar.add(Calendar.DAY_OF_YEAR, COUNT_CHANGE_DATE)
            binding.txtTimeWorking.text = formatDay.format(calendar.time) +
                    "," + formatDayOfMonth.format(calendar.time) +
                    " tháng " + formatMonth.format(calendar.time)
            checkVisibleBackDate()
            resetDataDate()
        }
    }

    private fun getData() {
        val date = binding.txtTimeWorking.text.toString()
        viewModel.workingDateLiveData.observe(viewLifecycleOwner) { workingDate ->
            if(workingDate.time != null) {
                binding.rcvTimeWorking.visibility = View.VISIBLE
                binding.notificationEmptyData.visibility = View.GONE
                workingTimeAdapter = WorkingTimeAdapter(requireActivity(), workingDate.time)
                workingTimeAdapter.notifyDataSetChanged()
                val grid = GridLayoutManager(requireActivity(), 3)
                binding.rcvTimeWorking.layoutManager = grid
                binding.rcvTimeWorking.adapter = workingTimeAdapter

                workingTimeAdapter.onClickSelectAppointment = {
                    val fragmentAppointment = FragmentAppointment()
                    val fm = activity?.supportFragmentManager?.beginTransaction()
                    fm?.replace(R.id.changeIdAppointment, fragmentAppointment)
                        ?.addToBackStack(null)?.commit()
                    viewModel.mPreferenceUtil.defaultPref()
                        .edit().putString(PreferenceKey.DATE_APPOINTMENT, date)
                        .apply()
                    viewModel.mPreferenceUtil.defaultPref()
                        .edit().putString(PreferenceKey.HOUR_APPOINTMENT, it.hour)
                        .apply()
                }
            }else {
                binding.rcvTimeWorking.visibility = View.GONE
                binding.notificationEmptyData.visibility = View.VISIBLE
            }
        }
    }

    private fun resetDataDate() {
        val date = binding.txtTimeWorking.text.toString()
        val requestBodyDate: RequestBody = date
            .toRequestBody("multipart/form-data".toMediaTypeOrNull())
        viewModel.getListWorkingTime(requestBodyDate)

        getData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkVisibleBackDate() {
        val calendarCurrent: Calendar = Calendar.getInstance()
        val dateCurrent = formatDay.format(calendarCurrent.time) +
                "," + formatDayOfMonth.format(calendarCurrent.time) +
                " tháng " + formatMonth.format(calendarCurrent.time)
        if (dateCurrent == binding.txtTimeWorking.text.toString()) {
            binding.imgBackTime.alpha = 0.3f
            binding.imgBackTime.isEnabled = false
        } else {
            binding.imgBackTime.alpha = 1f
            binding.imgBackTime.isEnabled = true
        }
    }

    override fun onFragmentBack(): Boolean {
        return true
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentTimeWorkingBinding.inflate(inflater)
}