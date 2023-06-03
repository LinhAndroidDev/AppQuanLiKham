package com.example.appkhambenh.ui.ui.register

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentRegisterBinding
import com.example.appkhambenh.ui.base.BaseFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class FragmentRegister : BaseFragment<RegisterViewModel, FragmentRegisterBinding>() {

    private var formatDate = SimpleDateFormat("dd/MM/yyyy", Locale.UK)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    override fun bindData() {
        super.bindData()

        val loadData = ProgressDialog(requireActivity())
        loadData.setTitle("Thông báo")
        loadData.setMessage("Please wait...")

        viewModel.registerSuccessful.observe(viewLifecycleOwner){ isSuccessful->
            if(isSuccessful){
                activity?.onBackPressed()
            }
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner){ isLoading->
            if(isLoading){
                loadData.show()
            }else{
                loadData.dismiss()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initUi(){
        binding.backRegister.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.cancel.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.calendar.setOnClickListener {
            val getDate = Calendar.getInstance()
            val datePicker = DatePickerDialog(requireActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                    val selectDate: Calendar = Calendar.getInstance()
                    selectDate.set(Calendar.YEAR, year)
                    selectDate.set(Calendar.MONTH, month)
                    selectDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    binding.edtBirth.setText(formatDate.format(selectDate.time))

                }, getDate.get(Calendar.YEAR), getDate.get((Calendar.MONTH)), getDate.get(Calendar.DAY_OF_MONTH))

            datePicker.show()
        }

        binding.register.setOnClickListener {

            val name: String = binding.edtName.text.toString()
            val email: String = binding.edtEmail.text.toString()
            val password: String = binding.edtPassword.text.toString()
            val passwordRepeat: String = binding.edtRepeatPassword.text.toString()
            val birth: String = binding.edtBirth.text.toString()
            val address: String = binding.edtAddress.text.toString()
            val phone: String = binding.edtPhone.text.toString()

            val validateEmail: com.example.appkhambenh.ui.utils.Email = com.example.appkhambenh.ui.utils.Email(email, password)

            if(name.isEmpty() || email.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty() || birth.isEmpty() || address.isEmpty()){
                setNotification(R.color.txt_green,R.string.txt_enter_enough_info)
            }else if(!validateEmail.isValidEmail()){
                setNotification(R.color.txt_red, R.string.txt_fail_email)
            }else if(!validateEmail.isPassword()){
                setNotification(R.color.txt_red, R.string.txt_fail_password)
            }else if(password != passwordRepeat){
                setNotification(R.color.txt_red, R.string.txt_enter_password_again)
            }else{
                val requestBodyEmail: RequestBody =
                    email.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val requestBodyPassword: RequestBody =
                    password.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val requestBodyName: RequestBody =
                    name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val requestBodyBirth: RequestBody =
                    birth.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val requestBodyAddress: RequestBody =
                    address.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val requestBodyPhone: RequestBody =
                    phone.toRequestBody("multipart/form-data".toMediaTypeOrNull())

                viewModel.requestRegisterUser(
                    requestBodyEmail,
                    requestBodyPassword,
                    requestBodyName,
                    requestBodyBirth,
                    requestBodyPhone,
                    requestBodyAddress
                )
            }
        }
    }

    private fun setNotification(color: Int, string: Int){
        val shake: Animation = AnimationUtils.loadAnimation(requireActivity(),R.anim.anim_shake)
        binding.notificationRegister.text = resources.getString(string)
        binding.notificationRegister.setTextColor(resources.getColor(color))
        binding.notificationRegister.visibility = View.VISIBLE
        binding.notificationRegister.startAnimation(shake)
    }

    override fun onFragmentBack(): Boolean {
        return false
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentRegisterBinding.inflate(inflater, container, false)
}