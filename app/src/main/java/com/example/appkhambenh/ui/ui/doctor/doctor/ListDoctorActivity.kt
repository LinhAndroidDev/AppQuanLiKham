package com.example.appkhambenh.ui.ui.doctor.doctor

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.format.Time
import android.view.LayoutInflater
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityListDoctorBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.model.Doctor
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.doctor.doctor.adapter.EditDoctorAdapter
import com.google.firebase.database.*
import java.util.*

class ListDoctorActivity : BaseActivity<EmptyViewModel, ActivityListDoctorBinding>() {

    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databaseReference = FirebaseDatabase.getInstance().reference

        getData()

        initUi()
    }

    @SuppressLint("SetTextI18n")
    private fun initUi() {
        binding.addDoctor.setOnClickListener {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog_add_department)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val edtDoctor: EditText = dialog.findViewById(R.id.edtDialogDepartment)
            val txtUpdateDoctor: TextView = dialog.findViewById(R.id.txtDialogUpdateDepartment)
            val title: TextView = dialog.findViewById(R.id.txtTitleEditDepartment)

            edtDoctor.hint = "Nhập tên Bác Sĩ"
            title.text = "Nhập Tên Bác Sĩ"

            txtUpdateDoctor.setOnClickListener {
                val strDoctor = edtDoctor.text.toString()
                if (strDoctor.isEmpty()) {
                    Toast.makeText(this, "Bạn chưa nhập tên Bác Sĩ", Toast.LENGTH_SHORT).show()
                } else {
                    val time = Time()
                    time.setToNow()
                    val seconds = time.toMillis(false).toString()
                    val doctor =
                        Doctor(strDoctor,null , seconds)
                    databaseReference.child("Doctor")
                        .child(seconds)
                        .setValue(doctor)
                    getData()
                    Toast.makeText(this@ListDoctorActivity, "Bạn đã thêm Bác sĩ $strDoctor", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }

            dialog.show()
        }

        binding.backListDoctor.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getData() {
        databaseReference.child("Doctor")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listDoctor = ArrayList<Doctor>()
                    if(snapshot.children != null){
                        for(dataSnapshot in snapshot.children){
                            val doctor: Doctor? = dataSnapshot.getValue(Doctor::class.java)
                            listDoctor.add(doctor!!)
                        }
                        val doctorAdapter =
                            EditDoctorAdapter(listDoctor, applicationContext)
//                        doctorAdapter.openDialogEditDepartment = {
//                            showDialogEditDepartment(it, this@ListDoctorActivity)
//                        }
                        val linear =
                            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                        binding.rcvListDoctor.layoutManager = linear
                        binding.rcvListDoctor.adapter = doctorAdapter
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityListDoctorBinding.inflate(inflater)
}