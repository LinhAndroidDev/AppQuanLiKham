package com.example.appkhambenh.ui.ui.doctor.department

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
import com.example.appkhambenh.databinding.ActivityEditDepartmentClinicBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.model.DepartmentClinic
import com.example.appkhambenh.ui.ui.doctor.department.adapter.EditDepartmentAdapter
import com.example.appkhambenh.ui.utils.showDialogEditDepartment
import com.google.firebase.database.*
import kotlin.collections.ArrayList

class EditDepartmentClinicActivity :
    BaseActivity<DepartmentViewModel, ActivityEditDepartmentClinicBinding>() {
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databaseReference = FirebaseDatabase.getInstance().reference

        getData()

        initUi()
    }

    private fun initUi() {
        binding.addDepartment.setOnClickListener {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog_add_department)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val edtDepartment: EditText = dialog.findViewById(R.id.edtDialogDepartment)
            val txtUpdateDepartment: TextView = dialog.findViewById(R.id.txtDialogUpdateDepartment)

            txtUpdateDepartment.setOnClickListener {
                val strDepartment = edtDepartment.text.toString()
                if (strDepartment.isEmpty()) {
                    show("Bạn chưa nhập tên khoa")
                } else {
                    val time = Time()
                    time.setToNow()
                    val seconds = time.toMillis(false).toString()
                    val department =
                        DepartmentClinic(strDepartment, seconds)
                    databaseReference.child("Department")
                        .child(seconds)
                        .setValue(department)
                    getData()
                    show("Bạn đã thêm khoa $strDepartment")
                    dialog.dismiss()
                }
            }
            dialog.show()
        }

        binding.backEditDepartment.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getData() {
        databaseReference.child("Department")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listDepartment = ArrayList<DepartmentClinic>()
                    if(snapshot.children != null){
                        for(dataSnapshot in snapshot.children){
                            val department: DepartmentClinic? = dataSnapshot.getValue(DepartmentClinic::class.java)
                            listDepartment.add(department!!)
                        }
                        val departmentAdapter =
                            EditDepartmentAdapter(listDepartment, applicationContext)
                        departmentAdapter.openDialogEditDepartment = {
                            showDialogEditDepartment(it, this@EditDepartmentClinicActivity)
                        }
                        val linear =
                            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                        binding.rcvEditDepartment.layoutManager = linear
                        binding.rcvEditDepartment.adapter = departmentAdapter
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    show(error.toString())
                }
            })
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityEditDepartmentClinicBinding.inflate(inflater)
}