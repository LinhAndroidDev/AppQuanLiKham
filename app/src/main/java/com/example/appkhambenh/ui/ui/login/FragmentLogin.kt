package com.example.appkhambenh.ui.ui.login

import android.annotation.SuppressLint
import android.app.ProgressDialog
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
import com.example.appkhambenh.ui.ui.user.LoginWithUser
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.doctor.LoginWithDoctorActivity
import com.example.appkhambenh.ui.ui.register.FragmentRegister
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.example.appkhambenh.ui.utils.validateEmail
import com.example.appkhambenh.ui.utils.validatePassword
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

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

        val loadData = ProgressDialog(requireActivity())
        loadData.setTitle("Thông báo")
        loadData.setMessage("Please wait...")
        viewModel.loadingLiveData.observe(viewLifecycleOwner){ isLoading->
            if(isLoading){
                loadData.show()
            }else{
                loadData.dismiss()
            }
        }

        viewModel.loginSuccessLiveData.observe(viewLifecycleOwner){ isSuccessful->
            if(isSuccessful){
                if(binding.checkForgetPassword.isChecked){
                    val email = binding.edtAccount.text.toString()
                    val password = binding.edtPassword.text.toString()
                    saveAccount(email, password, true)
                }else if(!binding.checkForgetPassword.isChecked){
                    val email = ""
                    val password = ""
                    saveAccount(email, password, false)
                }

                viewModel.mPreferenceUtil.defaultPref()
                    .edit().putBoolean(PreferenceKey.CHECK_LOGIN, true)
                    .apply()

                val intent = Intent(requireActivity(), LoginWithUser::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }

    private fun saveAccount(email: String, password: String, isForget: Boolean) {
        viewModel.mPreferenceUtil.defaultPref()
            .edit().putString(PreferenceKey.EMAIL,email)
            .apply()
        viewModel.mPreferenceUtil.defaultPref()
            .edit().putString(PreferenceKey.PASSWORD,password)
            .apply()
        viewModel.mPreferenceUtil.defaultPref()
            .edit().putBoolean(PreferenceKey.FORGET_PASSWORD, isForget)
            .apply()
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
                binding.edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        binding.login.setOnClickListener {
            val email = binding.edtAccount.text.toString()
            val password = binding.edtPassword.text.toString()
            if(email.isEmpty() || password.isEmpty()){
                setNotification(R.color.txt_green,R.string.txt_enter_enough_info)
            }else if(!validateEmail(email)){
                setNotification(R.color.txt_red,R.string.txt_fail_email)
            }else if(!validatePassword(password)){
                setNotification(R.color.txt_red,R.string.txt_fail_password)
            } else{
                binding.notificationLogin.visibility = View.GONE

                val requestBodyEmail: RequestBody =
                    email.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val requestBodyPassword: RequestBody =
                    password.toRequestBody("multipart/form-data".toMediaTypeOrNull())

                viewModel.requestLoginUser(requestBodyEmail, requestBodyPassword)
            }
        }

        binding.register.setOnClickListener {
            val fragmentRegister = FragmentRegister()
            val fm: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fm.replace(R.id.changeIdLogin,fragmentRegister).addToBackStack(null).commit()
        }

        binding.txtLoginWithDoctor.setOnClickListener {
            val intent = Intent(requireActivity(), LoginWithDoctorActivity::class.java)
            startActivity(intent)
        }

        binding.layoutLogin.setOnTouchListener { view, _ ->
            view.hideKeyboard()
            true
        }
    }

    private fun checkSaveAccount(){
        val email = viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.EMAIL,"")
        val password = viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.PASSWORD,"")
        val isForgetPassword = viewModel.mPreferenceUtil.defaultPref()
            .getBoolean(PreferenceKey.FORGET_PASSWORD,false)

        binding.edtAccount.setText(email)
        binding.edtPassword.setText(password)
        binding.checkForgetPassword.isChecked = true
    }

    private fun setNotification(color: Int, string: Int){
        val shake: Animation = AnimationUtils.loadAnimation(requireActivity(),R.anim.anim_shake)
        binding.notificationLogin.text = resources.getString(string)
        binding.notificationLogin.setTextColor(resources.getColor(color))
        binding.notificationLogin.visibility = View.VISIBLE
        binding.notificationLogin.startAnimation(shake)
    }

    override fun onFragmentBack(): Boolean {
        return true
    }
}