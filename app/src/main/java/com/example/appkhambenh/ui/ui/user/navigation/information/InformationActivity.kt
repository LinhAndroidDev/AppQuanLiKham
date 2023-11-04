package com.example.appkhambenh.ui.ui.user.navigation.information

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityInformationBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.user.LoginWithUser
import com.example.appkhambenh.ui.ui.user.avatar.SeeAvatarActivity
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.example.appkhambenh.ui.utils.validateEmail
import com.example.appkhambenh.ui.utils.validatePhone
import com.squareup.picasso.Picasso

class InformationActivity : BaseActivity<InformationViewModel, ActivityInformationBinding>(){
    var sex: Int = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    private fun initUi() {

        disableButtonChangeInfo()

        binding.backInfo.setOnClickListener { finish() }

        binding.edtName.setTextHint(
            viewModel.mPreferenceUtil.defaultPref()
                .getString(PreferenceKey.USER_NAME, "")
        )

        binding.edtEmail.setTextHint(
            viewModel.mPreferenceUtil.defaultPref()
                .getString(PreferenceKey.USER_EMAIL, "")
        )

        sex = viewModel.mPreferenceUtil.defaultPref()
            .getInt(PreferenceKey.USER_SEX, -1)
        if(sex == 0){
            binding.rbMan.isChecked = true
        } else{
            binding.rbWomen.isChecked = true
        }

        binding.rbMan.setOnCheckedChangeListener { _, b ->
            if(b){
                if(sex == 0 && binding.rbMan.isChecked)
                    disableButtonChangeInfo() else enableButtonChangeInfo()
            }
        }

        binding.rbWomen.setOnCheckedChangeListener { _, b ->
            if(b){
                if(sex == 1 && binding.rbWomen.isChecked)
                    disableButtonChangeInfo() else enableButtonChangeInfo()
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

        viewModel.mPreferenceUtil.defaultPref().getString(PreferenceKey.USER_AVATAR, "").let {
            if(it!!.isNotEmpty()){
                Picasso.get().load(it)
                    .error(R.drawable.user_ad)
                    .placeholder(R.drawable.user_ad)
                    .into(binding.avatarInfo)
            }
        }

        binding.avatarInfo.setOnClickListener {
            val intent = Intent(this@InformationActivity, SeeAvatarActivity::class.java)
            startActivity(intent)
        }

        checkEnableButtonInfo(binding.edtName)
        checkEnableButtonInfo(binding.edtEmail)
        checkEnableButtonInfo(binding.edtBirth)
        checkEnableButtonInfo(binding.edtPhone)
        checkEnableButtonInfo(binding.edtAddress)

        binding.changeInfo.setOnClickListener {
            if(binding.edtEmail.getTextView().isNotEmpty() && !validateEmail(binding.edtEmail.getTextView())){
                show("Email không đúng định dạng")
            }else if(binding.edtPhone.getTextView().isNotEmpty() && !validatePhone(binding.edtPhone.getTextView())){
                    show(resources.getString(R.string.txt_warning_phone))
            }else{
                val user_id = viewModel.mPreferenceUtil.defaultPref()
                    .getInt(PreferenceKey.USER_ID, -1).toString()
                val name = getValueInfo(binding.edtName, PreferenceKey.USER_NAME)
                val email = getValueInfo(binding.edtEmail, PreferenceKey.USER_EMAIL)
                val sex = if(binding.rbMan.isChecked) 0 else 1
                val birth = getValueInfo(binding.edtBirth, PreferenceKey.USER_BIRTH)
                val phone = getValueInfo(binding.edtPhone, PreferenceKey.USER_PHONE)
                val address = getValueInfo(binding.edtAddress, PreferenceKey.USER_ADDRESS)

                viewModel.updateInfo(
                    convertToRequestBody(user_id),
                    convertToRequestBody(name!!),
                    convertToRequestBody(email!!),
                    convertToRequestBody(sex.toString()),
                    convertToRequestBody(birth!!),
                    convertToRequestBody(phone!!),
                    convertToRequestBody(address!!)
                )

                viewModel.isLoadingLiveData.observe(this@InformationActivity) { isLoading ->
                    binding.loadingData.visibility = if(isLoading) View.VISIBLE else View.GONE
                }

                viewModel.updateInfoSuccessfulLiveData.observe(this@InformationActivity){ isSuccessful->
                    if(isSuccessful){
                        show("Bạn đã cập nhật lại thông tin cá nhân thành công")
                        val intent = Intent(this@InformationActivity, LoginWithUser::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun getValueInfo(edt: CustomTextViewInfo, key: String): String? {
        return edt.getTextView().let {
            it.ifEmpty {
                viewModel.mPreferenceUtil.defaultPref()
                    .getString(key, "")
            }
        }
    }


    private fun checkEnableButtonInfo(edt: CustomTextViewInfo){
        edt.isEnableButtonUpdateInfo = {
            if(it) enableButtonChangeInfo()
        }

        edt.allTextEmpty = {
            if(it && !checkValidateName()) disableButtonChangeInfo()
        }
    }

    private fun checkValidateName(): Boolean {
        return (binding.edtName.getTextView().trim().isNotEmpty()
                || binding.edtEmail.getTextView().trim().isNotEmpty()
                || binding.edtBirth.getTextView().trim().isNotEmpty()
                || binding.edtPhone.getTextView().trim().isNotEmpty()
                || binding.edtAddress.getTextView().trim().isNotEmpty())
    }

    private fun disableButtonChangeInfo(){
        binding.changeInfo.alpha = 0.5f
        binding.changeInfo.isEnabled = false
    }

    private fun enableButtonChangeInfo(){
        binding.changeInfo.alpha = 1f
        binding.changeInfo.isEnabled = true
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityInformationBinding.inflate(inflater)
}