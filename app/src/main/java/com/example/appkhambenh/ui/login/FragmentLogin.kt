package com.example.appkhambenh.ui.login

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.FragmentTransaction
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.LoginWithUser
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.register.FragmentRegister
import com.example.appkhambenh.ui.utils.Email
import kotlinx.android.synthetic.main.fragment_login.*

@Suppress("DEPRECATION")
class FragmentLogin : BaseFragment<LoginViewModel>() {
    lateinit var email: String
    lateinit var password: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        click()
    }

    override fun bindData() {
        super.bindData()

        val loadData: ProgressDialog = ProgressDialog(requireActivity())
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
                val intent: Intent = Intent(requireActivity(), LoginWithUser::class.java)
                startActivity(intent)
            }
        }
    }

    @SuppressLint("CommitTransaction")
    private fun click() {
        showPassword.setOnClickListener {
            if (edtPassword.transformationMethod == null) {
                showPassword.setBackgroundResource(R.drawable.icon_show_password_grey)
                edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            } else if (edtPassword.transformationMethod == PasswordTransformationMethod.getInstance()) {
                showPassword.setBackgroundResource(R.drawable.icon_hint_grey)
                edtPassword.transformationMethod = null
            }
        }

        login.setOnClickListener {
//            email = edtAccount.text.toString()
//            password = edtPassword.text.toString()
//            val emailValidate: Email = Email(email,password)
//            if(email.isEmpty() || password.isEmpty()){
//                setNotification(R.color.txt_green,R.string.txt_enter_enough_info)
//            }else if(!emailValidate.isValidEmail()){
//                setNotification(R.color.txt_red,R.string.txt_fail_email)
//            }else if(!emailValidate.isPassword()){
//                setNotification(R.color.txt_red,R.string.txt_fail_password)
//            } else{
//                notificationLogin.visibility = View.GONE
//
//                val requestLogin: RequestLogin = RequestLogin(email, password)
//                viewModel.requestLoginUser(requestLogin)
//            }
            val intent: Intent = Intent(requireActivity(), LoginWithUser::class.java)
            startActivity(intent)
        }

        register.setOnClickListener {
            val fragmentRegister: FragmentRegister = FragmentRegister()
            val fm: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fm.replace(R.id.changeIdLogin,fragmentRegister).addToBackStack(null).commit()
        }
    }

    fun setNotification(color: Int,string: Int){
        val shake: Animation = AnimationUtils.loadAnimation(requireActivity(),R.anim.anim_shake)
        notificationLogin.text = resources.getString(string)
        notificationLogin.setTextColor(resources.getColor(color))
        notificationLogin.visibility = View.VISIBLE
        notificationLogin.startAnimation(shake)
    }

    override fun onFragmentBack(): Boolean {
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
}