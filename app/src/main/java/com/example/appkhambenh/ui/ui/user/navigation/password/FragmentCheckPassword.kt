package com.example.appkhambenh.ui.ui.user.navigation.password

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentCheckPasswordBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.user.avatar.SeeAvatarActivity
import com.example.appkhambenh.ui.ui.user.navigation.information.CustomTextViewInfo
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.squareup.picasso.Picasso

class FragmentCheckPassword : BaseFragment<CheckPasswordViewModel, FragmentCheckPasswordBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun initUi() {
        binding.backCheckPassword.setOnClickListener { back() }

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

        binding.edtPassWordOld.inputTypePassword()
        binding.edtPassWordOld.setTextHint("Mật khẩu")
        checkEnableButtonInfo(binding.edtPassWordOld)

        binding.hidePassword.setOnCheckedChangeListener { _, b ->
            if(b){
                binding.edtPassWordOld.showPassword()
            }else{
                binding.edtPassWordOld.hidePassword()
            }
        }

        binding.checkPassword.setOnClickListener {
            viewModel.checkPassword(
                convertToRequestBody(userId.toString()),
                convertToRequestBody(binding.edtPassWordOld.getTextView())
            )

            viewModel.isLoadingLiveData.observe(viewLifecycleOwner){
                if(it) {
                    binding.loadingData.visibility = View.VISIBLE
                }else {
                    binding.loadingData.visibility = View.GONE
                }
            }

            viewModel.isSuccessfulLiveData.observe(viewLifecycleOwner){
                if(it){
                    val fragmentCreatePassword = FragmentCreatePassword()
                    val fm = requireActivity().supportFragmentManager.beginTransaction()
                        fm.setCustomAnimations(R.anim.fade_in, R.anim.exit, R.anim.enter, R.anim.fade_out)
                        fm.replace(R.id.changeIdPassword, fragmentCreatePassword).addToBackStack(null).commit()
                    val bundle = Bundle()
                    bundle.putString("Password", binding.edtPassWordOld.getTextView())
                    fragmentCreatePassword.arguments = bundle
                }
            }
        }
    }

    private fun checkEnableButtonInfo(edt: CustomTextViewInfo){
        edt.isEnableButtonUpdateInfo = {
            if(it) enableButtonChangeInfo() else disableButtonChangeInfo()
        }
    }

    private fun disableButtonChangeInfo(){
        binding.checkPassword.alpha = 0.5f
        binding.checkPassword.isEnabled = false
    }

    private fun enableButtonChangeInfo(){
        binding.checkPassword.alpha = 1f
        binding.checkPassword.isEnabled = true
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentCheckPasswordBinding.inflate(inflater)

    override fun onFragmentBack(): Boolean {
        return true
    }
}