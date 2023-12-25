package com.example.appkhambenh.ui.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.FragmentTransaction
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentLoginBinding
import com.example.appkhambenh.ui.ui.user.HomeActivity
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.register.FragmentRegister
import com.example.appkhambenh.ui.utils.validateEmail
import com.example.appkhambenh.ui.utils.validatePassword

@Suppress("DEPRECATION")
class FragmentLogin : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    override fun bindData() {
        super.bindData()

        viewModel.loadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) loading.show() else loading.dismiss()
        }

        viewModel.loginSuccessLiveData.observe(viewLifecycleOwner) { isSuccessful ->
            if (isSuccessful) {
                if (binding.checkForgetPassword.isChecked) {
                    saveAccount(
                        email = binding.edtAccount.text.toString(),
                        password = binding.edtPassword.text.toString(),
                        isForget = true
                    )
                } else if (!binding.checkForgetPassword.isChecked) {
                    saveAccount("", "", false)
                }

                sharePrefer.saveCheckLogin(true)

                val intent = Intent(requireActivity(), HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }

    private fun saveAccount(email: String, password: String, isForget: Boolean) {
        sharePrefer.saveEmail(email)
        sharePrefer.savePassword(password)
        sharePrefer.saveRememberPassword(isForget)
    }

    @SuppressLint("CommitTransaction", "ClickableViewAccessibility")
    private fun initUi() {

        checkSaveAccount()

        binding.showPassword.setOnClickListener {
            if (binding.edtPassword.transformationMethod == PasswordTransformationMethod.getInstance()) {
                binding.showPassword.setBackgroundResource(R.drawable.icon_show_password_grey)
                binding.edtPassword.transformationMethod = null
            } else if (binding.edtPassword.transformationMethod == null) {
                binding.showPassword.setBackgroundResource(R.drawable.icon_hint_grey)
                binding.edtPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }

        binding.login.setOnClickListener {
            val email = binding.edtAccount.text.toString()
            val password = binding.edtPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                setNotification(R.color.txt_green, R.string.enter_enough_info)
            } else if (!validateEmail(email)) {
                setNotification(R.color.txt_red, R.string.fail_email)
            } else if (!validatePassword(password)) {
                setNotification(R.color.txt_red, R.string.fail_password)
            } else {
                binding.notificationLogin.visibility = View.GONE

                viewModel.requestLoginUser(
                    convertToRequestBody(email),
                    convertToRequestBody(password),
                    requireActivity()
                )
            }
        }

        binding.register.setOnClickListener {
            val fragmentRegister = FragmentRegister()
            val fm: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            fm.replace(R.id.changeIdLogin, fragmentRegister).addToBackStack(null).commit()
        }

        binding.layoutLogin.setOnTouchListener { view, _ ->
            view.hideKeyboard()
            true
        }
    }

    private fun checkSaveAccount() {
        binding.edtAccount.setText(sharePrefer.getEmail())
        binding.edtPassword.setText(sharePrefer.getPassword())
        binding.checkForgetPassword.isChecked = true
    }

    private fun setNotification(color: Int, string: Int) {
        val shake: Animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_shake)
        binding.notificationLogin.text = resources.getString(string)
        binding.notificationLogin.setTextColor(resources.getColor(color))
        binding.notificationLogin.visibility = View.VISIBLE
        binding.notificationLogin.startAnimation(shake)
    }

    override fun onFragmentBack(): Boolean {
        return true
    }
}