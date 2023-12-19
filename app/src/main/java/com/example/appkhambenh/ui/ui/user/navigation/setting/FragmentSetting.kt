package com.example.appkhambenh.ui.ui.user.navigation.setting

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentSettingBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.MainActivity
import com.example.appkhambenh.ui.ui.user.HomeActivity
import com.example.appkhambenh.ui.ui.user.avatar.SeeAvatarActivity
import com.example.appkhambenh.ui.ui.user.navigation.password.ChangePasswordActivity
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.squareup.picasso.Picasso

class FragmentSetting : BaseFragment<EmptyViewModel, FragmentSettingBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    @SuppressLint("IntentReset")
    private fun initUi() {

        viewModel.mPreferenceUtil.defaultPref().getString(PreferenceKey.USER_AVATAR, "")
            .let {
                if(it!!.isEmpty()){
                    binding.avtSetting.setImageResource(R.drawable.user_ad)
                }else{
                    Picasso.get().load(it)
                        .placeholder(R.drawable.user_ad)
                        .error(R.drawable.user_ad)
                        .into(binding.avtSetting)
                }
            }

        binding.nameSetting.text = viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.USER_NAME, "")

        binding.phoneSetting.text = viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.USER_PHONE, "")

        binding.avtSetting.setOnClickListener {
            val intent = Intent(requireActivity(), SeeAvatarActivity::class.java)
            startActivity(intent)
        }

        binding.updateInfoMain.setOnClickListener {
            val intent = Intent(requireActivity(), UpdateInformationActivity::class.java)
            startActivity(intent)
        }

        binding.updateInfo.setOnClickListener {
            val intent = Intent(requireActivity(), UpdateInformationActivity::class.java)
            startActivity(intent)
        }

        binding.resetPassword.setOnClickListener {
            val intent = Intent(requireActivity(), ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        binding.resetLanguage.setOnClickListener {
            (activity as HomeActivity).expandBottomSelectLanguage()
        }

        binding.logout.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            viewModel.mPreferenceUtil.defaultPref()
                .edit().putBoolean(PreferenceKey.CHECK_LOGIN, false)
                .apply()
        }
    }

    fun changeLanguageTextSetting(){
        binding.txtUpdateInfo.text = getString(R.string.update_info_person)
        binding.txtNoteUpdateInfo.text = getString(R.string.note_update_info_person)
        binding.txtSetting.text = getString(R.string.setting)
        binding.titleLanguage.text = getString(R.string.title_language)
        binding.txtChangePassword.text = getString(R.string.change_password)
        binding.txtLanguage.text = getString(R.string.language)
        binding.txtDifferent.text = getString(R.string.different)
        binding.txtFail.text = getString(R.string.notification_fail)
        binding.txtCondition.text = getString(R.string.condition)
        binding.txtDeleteAccount.text = getString(R.string.delete_account)
        binding.txtLogout.text = getString(R.string.logout)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentSettingBinding.inflate(inflater)

}