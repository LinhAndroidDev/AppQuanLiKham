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
import com.example.appkhambenh.ui.utils.validateEmail
import com.example.appkhambenh.ui.utils.validatePassword
import com.example.appkhambenh.ui.utils.validatePhone
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

        binding.rbPatient.isChecked = true

        binding.backRegister.setOnClickListener { back() }

        binding.cancel.setOnClickListener { back() }

        binding.layoutDate.setOnClickListener {
            showDialogSelectDate()
        }

        binding.rbAdmin.setOnCheckedChangeListener { _, b ->
            if(b){
                resetView()
                binding.txtTitleName.text = "Cơ sở y tế"
                binding.edtName.hint = "Nhập cơ sở y tế"
                binding.layoutSex.visibility = View.GONE
                binding.layoutBirth.visibility = View.GONE
                binding.layoutSpecialist.visibility = View.GONE
            }
        }

        binding.rbDoctor.setOnCheckedChangeListener { _, b ->
            if(b){
                resetView()
                binding.txtTitleName.text = "Bác sĩ"
                binding.edtName.hint = "Nhập tên Bác Sĩ"
                binding.layoutSex.visibility = View.VISIBLE
                binding.layoutBirth.visibility = View.VISIBLE
                binding.layoutSpecialist.visibility = View.VISIBLE
            }
        }

        binding.rbPatient.setOnCheckedChangeListener { _, b ->
            if(b){
                resetView()
                binding.txtTitleName.text = resources.getString(R.string.title_name)
                binding.edtName.hint = resources.getString(R.string.enter_name)
                binding.layoutSex.visibility = View.VISIBLE
                binding.layoutBirth.visibility = View.VISIBLE
                binding.layoutSpecialist.visibility = View.GONE
            }
        }

        binding.register.setOnClickListener {
            val name: String = binding.edtName.text.toString()
            val email: String = binding.edtEmail.text.toString()
            var specialist: String = binding.edtSpecialist.text.toString()
            var sex = if(binding.rbWomen.isChecked) 1 else 0
            val password: String = binding.edtPassword.text.toString()
            val passwordRepeat: String = binding.edtRepeatPassword.text.toString()
            var birth: String = binding.edtBirth.text.toString()
            val address: String = binding.edtAddress.text.toString()
            val phone: String = binding.edtPhone.text.toString()
            var type = if(binding.rbPatient.isChecked) 0 else 1

            if(isNotEnoughInfo()){
                setNotification(R.color.txt_green,R.string.enter_enough_info)
            }else if(!validateEmail(email)){
                setNotification(R.color.txt_red, R.string.fail_email)
            }else if(!validatePassword(password)){
                setNotification(R.color.txt_red, R.string.fail_password)
            }else if(password != passwordRepeat){
                setNotification(R.color.txt_red, R.string.enter_password_again)
            }else if(!validatePhone(phone)){
                setNotification(R.color.txt_red, R.string.warning_phone)
            }
            else{
                if(binding.rbAdmin.isChecked){
                    sex = 2
                    birth = ""
                    type = 2
                }

                if(!binding.rbDoctor.isChecked) specialist = ""

                viewModel.requestRegisterUser(
                    convertToRequestBody(email),
                    convertToRequestBody(sex.toString()),
                    convertToRequestBody(specialist),
                    convertToRequestBody(password),
                    convertToRequestBody(name),
                    convertToRequestBody(birth),
                    convertToRequestBody(phone),
                    convertToRequestBody(address),
                    convertToRequestBody(type.toString())
                )
            }
        }

        binding.layoutRegister.setOnTouchListener { view, _ ->
            view.hideKeyboard()
            true
        }
    }

    private fun resetView() {
        binding.edtName.setText("")
        binding.edtEmail.setText("")
        binding.edtSpecialist.setText("")
        binding.edtBirth.text = ""
        binding.edtPhone.setText("")
        binding.edtPassword.setText("")
        binding.edtRepeatPassword.setText("")
        binding.edtAddress.setText("")
    }

    private fun isNotEnoughInfo(): Boolean{
        return ((!binding.rbAdmin.isChecked) && (binding.edtName.text.isEmpty()
                || binding.edtEmail.text.isEmpty()
                || binding.edtPassword.text.isEmpty()
                || binding.edtRepeatPassword.text.isEmpty()
                || binding.edtBirth.text.isEmpty()
                || binding.edtAddress.text.isEmpty()
                || (!binding.rbMan.isChecked && !binding.rbWomen.isChecked)
                || !binding.cbAgree.isChecked)
                ) || (binding.rbDoctor.isChecked && binding.edtSpecialist.text.isEmpty())
    }

    private fun showDialogSelectDate() {
        val getDate = Calendar.getInstance()
        val datePicker = DatePickerDialog(requireActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->

                val selectDate: Calendar = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, year)
                selectDate.set(Calendar.MONTH, month)
                selectDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                binding.edtBirth.text = formatDate.format(selectDate.time)

            }, getDate.get(Calendar.YEAR), getDate.get((Calendar.MONTH)), getDate.get(Calendar.DAY_OF_MONTH))

        datePicker.show()
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