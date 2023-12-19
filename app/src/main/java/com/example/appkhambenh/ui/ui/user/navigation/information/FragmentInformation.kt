package com.example.appkhambenh.ui.ui.user.navigation.information

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentInformationBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.user.avatar.EditAvatarActivity
import com.example.appkhambenh.ui.ui.user.avatar.SeeAvatarActivity
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.example.appkhambenh.ui.utils.validateEmail
import com.example.appkhambenh.ui.utils.validatePhone
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentInformation : BaseFragment<InformationViewModel, FragmentInformationBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    @SuppressLint("ClickableViewAccessibility", "IntentReset")
    private fun initUi() {

        disableButtonChangeInfo()

        binding.backInfo.setOnClickListener { back() }

        binding.ciEditAvatar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "images/"
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            startActivityForResult(intent, 100)
        }

        binding.layoutInfo.setOnTouchListener { _, _ ->
            closeKeyboard()
            false
        }

        lifecycleScope.launch {
            delay(500L)
            withContext(Dispatchers.Main){
                createView()
            }
        }

        viewModel.mPreferenceUtil.defaultPref().getString(PreferenceKey.USER_AVATAR, "").let {
            if (it!!.isNotEmpty()) {
                Picasso.get().load(it)
                    .error(R.drawable.user_ad)
                    .placeholder(R.drawable.user_ad)
                    .into(binding.avatarInfo)
            }
        }

        binding.avatarInfo.setOnClickListener {
            val intent = Intent(requireActivity(), SeeAvatarActivity::class.java)
            startActivity(intent)
        }

        checkEnableButtonInfo(binding.edtName)
        checkEnableButtonInfo(binding.edtEmail)
        checkEnableButtonInfo(binding.edtBirth)
        checkEnableButtonInfo(binding.edtPhone)
        checkEnableButtonInfo(binding.edtAddress)

        binding.changeInfo.setOnClickListener {
            if (binding.edtEmail.getTextView()
                    .isNotEmpty() && !validateEmail(binding.edtEmail.getTextView())
            ) {
                show(getString(R.string.fail_email))
            } else if (binding.edtPhone.getTextView()
                    .isNotEmpty() && !validatePhone(binding.edtPhone.getTextView())
            ) {
                show(resources.getString(R.string.warning_phone))
            } else {
                val name = getValueInfo(binding.edtName, PreferenceKey.USER_NAME)
                val email = getValueInfo(binding.edtEmail, PreferenceKey.USER_EMAIL)
                val sex = if (binding.rbMan.isCheck) 0 else 1
                val birth = getValueInfo(binding.edtBirth, PreferenceKey.USER_BIRTH)
                val phone = getValueInfo(binding.edtPhone, PreferenceKey.USER_PHONE)
                val address = getValueInfo(binding.edtAddress, PreferenceKey.USER_ADDRESS)

                viewModel.updateInfo(
                    convertToRequestBody(userId.toString()),
                    convertToRequestBody(name!!),
                    convertToRequestBody(email!!),
                    convertToRequestBody(sex.toString()),
                    convertToRequestBody(birth!!),
                    convertToRequestBody(phone!!),
                    convertToRequestBody(address!!)
                )

                viewModel.isLoadingLiveData.observe(requireActivity()) { isLoading ->
                    if (isLoading) loading.show() else loading.dismiss()
                }

                viewModel.updateInfoSuccessfulLiveData.observe(requireActivity()) { isSuccessful ->
                    if (isSuccessful) {
                        show(getString(R.string.update_infor_success))
                        back()
                    }
                }
            }
        }
    }

    private fun createView() {
        binding.edtName.setTextHint(
            viewModel.mPreferenceUtil.defaultPref()
                .getString(PreferenceKey.USER_NAME, "")
        )

        binding.edtEmail.setTextHint(
            viewModel.mPreferenceUtil.defaultPref()
                .getString(PreferenceKey.USER_EMAIL, "")
        )

        binding.rbMan.setLabel(getString(R.string.males))
        binding.rbWomen.setLabel(getString(R.string.females))
        if (sex == 0) checkMan() else checkWomen()

        binding.rbMan.setOnClickListener {
            if (!binding.rbMan.isCheck) {
                if (sex == 0) disableButtonChangeInfo() else enableButtonChangeInfo()
                checkMan()
            }
        }

        binding.rbWomen.setOnClickListener {
            if (!binding.rbWomen.isCheck) {
                if (sex == 1) disableButtonChangeInfo() else enableButtonChangeInfo()
                checkWomen()
            }
        }

        binding.edtBirth.visibleViewBirth()
        binding.edtBirth.setTextHint(
            viewModel.mPreferenceUtil.defaultPref()
                .getString(PreferenceKey.USER_BIRTH, "")
        )
        binding.edtPhone.inputTypePhone()
        binding.edtPhone.setTextHint(
            viewModel.mPreferenceUtil.defaultPref()
                .getString(PreferenceKey.USER_PHONE, "")
        )
        binding.edtAddress.setTextHint(
            viewModel.mPreferenceUtil.defaultPref()
                .getString(PreferenceKey.USER_ADDRESS, "")
        )
    }

    private fun getValueInfo(edt: CustomTextViewInfo, key: String): String? {
        return edt.getTextView().let {
            it.ifEmpty {
                viewModel.mPreferenceUtil.defaultPref()
                    .getString(key, "")
            }
        }
    }

    private fun checkMan() {
        binding.rbMan.setCheck()
        binding.rbWomen.setUnCheck()
    }

    private fun checkWomen() {
        binding.rbWomen.setCheck()
        binding.rbMan.setUnCheck()
    }

    private fun checkEnableButtonInfo(edt: CustomTextViewInfo) {
        edt.isEnableButtonUpdateInfo = {
            if (it) enableButtonChangeInfo()
        }

        edt.allTextEmpty = {
            if (it && !checkValidateName()) disableButtonChangeInfo()
        }
    }

    private fun checkValidateName(): Boolean {
        return (binding.edtName.getTextView().trim().isNotEmpty()
                || binding.edtEmail.getTextView().trim().isNotEmpty()
                || binding.edtBirth.getTextView().trim().isNotEmpty()
                || binding.edtPhone.getTextView().trim().isNotEmpty()
                || binding.edtAddress.getTextView().trim().isNotEmpty())
    }

    private fun disableButtonChangeInfo() {
        binding.changeInfo.alpha = 0.5f
        binding.changeInfo.isEnabled = false
    }

    private fun enableButtonChangeInfo() {
        binding.changeInfo.alpha = 1f
        binding.changeInfo.isEnabled = true
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
    ) = FragmentInformationBinding.inflate(inflater)

}
