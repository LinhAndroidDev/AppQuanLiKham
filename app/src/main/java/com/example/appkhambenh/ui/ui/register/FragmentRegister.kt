package com.example.appkhambenh.ui.ui.register

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentRegisterBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.data.remote.model.RegisterModel
import com.example.appkhambenh.ui.utils.DateUtils
import com.example.appkhambenh.ui.utils.validateEmail
import com.example.appkhambenh.ui.utils.validatePassword
import com.example.appkhambenh.ui.utils.validatePhone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
@AndroidEntryPoint
class FragmentRegister : BaseFragment<RegisterViewModel, FragmentRegisterBinding>() {

    private val calendar by lazy { Calendar.getInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    override fun bindData() {
        super.bindData()

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) loading.show() else loading.dismiss()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initUi() {

        binding.rbPatient.isChecked = true

        binding.backRegister.setOnClickListener { back() }

        binding.cancel.setOnClickListener { back() }

        binding.edtBirth.setOnClickListener {
            showDialogSelectDate()
        }

        binding.rbAdmin.setOnCheckedChangeListener { _, b ->
            if (b) {
                resetView()
                binding.txtTitleName.text = "Cơ sở y tế"
                binding.edtName.hint = "Nhập cơ sở y tế"
                binding.layoutSex.visibility = View.GONE
                binding.layoutBirth.visibility = View.GONE
                binding.layoutSpecialist.visibility = View.GONE
            }
        }

        binding.rbDoctor.setOnCheckedChangeListener { _, b ->
            if (b) {
                resetView()
                binding.txtTitleName.text = "Bác sĩ"
                binding.edtName.hint = "Nhập tên Bác Sĩ"
                binding.layoutSex.visibility = View.VISIBLE
                binding.layoutBirth.visibility = View.VISIBLE
                binding.layoutSpecialist.visibility = View.VISIBLE
            }
        }

        binding.rbPatient.setOnCheckedChangeListener { _, b ->
            if (b) {
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
            val sex = if (binding.rbWomen.isChecked) 1 else 0
            val password: String = binding.edtPassword.text.toString()
            val passwordRepeat: String = binding.edtRepeatPassword.text.toString()
            val birth: String = binding.edtBirth.text.toString()
            val address: String = binding.edtAddress.text.toString()
            val phone: String = binding.edtPhone.text.toString()

            if (isNotEnoughInfo()) {
                setNotification(R.color.txt_green, R.string.enter_enough_info)
            } else if (!validateEmail(email)) {
                setNotification(R.color.txt_red, R.string.fail_email)
            } else if (!validatePassword(password)) {
                setNotification(R.color.txt_red, R.string.fail_password)
            } else if (password != passwordRepeat) {
                setNotification(R.color.txt_red, R.string.enter_password_again)
            } else if (!validatePhone(phone)) {
                setNotification(R.color.txt_red, R.string.warning_phone)
            } else {

                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.requestRegisterUser(
                        RegisterModel(
                            userName = name,
                            userType = 4,
                            userEmail = email,
                            userPassword = password,
                            userPhoneNumber = phone,
                            userBirthday = DateUtils.convertDateToLong(birth),
                            userGender = sex
                        )
                    )

                    viewModel.registerSuccessful.collect { isSuccessful ->
                        if (isSuccessful) {
                            activity?.onBackPressed()
                        }
                    }
                }
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
        binding.edtBirth.setText("")
        binding.edtPhone.setText("")
        binding.edtPassword.setText("")
        binding.edtRepeatPassword.setText("")
        binding.edtAddress.setText("")
    }

    private fun isNotEnoughInfo(): Boolean {
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
        DatePickerDialog(
            requireActivity(),
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                setDateWithText()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun setDateWithText() {
        val dateFormat = SimpleDateFormat(DateUtils.DAY_OF_YEAR, Locale.getDefault())
        binding.edtBirth.setText(dateFormat.format(calendar.time))
    }

    private fun setNotification(color: Int, string: Int) {
        val shake: Animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_shake)
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