package com.example.appkhambenh.ui.ui.user.appointment.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentAppointmentBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.model.DepartmentClinic
import com.example.appkhambenh.ui.model.Service
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.doctor.department.adapter.EditDepartmentAdapter
import com.example.appkhambenh.ui.ui.user.appointment.register.adapter.ServiceAdapter
import com.example.appkhambenh.ui.ui.user.appointment.time.FragmentTimeWorking
import com.example.appkhambenh.ui.utils.*
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class FragmentAppointment : BaseFragment<EmptyViewModel, FragmentAppointmentBinding>() {
    lateinit var popUpView: View
    var popupWindow = PopupWindow()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    @SuppressLint("CutPasteId")
    private fun initUi() {
        val avatar = viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.USER_AVATAR, "").toString()
        Picasso.get().load(avatar)
            .placeholder(R.drawable.user_ad)
            .error(R.drawable.user_ad)
            .into(binding.avatarAppointment)

        val date = viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.DATE_APPOINTMENT, "").toString()
        binding.date.text = date

        val hour = viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.HOUR_APPOINTMENT, "").toString()
        binding.hour.text = hour

        binding.txtUseNameAppointment.text =
            viewModel.mPreferenceUtil.defaultPref().getString(PreferenceKey.USER_NAME,"")

        binding.backAppointment.setOnClickListener {
            val fragmentTimeWorking = FragmentTimeWorking()
            val fm = activity?.supportFragmentManager?.beginTransaction()
            fm?.replace(R.id.changeIdAppointment, fragmentTimeWorking)
                ?.commit()
        }

        binding.txtSelectService.setOnClickListener {
            showPopupView(binding.txtSelectService)

            val rcvService: RecyclerView = popUpView.findViewById(R.id.rcv_function_appointment)
            val linear = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rcvService.layoutManager = linear
            val listService: ArrayList<Service> = arrayListOf()
            listService.add(Service("Khám tự nguyện"))
            listService.add(Service("Khám theo định kì"))

            val serviceAdapter = ServiceAdapter(listService, requireActivity())
            serviceAdapter.onSelectFunction = {
                binding.txtSelectService.text = it
                popupWindow.dismiss()
            }
            rcvService.adapter = serviceAdapter
        }

        binding.txtSelectDepartment.setOnClickListener {
            showPopupView(binding.txtSelectDepartment)

            val rcvDepartment: RecyclerView = popUpView.findViewById(R.id.rcv_function_appointment)
            val linear = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rcvDepartment.layoutManager = linear

            getDataDepartment(requireActivity(), rcvDepartment)
            getNameDepartment = {
                binding.txtSelectDepartment.text = it
                popupWindow.dismiss()
            }
        }

        binding.txtSelectDoctor.setOnClickListener {
            showPopupView(binding.txtSelectDoctor)
            val rcvDoctor: RecyclerView = popUpView.findViewById(R.id.rcv_function_appointment)
            val linear = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rcvDoctor.layoutManager = linear

            getDataDoctor(requireActivity(), rcvDoctor)
            getNameDoctor = {
                binding.txtSelectDoctor.text = it
                popupWindow.dismiss()
            }
        }
    }

    private fun showPopupView(txtView: TextView){
        popUpView = View.inflate(context, R.layout.popup_function_appointment, null)
        val width = ViewGroup.LayoutParams.WRAP_CONTENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        val focusable = true
        popupWindow = PopupWindow(popUpView, width, height, focusable)
        popupWindow.showAsDropDown(txtView, 0, -80, Gravity.BOTTOM)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAppointmentBinding.inflate(inflater)
}