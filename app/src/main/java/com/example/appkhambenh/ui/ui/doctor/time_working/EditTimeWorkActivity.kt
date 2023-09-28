package com.example.appkhambenh.ui.ui.doctor.time_working

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityEditTimeWorkBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.model.TimeWorking
import com.example.appkhambenh.ui.ui.doctor.time_working.adapter.EditTimeAdapter
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.*
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class EditTimeWorkActivity : BaseActivity<EditTimeWorkingViewModel, ActivityEditTimeWorkBinding>() {
    lateinit var bottomShareBehavior: BottomSheetBehavior<View>
    lateinit var editTimeAdapter: EditTimeAdapter
    var POSITION_ITEM = -1

    @RequiresApi(Build.VERSION_CODES.O)
    var formatDay = SimpleDateFormat("EEEE", Locale("vi", "VN"))
    var formatDayOfMonth = SimpleDateFormat("dd", Locale("vi", "VN"))
    var formatMonth = SimpleDateFormat("MM", Locale("vi", "VN"))
    var COUNT_CHANGE_DATE = 0

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val calendar: Calendar = Calendar.getInstance()
        binding.txtTimeEdit.text = formatDay.format(calendar.time) +
                "," + formatDayOfMonth.format(calendar.time) +
                " tháng " + formatMonth.format(calendar.time)

        binding.backDateEdit.alpha = 0.3f
        binding.backDateEdit.isEnabled = false

        val date = binding.txtTimeEdit.text.toString()
        val requestBodyDate: RequestBody = date
            .toRequestBody("multipart/form-data".toMediaTypeOrNull())
        viewModel.getListWorkingTime(requestBodyDate)

        getData()

        initUi()
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initUi() {
        binding.addTime.setOnClickListener {
            showDialogAddTime()
        }

        binding.addHour.setOnClickListener {
            showDialogAddHour("Thêm giờ làm việc",1, "")
        }

        binding.nextDateEdit.setOnClickListener {
            binding.backDateEdit.alpha = 1f
            binding.backDateEdit.isEnabled = true
            val calendar: Calendar = Calendar.getInstance()
            COUNT_CHANGE_DATE++
            calendar.add(Calendar.DAY_OF_YEAR, COUNT_CHANGE_DATE)
            binding.txtTimeEdit.text = formatDay.format(calendar.time) +
                    "," + formatDayOfMonth.format(calendar.time) +
                    " tháng " + formatMonth.format(calendar.time)
            checkVisibleBackDate()
            resetDataDate()
        }

        binding.backDateEdit.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            COUNT_CHANGE_DATE--
            calendar.add(Calendar.DAY_OF_YEAR, COUNT_CHANGE_DATE)
            binding.txtTimeEdit.text = formatDay.format(calendar.time) +
                    "," + formatDayOfMonth.format(calendar.time) +
                    " tháng " + formatMonth.format(calendar.time)
            checkVisibleBackDate()
            resetDataDate()
        }

        binding.backEditTimeWorking.setOnClickListener {
            onBackPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkVisibleBackDate() {
        val calendarCurrent: Calendar = Calendar.getInstance()
        val dateCurrent = formatDay.format(calendarCurrent.time) +
                "," + formatDayOfMonth.format(calendarCurrent.time) +
                " tháng " + formatMonth.format(calendarCurrent.time)
        if (dateCurrent == binding.txtTimeEdit.text.toString()) {
            binding.backDateEdit.alpha = 0.3f
            binding.backDateEdit.isEnabled = false
        } else {
            binding.backDateEdit.alpha = 1f
            binding.backDateEdit.isEnabled = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    private fun showDialogAddHour(txtTitle: String,type: Int, dmy: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_add_hour)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val txtEditHour: TextView = dialog.findViewById(R.id.txtEditHourWorking)
        val hour: com.shawnlin.numberpicker.NumberPicker = dialog.findViewById(R.id.npHour)
        val minute: com.shawnlin.numberpicker.NumberPicker = dialog.findViewById(R.id.npMinute)
        val typeHour: com.shawnlin.numberpicker.NumberPicker = dialog.findViewById(R.id.npTypeHour)
        val selectHour: TextView = dialog.findViewById(R.id.txtSelectHour)

        txtEditHour.text = txtTitle

        val berkshire: Typeface? = ResourcesCompat.getFont(this, R.font.svn_berkshire_swash)
        hour.setSelectedTypeface(berkshire)
        minute.setSelectedTypeface(berkshire)
        typeHour.setSelectedTypeface(berkshire)

        hour.minValue = 0
        hour.maxValue = 23
        hour.setFormatter { String.format("%02d", it) }

        minute.minValue = 0
        minute.maxValue = 59
        minute.setFormatter { String.format("%02d", it) }

        val str = arrayOf("AM")
        typeHour.minValue = 0
        typeHour.maxValue = str.size - 1
        typeHour.displayedValues = str

        var strHour = "00"
        var strMinute = "00"

        minute.setOnValueChangedListener { numberPicker, i, i2 ->
            val number: Int = numberPicker.value
            strMinute = if (number < 10) {
                "0$number"
            } else {
                number.toString()
            }
        }

        hour.setOnValueChangedListener { numberPicker, i, i2 ->
            val number: Int = numberPicker.value
            strHour = if (number < 10) {
                "0$number"
            } else {
                number.toString()
            }
        }

        selectHour.setOnClickListener {
            val time = TimeWorking("$strHour:$strMinute", false)
            val date = binding.txtTimeEdit.text.toString()
            if(type == 1){
                val requestBodyDate: RequestBody = date
                    .toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val requestBodyTime: RequestBody = "$strHour:$strMinute"
                    .toRequestBody("multipart/form-data".toMediaTypeOrNull())
                viewModel.editWorkingTime(requestBodyDate, requestBodyTime)
                viewModel.getListWorkingTime(requestBodyDate)
                getData()
            }else if(type == 2){

            }
            dialog.dismiss()
        }

        dialog.show()
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getData() {
        viewModel.workingDateLiveData.observe(this) { workingDate ->
            if(workingDate.time != null) {
                binding.rcvEditTime.visibility = View.VISIBLE
                binding.notificationEmptyData.visibility = View.GONE
                editTimeAdapter = EditTimeAdapter(workingDate.time, this@EditTimeWorkActivity)
                editTimeAdapter.notifyDataSetChanged()
                val grid = GridLayoutManager(applicationContext, 3)
                binding.rcvEditTime.layoutManager = grid
                binding.rcvEditTime.adapter = editTimeAdapter

                editTimeAdapter.onSelectDelete = { isSelect ->
                    if (isSelect) {
                        binding.layoutBottomDeleteHour.layoutDelete.setOnTouchListener { _, _ -> true }

                        bottomShareBehavior =
                            BottomSheetBehavior.from(binding.layoutBottomDeleteHour.layoutDelete)
                        bottomShareBehavior.state = BottomSheetBehavior.STATE_EXPANDED

                        binding.layoutBottomDeleteHour.txtCancelDeleteHour.setOnClickListener {
                            cancelDeleteHour(workingDate.time)
                        }

                        binding.layoutBottomDeleteHour.txtDeleteHour.setOnClickListener {
                            for (i in 0 until workingDate.time.size) {
                                if (workingDate.time[i].isSelectDelete == true) {

                                    editTimeAdapter.notifyDataSetChanged()
                                }
                            }
                            cancelDeleteHour(workingDate.time)
                        }
                    }
                }
                editTimeAdapter.onClickEditHourWorking = {
                    POSITION_ITEM = it
                    val day = workingDate.time[it].hour.toString()
                    showDialogAddHour("Chỉnh sửa giờ làm việc", 2, day)
                }
            } else {
                binding.rcvEditTime.visibility = View.GONE
                binding.notificationEmptyData.visibility = View.VISIBLE
            }
        }
        }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    private fun showDialogAddTime() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_add_time)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val edtTime: TextView = dialog.findViewById(R.id.edtDialogTimeWorking)
        val txtUpdateTime: TextView = dialog.findViewById(R.id.txtDialogUpdateTimeWorking)

        edtTime.setOnClickListener {
            val getDate = Calendar.getInstance()
            val datePicker = DatePickerDialog(this,
                { _, year, monthOfYear, dayOfMonth ->

                    val calendar: Calendar = Calendar.getInstance()
                    calendar.set(year, monthOfYear, dayOfMonth)

                    COUNT_CHANGE_DATE =
                        TimeUnit.MILLISECONDS.toDays(calendar.timeInMillis- getDate.timeInMillis).toInt()

                    edtTime.text = formatDay.format(calendar.time) +
                            "," + formatDayOfMonth.format(calendar.time) +
                            " tháng " + formatMonth.format(calendar.time)
                },
                getDate.get(Calendar.YEAR),
                getDate.get((Calendar.MONTH)),
                getDate.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        txtUpdateTime.setOnClickListener {
            val time = edtTime.text.toString()
            if (time.isEmpty()) {
                Toast.makeText(this, "Bạn chưa nhập thời gian", Toast.LENGTH_SHORT).show()
            } else {
                dialog.dismiss()
                binding.txtTimeEdit.text = time
                resetDataDate()
                checkVisibleBackDate()
            }
        }

        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun resetDataDate() {
        val date = binding.txtTimeEdit.text.toString()
        val requestBodyDate: RequestBody = date
            .toRequestBody("multipart/form-data".toMediaTypeOrNull())
        viewModel.getListWorkingTime(requestBodyDate)

        getData()
    }

    private fun cancelDeleteHour(list: ArrayList<TimeWorking>) {
        bottomShareBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        editTimeAdapter.notifyDataSetChanged()
        val listPositionHour: ArrayList<Int> = arrayListOf()
        for(i in 0 until list.size){
            listPositionHour.add(1)
        }
        saveListPositionHour(listPositionHour,PreferenceKey.LIST_POSITION_HOUR)
    }

    private fun saveListPositionHour(list: ArrayList<Int>, key: String?) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityEditTimeWorkBinding.inflate(inflater)
}