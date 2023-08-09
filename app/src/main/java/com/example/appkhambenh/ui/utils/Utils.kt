package com.example.appkhambenh.ui.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.model.DepartmentClinic
import com.example.appkhambenh.ui.model.Doctor
import com.example.appkhambenh.ui.ui.user.appointment.register.FragmentAppointment
import com.example.appkhambenh.ui.ui.user.appointment.register.adapter.DepartmentAdapter
import com.example.appkhambenh.ui.ui.user.appointment.register.adapter.DoctorAdapter
import com.google.firebase.database.*
import java.util.HashMap

var getNameDepartment: ((String)->Unit)? = null
var getNameDoctor: ((String)->Unit)? = null

fun showDialogEditDepartment(departmentClinic: DepartmentClinic, context: Context) {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(true)
    dialog.setContentView(R.layout.dialog_add_department)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.show()

    val edtDepartment: EditText = dialog.findViewById(R.id.edtDialogDepartment)
    val txtUpdateDepartment: TextView = dialog.findViewById(R.id.txtDialogUpdateDepartment)
    val txtTitleEditDepartment: TextView = dialog.findViewById(R.id.txtTitleEditDepartment)
    edtDepartment.setText(departmentClinic.nameDpt.toString())
    txtTitleEditDepartment.text = "Chỉnh Sửa Tên Khoa"

    txtUpdateDepartment.setOnClickListener {
        val strDepartment = edtDepartment.text.toString()
        if (strDepartment.isEmpty()) {
            Toast.makeText(context, "Bạn chưa nhập tên khoa", Toast.LENGTH_SHORT).show()
        } else {
            val database: DatabaseReference =
                FirebaseDatabase.getInstance().reference
            val hashMap = HashMap<String, Any>()
            hashMap["nameDpt"] = strDepartment
            database.child("Department").child(departmentClinic.time.toString())
                .updateChildren(hashMap as Map<String, Any>)
            Toast.makeText(context, "Bạn đã đổi tên thành $strDepartment", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
    }
}

fun getDataDepartment(
    context: Context,
    rcvDepartment: RecyclerView
) {
    val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
    databaseReference.child("Department")
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listDepartment = ArrayList<DepartmentClinic>()
                if(snapshot.children != null){
                    for(dataSnapshot in snapshot.children){
                        val department: DepartmentClinic? = dataSnapshot.getValue(
                            DepartmentClinic::class.java)
                        listDepartment.add(department!!)
                    }
                    val departmentAdapter = DepartmentAdapter(listDepartment, context)
                    departmentAdapter.onSelectDepartment = {
                        getNameDepartment?.invoke(it)
                    }
                    val linear =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    rcvDepartment.layoutManager = linear
                    rcvDepartment.adapter = departmentAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
}

fun getDataDoctor(
    context: Context,
    rcvDoctor: RecyclerView
) {
    val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
    databaseReference.child("Doctor")
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listDoctor = ArrayList<Doctor>()
                if(snapshot.children != null){
                    for(dataSnapshot in snapshot.children){
                        val doctor: Doctor? = dataSnapshot.getValue(
                            Doctor::class.java)
                        listDoctor.add(doctor!!)
                    }
                    val departmentAdapter = DoctorAdapter(listDoctor, context)
                    departmentAdapter.selectDoctor = {
                        getNameDoctor?.invoke(it)
                    }
                    val linear =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    rcvDoctor.layoutManager = linear
                    rcvDoctor.adapter = departmentAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
}