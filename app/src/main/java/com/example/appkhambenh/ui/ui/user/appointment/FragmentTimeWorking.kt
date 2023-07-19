package com.example.appkhambenh.ui.ui.user.appointment

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentTimeWorkingBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.model.Time
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.appointment.adapter.WorkingTimeAdapter
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class FragmentTimeWorking : BaseFragment<EmptyViewModel, FragmentTimeWorkingBinding>() {
    lateinit var databaseReference: DatabaseReference
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

        databaseReference = FirebaseDatabase.getInstance().reference

        val calendar: Calendar = Calendar.getInstance()
        binding.txtTimeWorking.text = formatDay.format(calendar.time) +
                "," + formatDayOfMonth.format(calendar.time) +
                " tháng " + formatMonth.format(calendar.time)
        setText()
        binding.imgBackTime.visibility = View.GONE

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
            binding.imgBackTime.visibility = View.VISIBLE
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
        val listHour: ArrayList<Time> = arrayListOf()
        databaseReference.child("TimeWorking")
            .child(date)
            .child("time").addChildEventListener(object : ChildEventListener {
                @SuppressLint("ClickableViewAccessibility")
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val hr = snapshot.getValue(Time::class.java)
                    listHour.add(hr!!)
                    if (listHour.isEmpty()) {
                        binding.rcvTimeWorking.visibility = View.GONE
                    } else {
                        binding.rcvTimeWorking.visibility = View.VISIBLE
                    }

                    workingTimeAdapter = WorkingTimeAdapter(requireActivity(),listHour)
                    workingTimeAdapter.onClickSelectAppointment = {
                        val fragmentAppointment = FragmentAppointment()
                        val fm = activity?.supportFragmentManager?.beginTransaction()
                        fm?.replace(R.id.changeIdAppointment, fragmentAppointment)
                            ?.addToBackStack(null)?.commit()
                        viewModel.mPreferenceUtil.defaultPref()
                            .edit().putString(PreferenceKey.DATE_APPOINTMENT, date)
                            .apply()
                        viewModel.mPreferenceUtil.defaultPref()
                            .edit().putString(PreferenceKey.HOUR_APPOINTMENT, it)
                            .apply()
                    }
                    workingTimeAdapter.notifyDataSetChanged()
                    val grid = GridLayoutManager(requireActivity(), 3)
                    binding.rcvTimeWorking.layoutManager = grid
                    binding.rcvTimeWorking.adapter = workingTimeAdapter
                }

                override fun onChildChanged(
                    snapshot: DataSnapshot,
                    previousChildName: String?,
                ) {}

                override fun onChildRemoved(snapshot: DataSnapshot) {}

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onCancelled(error: DatabaseError) {}

            })
    }

    private fun resetDataDate() {
        val listHour = null
        workingTimeAdapter = WorkingTimeAdapter(requireActivity(),listHour)
        workingTimeAdapter.notifyDataSetChanged()
        val grid = GridLayoutManager(requireActivity(), 3)
        binding.rcvTimeWorking.layoutManager = grid
        binding.rcvTimeWorking.adapter = workingTimeAdapter

        getData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkVisibleBackDate() {
        val calendarCurrent: Calendar = Calendar.getInstance()
        val dateCurrent = formatDay.format(calendarCurrent.time) +
                "," + formatDayOfMonth.format(calendarCurrent.time) +
                " tháng " + formatMonth.format(calendarCurrent.time)
        if (dateCurrent == binding.txtTimeWorking.text.toString()) {
            binding.imgBackTime.visibility = View.GONE
        } else {
            binding.imgBackTime.visibility = View.VISIBLE
        }
    }

    private fun setText() {
        val berkshire: Typeface? = ResourcesCompat.getFont(requireActivity(), R.font.svn_berkshire_swash)
        binding.txtTimeWorking.typeface = berkshire
    }

    override fun onFragmentBack(): Boolean {
        return true
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentTimeWorkingBinding.inflate(inflater)
}