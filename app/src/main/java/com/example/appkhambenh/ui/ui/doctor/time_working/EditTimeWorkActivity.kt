package com.example.appkhambenh.ui.ui.doctor.time_working

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.text.format.Time
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
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.doctor.time_working.adapter.EditTimeAdapter
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.*
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class EditTimeWorkActivity : BaseActivity<EmptyViewModel, ActivityEditTimeWorkBinding>() {
    lateinit var databaseReference: DatabaseReference
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

        databaseReference = FirebaseDatabase.getInstance().reference

        val calendar: Calendar = Calendar.getInstance()
        binding.txtTimeEdit.text = formatDay.format(calendar.time) +
                "," + formatDayOfMonth.format(calendar.time) +
                " tháng " + formatMonth.format(calendar.time)
        setText()
        binding.backDateEdit.visibility = View.GONE

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
            binding.backDateEdit.visibility = View.VISIBLE
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
            binding.backDateEdit.visibility = View.GONE
        } else {
            binding.backDateEdit.visibility = View.VISIBLE
        }
    }

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
            val timeCurrent = Time()
            timeCurrent.setToNow()
            val seconds = timeCurrent.toMillis(false).toString()
            val time = TimeWorking("$strHour:$strMinute", seconds)
            val date = binding.txtTimeEdit.text.toString()
            if(type == 1){
                databaseReference.child("TimeWorking")
                    .child(date)
                    .child("time")
                    .child(seconds)
                    .setValue(time)
                databaseReference.child("TimeWorking")
                    .child(date)
                    .child("date").setValue(date)
            }else if(type == 2){
                val hashMap = HashMap<String, Any>()
                hashMap["hour"] = time
                hashMap["time"] = dmy
                databaseReference.child("TimeWorking")
                    .child(date)
                    .child("time")
                    .child(dmy)
                    .updateChildren(hashMap)
            }

            getData()

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun getData() {
        val date = binding.txtTimeEdit.text.toString()
        val listHour: ArrayList<TimeWorking> = arrayListOf()
        databaseReference.child("TimeWorking")
            .child(date)
            .child("time").addChildEventListener(object : ChildEventListener {
                @SuppressLint("ClickableViewAccessibility")
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val hr = snapshot.getValue(TimeWorking::class.java)
                    listHour.add(hr!!)
                    if (listHour.isEmpty()) {
                        binding.rcvEditTime.visibility = View.GONE
                    } else {
                        binding.rcvEditTime.visibility = View.VISIBLE
                    }

                    val listPositionHour: kotlin.collections.ArrayList<Int> = arrayListOf()
                    for(i in 0 until listHour.size){
                        listPositionHour.add(0)
                    }
                    saveListPositionHour(listPositionHour,PreferenceKey.LIST_POSITION_HOUR)

                    editTimeAdapter = EditTimeAdapter(listHour, this@EditTimeWorkActivity)
                    editTimeAdapter.notifyDataSetChanged()
                    editTimeAdapter.onSelectDelete = {
                        if(it){
                            binding.layoutBottomDeleteHour.layoutDelete.setOnTouchListener { _, _ -> true }

                            bottomShareBehavior = BottomSheetBehavior.from(binding.layoutBottomDeleteHour.layoutDelete)
                            bottomShareBehavior.state = BottomSheetBehavior.STATE_EXPANDED

                            binding.layoutBottomDeleteHour.txtCancelDeleteHour.setOnClickListener {
                                cancelDeleteHour(listHour)
                            }

                            binding.layoutBottomDeleteHour.txtDeleteHour.setOnClickListener {
                                for(i in 0 until listHour.size){
                                    if(listHour[i].isSelectDelete == true){
                                        val databaseReference = FirebaseDatabase.getInstance().reference
                                        databaseReference.child("TimeWorking")
                                            .child(binding.txtTimeEdit.text.toString())
                                            .child("time")
                                            .child(listHour[i].time.toString()).removeValue()
                                        editTimeAdapter.notifyDataSetChanged()
                                    }
                                }
                                cancelDeleteHour(listHour)
                            }
                        }
                    }
                    editTimeAdapter.onClickEditHourWorking = {
                        POSITION_ITEM = it
                        val day =listHour[it].time.toString()
                        showDialogAddHour("Chỉnh sửa giờ làm việc",2, day)
                    }
                    val grid = GridLayoutManager(applicationContext, 3)
                    binding.rcvEditTime.layoutManager = grid
                    binding.rcvEditTime.adapter = editTimeAdapter
                }

                override fun onChildChanged(
                    snapshot: DataSnapshot,
                    previousChildName: String?,
                ) {}

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    for(i in 0 until listHour.size){
                        if(listHour[i].isSelectDelete == true){
                            listHour.remove(listHour[i])
                            break
                        }
                    }
                    editTimeAdapter.notifyDataSetChanged()
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onCancelled(error: DatabaseError) {}

            })
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
                val date = binding.txtTimeEdit.text.toString()
                databaseReference.child("TimeWorking")
                    .child(date)
                    .child("date").setValue(date)
                resetDataDate()
                checkVisibleBackDate()
            }
        }

        dialog.show()
    }

    private fun resetDataDate() {
        val listHour = null
        val editTimeAdapter = EditTimeAdapter(listHour, this@EditTimeWorkActivity)
        editTimeAdapter.notifyDataSetChanged()
        val grid = GridLayoutManager(applicationContext, 3)
        binding.rcvEditTime.layoutManager = grid
        binding.rcvEditTime.adapter = editTimeAdapter

        getData()
    }

    private fun cancelDeleteHour(list: ArrayList<TimeWorking>) {
        bottomShareBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        editTimeAdapter.notifyDataSetChanged()
        val listPositionHour: kotlin.collections.ArrayList<Int> = arrayListOf()
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

    private fun setText(){
        val berkshire: Typeface? = ResourcesCompat.getFont(this, R.font.svn_berkshire_swash)
        binding.txtTimeEdit.typeface = berkshire
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityEditTimeWorkBinding.inflate(inflater)
}