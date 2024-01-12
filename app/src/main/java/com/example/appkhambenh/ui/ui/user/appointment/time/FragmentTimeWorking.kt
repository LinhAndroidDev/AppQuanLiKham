package com.example.appkhambenh.ui.ui.user.appointment.time

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentTimeWorkingBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.user.appointment.register.FragmentAppointment
import com.example.appkhambenh.ui.ui.user.appointment.time.adapter.WorkingTimeAdapter
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class FragmentTimeWorking : BaseFragment<TimeWorkingViewModel, FragmentTimeWorkingBinding>() {
    private lateinit var workingTimeAdapter: WorkingTimeAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    var formatDay = SimpleDateFormat("EEEE", Locale("vi", "VN"))
    private var formatDayOfMonth = SimpleDateFormat("dd", Locale("vi", "VN"))
    private var formatMonth = SimpleDateFormat("MM", Locale("vi", "VN"))
    private var COUNT_CHANGE_DATE = 0

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

        viewModel.getListWorkingTime(
            convertToRequestBody(binding.txtTimeWorking.text.toString()),
            convertToRequestBody("6")
        )

        getData()

        initUi()
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initUi() {

        binding.headerWorkingTime.setTitle(getString(R.string.select_time_checking))

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
                    val fm: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                    fm.hide(requireActivity().supportFragmentManager.findFragmentByTag("FragmentTimeWorking")!!)
                        .add(R.id.changeIdAppointment, fragmentAppointment)
                        .addToBackStack(null).commit()
                    sharePrefer.saveDateAppoint(date)
                    sharePrefer.saveHourAppoint(it.hour ?: "")
                    sharePrefer.saveIdDay(workingDate.id_day ?: -1)
                }
            }else {
                binding.rcvTimeWorking.visibility = View.GONE
                binding.notificationEmptyData.visibility = View.VISIBLE
            }
        }
    }

    private fun resetDataDate() {
        viewModel.getListWorkingTime(
            convertToRequestBody(binding.txtTimeWorking.text.toString()),
            convertToRequestBody("6")
        )

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