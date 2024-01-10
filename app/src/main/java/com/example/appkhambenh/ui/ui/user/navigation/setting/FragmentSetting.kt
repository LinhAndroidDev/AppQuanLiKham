package com.example.appkhambenh.ui.ui.user.navigation.setting

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentSettingBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.MainActivity
import com.example.appkhambenh.ui.ui.user.HomeActivity
import com.example.appkhambenh.ui.ui.user.avatar.SeeAvatarActivity
import com.example.appkhambenh.ui.ui.user.navigation.password.ChangePasswordActivity

class FragmentSetting : BaseFragment<EmptyViewModel, FragmentSettingBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    @SuppressLint("IntentReset")
    private fun initUi() {

        sharePrefer.getUserAvatar().let {
            if (it.isNotEmpty()) {
                Glide.with(requireActivity()).load(it)
                    .error(R.drawable.user_ad)
                    .into(binding.avtSetting)
            }
        }

        binding.nameSetting.text = sharePrefer.getUserName()

        binding.phoneSetting.text = sharePrefer.getUserPhone()

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

            sharePrefer.saveCheckLogin(false)
        }
    }

    fun changeLanguageTextSetting() {
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
    ) = FragmentSettingBinding.inflate(inflater)

}