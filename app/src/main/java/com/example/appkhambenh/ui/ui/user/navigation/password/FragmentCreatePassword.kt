package com.example.appkhambenh.ui.ui.user.navigation.password

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentCreatePasswordBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.user.LoginWithUser
import com.example.appkhambenh.ui.ui.user.avatar.SeeAvatarActivity
import com.example.appkhambenh.ui.ui.user.navigation.information.CustomTextViewInfo
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.example.appkhambenh.ui.utils.validatePassword
import com.squareup.picasso.Picasso

class FragmentCreatePassword : BaseFragment<ChangePasswordViewModel, FragmentCreatePasswordBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {
        val password = arguments?.getString("Password", "")

        binding.edtPassWordNew.inputTypePassword()
        binding.edtPassWordNew.setTextHint("Mật khẩu")
        checkEnableButtonInfo(binding.edtPassWordNew)

        val avatar = viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.USER_AVATAR, "").toString()
        if(avatar.isNotEmpty()) {
            Picasso.get().load(avatar)
                .placeholder(R.drawable.user_ad)
                .error(R.drawable.user_ad)
                .into(binding.avatarInfo)
        }
        binding.avatarInfo.setOnClickListener {
            val intent = Intent(requireActivity(), SeeAvatarActivity::class.java)
            startActivity(intent)
        }

        binding.hidePassword.setOnCheckedChangeListener { _, b ->
            if(b) {
                binding.edtPassWordNew.showPassword()
            }else {
                binding.edtPassWordNew.hidePassword()
            }
        }

        binding.createPassword.setOnClickListener {
            if (!validatePassword(binding.edtPassWordNew.getTextView())){
                show(resources.getString(R.string.txt_fail_password))
            }else if(binding.edtPassWordNew.getTextView() == password){
                show("Mật khẩu mới phải khác mật khẩu cũ")
            }else {
                viewModel.changePassword(
                    convertToRequestBody(id_user.toString()),
                    convertToRequestBody(binding.edtPassWordNew.getTextView())
                )

                viewModel.isLoadingLiveData.observe(viewLifecycleOwner){
                    if(it){
                        binding.loadingData.visibility = View.VISIBLE
                    }else{
                        binding.loadingData.visibility = View.GONE
                    }
                }

                viewModel.isSuccessfulLiveData.observe(viewLifecycleOwner){
                    if(it){
                        show("Bạn đã thay đổi mật khẩu thành công")
                        val intent = Intent(requireActivity(), LoginWithUser::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }
            }
        }

        binding.backCreatePassword.setOnClickListener { back() }

    }

    private fun checkEnableButtonInfo(edt: CustomTextViewInfo){
        edt.isEnableButtonUpdateInfo = {
            if(it) enableButtonChangeInfo() else disableButtonChangeInfo()
        }
    }

    private fun disableButtonChangeInfo(){
        binding.createPassword.alpha = 0.5f
        binding.createPassword.isEnabled = false
    }

    private fun enableButtonChangeInfo(){
        binding.createPassword.alpha = 1f
        binding.createPassword.isEnabled = true
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentCreatePasswordBinding.inflate(inflater)

    override fun onFragmentBack(): Boolean {
        return false
    }
}