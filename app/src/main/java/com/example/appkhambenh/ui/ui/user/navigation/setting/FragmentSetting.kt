package com.example.appkhambenh.ui.ui.user.navigation.setting

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentSettingBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.MainActivity
import com.example.appkhambenh.ui.ui.user.HomeActivity
import com.example.appkhambenh.ui.ui.user.avatar.EditAvatarActivity
import com.example.appkhambenh.ui.ui.user.avatar.SeeAvatarActivity
import com.example.appkhambenh.ui.ui.user.navigation.password.ChangePasswordActivity
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
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

        binding.ciEditAvatar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "images/"
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            startActivityForResult(intent, 100)
        }

        binding.avtSetting.setOnClickListener {
            val intent = Intent(requireActivity(), SeeAvatarActivity::class.java)
            startActivity(intent)
        }

        binding.editProfile.setOnClickListener {
            (activity as HomeActivity).goToEditProfile()
        }

        binding.updateInfo.setOnClickListener {
            (activity as HomeActivity).goToEditProfile()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK) {
            val intent = Intent(requireActivity(), EditAvatarActivity::class.java)
            intent.putExtra("uri_avatar", data?.data.toString())
            startActivity(intent)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentSettingBinding.inflate(inflater)

}