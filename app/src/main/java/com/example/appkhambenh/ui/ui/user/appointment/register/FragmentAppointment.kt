package com.example.appkhambenh.ui.ui.user.appointment.register

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentAppointmentBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.model.RegisterChecking
import com.example.appkhambenh.ui.model.Service
import com.example.appkhambenh.ui.ui.user.LoginWithUser
import com.example.appkhambenh.ui.ui.user.appointment.register.adapter.ServiceAdapter
import com.example.appkhambenh.ui.ui.user.avatar.SeeAvatarActivity
import com.example.appkhambenh.ui.ui.user.navigation.information.CustomTextViewInfo
import com.example.appkhambenh.ui.utils.*
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class FragmentAppointment : BaseFragment<FragmentAppointmentViewModel, FragmentAppointmentBinding>() {
    lateinit var popUpView: View
    var popupWindow = PopupWindow()
    var isUpdate = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    @SuppressLint("CutPasteId")
    private fun initUi() {
        val registerChecking: RegisterChecking? = arguments?.getSerializable(PreferenceKey.REGISTER_CHECKING) as RegisterChecking?
        if(registerChecking != null){
            isUpdate = true
            binding.txtSelectService.hint = registerChecking.service
            binding.txtSelectDepartment.hint = registerChecking.department
            binding.txtSelectDoctor.hint = registerChecking.doctor
            binding.edtReasons.hint = registerChecking.reasons
            binding.txtRegister.text = "Lưu thay đổi lịch hẹn"
            binding.txtTitle.text = "Chỉnh sửa lịch hẹn"
            binding.date.text = registerChecking.date
            binding.hour.text = registerChecking.hour
        }else{
            binding.date.text = viewModel.mPreferenceUtil.defaultPref()
                .getString(PreferenceKey.DATE_APPOINTMENT, "").toString()

            binding.hour.text = viewModel.mPreferenceUtil.defaultPref()
                .getString(PreferenceKey.HOUR_APPOINTMENT, "").toString()
        }

        val avatar = viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.USER_AVATAR, "").toString()
        if(avatar.isNotEmpty()) {
            Picasso.get().load(avatar)
                .placeholder(R.drawable.user_ad)
                .error(R.drawable.user_ad)
                .into(binding.avatarAppointment)
        }

        binding.avatarAppointment.setOnClickListener {
            val intent = Intent(requireActivity(), SeeAvatarActivity::class.java)
            startActivity(intent)
        }

        binding.txtUseNameAppointment.text =
            viewModel.mPreferenceUtil.defaultPref().getString(PreferenceKey.USER_NAME,"")

        binding.backAppointment.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

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
            val id_user = viewModel.mPreferenceUtil.defaultPref()
                .getInt(PreferenceKey.USER_ID, -1).toString()

            val loadData = ProgressDialog(requireContext())
            loadData.setTitle("Thông báo")
            loadData.setMessage("Please wait...")

            if(!isUpdate){
                if(strService.isEmpty() || strDepartment.isEmpty() || strDoctor.isEmpty() || strReasons.isEmpty()){
                    Toast.makeText(requireActivity(),"Bạn chưa nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show()
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
                            convertToRequestBody(id_user)
                        )

                        val id_day = viewModel.mPreferenceUtil.defaultPref().getInt(PreferenceKey.ID_DAY, 0)
                        val status = 1
                        viewModel.changeStatusWorkingTime(
                            convertToRequestBody(id_day.toString()),
                            convertToRequestBody(binding.hour.text.toString()),
                            convertToRequestBody(status.toString())
                        )

                        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
                            if(it){
                                loadData.show()
                            }else {
                                loadData.dismiss()
                            }
                        }

                        viewModel.isSuccessfulLiveData.observe(viewLifecycleOwner) {
                            if(it) {
                                val intent = Intent(requireActivity(), LoginWithUser::class.java)
                                startActivity(intent)
                                activity?.finish()
                                Toast.makeText(requireActivity() ,"Bạn đã đăng kí lịch khám thành công" ,Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    alertDialog.setNegativeButton("Không") { _, _ -> }
                    alertDialog.show()

                }
            }else{
                if(strService.isEmpty() && strDepartment.isEmpty() && strDoctor.isEmpty() && strReasons.isEmpty()){
                    Toast.makeText(requireActivity(),"Bạn chưa thay đổi thông tin lịch hẹn",Toast.LENGTH_SHORT).show()
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
                        convertToRequestBody(id_user)
                    )

                    viewModel.loadingEditLiveData.observe(viewLifecycleOwner){ isLoading ->
                        if(isLoading) loadData.show() else loadData.dismiss()
                    }

                    viewModel.editAppointSuccessfulLiveData.observe(viewLifecycleOwner){ isSuccess ->
                        if(isSuccess){
                            activity?.supportFragmentManager?.popBackStack()
                            show("Bạn đã thay đổi lịch hẹn thành công")
                        }
                    }
                }
            }
        }
    }

    private fun getValueUpdateAppoint(strText: String, hint: String): String {
        return strText.let {
            it.ifEmpty {
                hint
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

    override fun onFragmentBack(): Boolean {
        return false
    }
}