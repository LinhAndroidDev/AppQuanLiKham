package com.example.appkhambenh.ui.ui.user.appointment.register

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentAppointmentBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.model.RegisterChecking
import com.example.appkhambenh.ui.model.Service
import com.example.appkhambenh.ui.ui.user.HomeActivity
import com.example.appkhambenh.ui.ui.user.appointment.register.adapter.ServiceAdapter
import com.example.appkhambenh.ui.ui.user.avatar.SeeAvatarActivity
import com.example.appkhambenh.ui.ui.user.manage_appointment.FragmentManageAppointment
import com.example.appkhambenh.ui.utils.*
import com.google.firebase.database.*

@Suppress("DEPRECATION")
class FragmentAppointment : BaseFragment<FragmentAppointmentViewModel, FragmentAppointmentBinding>() {
    private val popUpView: View by lazy { View.inflate(context, R.layout.popup_function_appointment, null) }
    private var popupWindow = PopupWindow()
    private var isUpdate = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    @SuppressLint("CutPasteId")
    private fun initUi() {
        val registerChecking: RegisterChecking? =
            arguments?.getSerializable(FragmentManageAppointment.REGISTER_CHECKING) as RegisterChecking?
        if (registerChecking != null) {
            isUpdate = true
            binding.txtSelectService.hint = registerChecking.service
            binding.txtSelectDepartment.hint = registerChecking.department
            binding.txtSelectDoctor.hint = registerChecking.doctor
            binding.edtReasons.hint = registerChecking.reasons
            binding.txtRegister.text = getString(R.string.save_appointment_changes)
            binding.headerAppoint.setTitle(getString(R.string.edit_appoint))
            binding.date.text = registerChecking.date
            binding.hour.text = registerChecking.hour
        }else{
            binding.headerAppoint.setTitle(getString(R.string.manage_appointment))
            binding.date.text = sharePrefer.getDateAppoint()
            binding.hour.text = sharePrefer.getHourAppoint()
        }

        sharePrefer.getUserAvatar().let {
            if(it.isNotEmpty()) {
                Glide.with(requireActivity()).load(it)
                    .error(R.drawable.user_ad)
                    .into(binding.avatarAppointment)
            }
        }

        binding.avatarAppointment.setOnClickListener {
            val intent = Intent(requireActivity(), SeeAvatarActivity::class.java)
            startActivity(intent)
        }

        binding.txtUseNameAppointment.text = sharePrefer.getUserName()

        binding.txtSelectService.setOnClickListener {
            showPopupView(binding.txtSelectService)

            val rcvService: RecyclerView = popUpView.findViewById(R.id.rcv_function_appointment)
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

            getDataDepartment(requireActivity(), rcvDepartment)
            getNameDepartment = {
                binding.txtSelectDepartment.text = it
                popupWindow.dismiss()
            }
        }

        binding.txtSelectDoctor.setOnClickListener {
            showPopupView(binding.txtSelectDoctor)
            val rcvDoctor: RecyclerView = popUpView.findViewById(R.id.rcv_function_appointment)

            getDataDoctor(requireActivity(), rcvDoctor)
            getNameDoctor = {
                binding.txtSelectDoctor.text = it
                popupWindow.dismiss()
            }
        }

        binding.registerChecking.setOnClickListener {
            val strService = binding.txtSelectService.text.toString()
            val strDepartment = binding.txtSelectDepartment.text.toString()
            val strDoctor = binding.txtSelectDoctor.text.toString()
            val strDate = binding.date.text.toString()
            val strHour = binding.hour.text.toString()
            val strReasons = binding.edtReasons.text.toString()

            if(!isUpdate){
                if(strService.isEmpty() || strDepartment.isEmpty() || strDoctor.isEmpty() || strReasons.isEmpty()){
                    show("Bạn chưa nhập đầy đủ thông tin")
                }else{
                    val alertDialog : AlertDialog.Builder = AlertDialog.Builder(requireActivity())
                    alertDialog.setTitle("Thông báo")
                    alertDialog.setIcon(R.mipmap.ic_launcher)
                    alertDialog.setMessage("Bạn có chắc chắn muốn đăng kí lịch khám?")
                    alertDialog.setPositiveButton("Có") { _, _ ->
                        viewModel.addAppoint(
                            convertToRequestBody(strService),
                            convertToRequestBody(strDepartment),
                            convertToRequestBody(strDoctor),
                            convertToRequestBody(strDate),
                            convertToRequestBody(strHour),
                            convertToRequestBody(strReasons),
                            convertToRequestBody(sharePrefer.getUserId().toString())
                        )

                        val status = 1
                        viewModel.changeStatusWorkingTime(
                            convertToRequestBody(sharePrefer.getIdDay().toString()),
                            convertToRequestBody(binding.hour.text.toString()),
                            convertToRequestBody(status.toString())
                        )

                        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
                            if(it) loading.show() else loading.dismiss()
                        }

                        viewModel.isSuccessfulLiveData.observe(viewLifecycleOwner) {
                            if(it) {
                                val intent = Intent(requireActivity(), HomeActivity::class.java)
                                startActivity(intent)
                                activity?.finish()
                                show("Bạn đã đăng kí lịch khám thành công")
                            }
                        }
                    }
                    alertDialog.setNegativeButton("Không") { _, _ -> }
                    alertDialog.show()

                }
            }else{
                if(strService.isEmpty() && strDepartment.isEmpty() && strDoctor.isEmpty() && strReasons.isEmpty()){
                    show("Bạn chưa thay đổi thông tin lịch hẹn")
                }else{
                    val service = getValueUpdateAppoint(
                        strService,
                        binding.txtSelectService.hint.toString()
                    )
                    val department = getValueUpdateAppoint(
                        strDepartment,
                        binding.txtSelectDepartment.hint.toString()
                    )
                    val doctor = getValueUpdateAppoint(
                        strDoctor,
                        binding.txtSelectDoctor.hint.toString()
                    )
                    val reasons = getValueUpdateAppoint(
                        strReasons,
                        binding.edtReasons.hint.toString()
                    )

                    viewModel.editAppoint(
                        convertToRequestBody(service),
                        convertToRequestBody(department),
                        convertToRequestBody(doctor),
                        convertToRequestBody(strDate),
                        convertToRequestBody(strHour),
                        convertToRequestBody(reasons),
                        convertToRequestBody(sharePrefer.getUserId().toString())
                    )

                    viewModel.loadingEditLiveData.observe(viewLifecycleOwner){ isLoading ->
                        if(isLoading) loading.show() else loading.dismiss()
                    }

                    viewModel.editAppointSuccessfulLiveData.observe(viewLifecycleOwner){ isSuccess ->
                        if(isSuccess){
                            activity?.supportFragmentManager?.popBackStack()
                            show(getString(R.string.change_appoint_successful))
                        }
                    }
                }
            }
        }
    }

    private fun getValueUpdateAppoint(strText: String, hint: String): String {
        return strText.let { it.ifEmpty { hint } }
    }

    private fun showPopupView(txtView: TextView){
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

    override fun onFragmentBack(): Boolean {
        return false
    }
}