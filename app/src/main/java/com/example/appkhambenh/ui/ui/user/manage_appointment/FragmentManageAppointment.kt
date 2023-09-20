package com.example.appkhambenh.ui.ui.user.manage_appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentManageAppointmentBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.model.RegisterChecking
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.manage_appointment.adapter.ManageAppointmentAdapter
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class FragmentManageAppointment : BaseFragment<EmptyViewModel, FragmentManageAppointmentBinding>() {

    lateinit var databaseReference: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseReference = FirebaseDatabase.getInstance().reference

        initUi()
    }

    private fun initUi() {
        databaseReference.child("Register_Checking")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val listRegisterChecking: ArrayList<RegisterChecking> = arrayListOf()


                    if(snapshot.children != null){
                        for(dataSnapshot in snapshot.children){
                            val registerChecking: RegisterChecking? = dataSnapshot.getValue(RegisterChecking::class.java)
                            listRegisterChecking.add(registerChecking!!)
                        }
                    }

                    if (listRegisterChecking != null) {
                        val adapterManageAppointment =
                            ManageAppointmentAdapter(listRegisterChecking, requireActivity())
                        val linear = LinearLayoutManager(
                            requireActivity(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        binding.rcvManageAppointment.layoutManager = linear
                        binding.rcvManageAppointment.adapter = adapterManageAppointment
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        requireActivity(),
                        "Vui lòng kiểm tra đường truyền",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })

        binding.backManageAppoint.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentManageAppointmentBinding.inflate(inflater)

    override fun onFragmentBack(): Boolean {
        return true
    }
}