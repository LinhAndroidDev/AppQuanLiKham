package com.example.appkhambenh.ui.ui.doctor.time_working

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
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
import com.example.appkhambenh.ui.ui.doctor.time_working.adapter.EditTimeAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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
            showDialogDate()
        }

        binding.addHour.setOnClickListener {
            showDialogHour("Thêm giờ làm việc",1, "", -1)
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
    @SuppressLint("SimpleDateFormat", "CheckResult")
    private fun showDialogHour(txtTitle: String, type: Int, strHourSelected: String, idDay: Int) {
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

        val listValueHour = arrayOf("00", "15", "30", "45")
        minute.minValue = 0
        minute.maxValue = listValueHour.size - 1
        minute.displayedValues = listValueHour
//        minute.setFormatter { String.format("%02d", it) }

        val str = arrayOf("AM")
        typeHour.minValue = 0
        typeHour.maxValue = str.size - 1
        typeHour.displayedValues = str

        var strHour = "00"
        var strMinute = "00"

        if(type == 2){
            strHourSelected.split(":").apply {
                hour.value = this[0].toInt()
                for(i in listValueHour.indices){
                    if(listValueHour[i] == this[1]){
                        minute.value = i
                    }
                }
                strHour = this[0]
                strMinute = this[1]
            }
        }

        minute.setOnValueChangedListener { numberPicker, i, i2 ->
            val number: Int = numberPicker.value
            strMinute = if (number == 0) "00" else "${number*15}"
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
            if(type == 1){
                viewModel.editWorkingTime(
                    convertToRequestBody(binding.txtTimeEdit.text.toString()),
                    convertToRequestBody("$strHour:$strMinute")
                )

                viewModel.isSuccessfulLiveData.observe(this, androidx.lifecycle.Observer {
                    if(it){
                        object : CountDownTimer(300, 300) {
                            override fun onTick(p0: Long) {

                            }

                            override fun onFinish() {
                                viewModel.getListWorkingTime(
                                    convertToRequestBody(binding.txtTimeEdit.text.toString())
                                )
                                getData()
                            }

                        }.start()
                    }
                })
                dialog.dismiss()
            }else if(type == 2){
                if(strHourSelected != "$strHour:$strMinute"){
                    viewModel.editWorkingTime(
                        convertToRequestBody(idDay.toString()),
                        convertToRequestBody(strHourSelected),
                        convertToRequestBody("$strHour:$strMinute")
                    )

                    object : CountDownTimer(300, 300){
                        override fun onTick(p0: Long) {

                        }

                        override fun onFinish() {
                            if(viewModel.editSuccessful){
                                show("Bạn đã cập nhật $strHourSelected thành ${"$strHour:$strMinute"}")
                                viewModel.getListWorkingTime(
                                    convertToRequestBody(binding.txtTimeEdit.text.toString())
                                )
                                editTimeAdapter.notifyDataSetChanged()
                            }
                        }

                    }.start()

                    dialog.dismiss()
                }else{
                    show("Bạn chưa thay đổi giờ")
                }
            }
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

                editTimeAdapter.onSelectDelete = { strHour ->
                    binding.layoutBottomDeleteHour.layoutDelete.setOnTouchListener { _, _ -> true }
                    bottomShareBehavior =
                        BottomSheetBehavior.from(binding.layoutBottomDeleteHour.layoutDelete)
                    bottomShareBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    binding.layoutBottomDeleteHour.txtCancelDeleteHour.setOnClickListener {
                        bottomShareBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    }

                    binding.layoutBottomDeleteHour.txtDeleteHour.setOnClickListener {
                        viewModel.deleteWorkingTime(
                            convertToRequestBody(workingDate.id_day.toString()),
                            convertToRequestBody(strHour)
                        )

                        object : CountDownTimer(300, 300){
                            override fun onTick(p0: Long) {

                            }

                            override fun onFinish() {
                                if(viewModel.deleteSuccessful){
                                    show("Bạn đã xoá $strHour thành công")
                                    viewModel.getListWorkingTime(
                                        convertToRequestBody(binding.txtTimeEdit.text.toString())
                                    )
                                    editTimeAdapter.notifyDataSetChanged()
                                }
                            }

                        }.start()

                        bottomShareBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                }
                editTimeAdapter.onClickEditHourWorking = {
                    POSITION_ITEM = it
                    val strHour = workingDate.time[it].hour.toString()
                    showDialogHour("Chỉnh sửa giờ làm việc", 2, strHour, workingDate.id_day!!)
                }
            } else {
                binding.rcvEditTime.visibility = View.GONE
                binding.notificationEmptyData.visibility = View.VISIBLE
            }
        }
        }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    private fun showDialogDate() {
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
                show("Bạn chưa nhập thời gian")
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
        viewModel.getListWorkingTime(convertToRequestBody(date))

        getData()
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityEditTimeWorkBinding.inflate(inflater)
}