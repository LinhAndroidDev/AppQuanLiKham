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
import com.bumptech.glide.Glide
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentInformationBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.user.avatar.EditAvatarActivity
import com.example.appkhambenh.ui.ui.user.avatar.SeeAvatarActivity
import com.example.appkhambenh.ui.utils.validateEmail
import com.example.appkhambenh.ui.utils.validatePhone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentInformation : BaseFragment<InformationViewModel, FragmentInformationBinding>() {

    companion object {
        const val URI_AVATAR = "URI_AVATAR"
    }

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

        sharePrefer.getUserAvatar().let {
            if (it.isNotEmpty()) {
                Glide.with(requireActivity()).load(it)
                    .error(R.drawable.user_ad)
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
                val name = getValueInfo(binding.edtName, sharePrefer.getUserName())
                val email = getValueInfo(binding.edtEmail, sharePrefer.getUserEmail())
                val sex = if (binding.rbMan.isCheck) 0 else 1
                val birth = getValueInfo(binding.edtBirth, sharePrefer.getUserBirth())
                val phone = getValueInfo(binding.edtPhone, sharePrefer.getUserPhone())
                val address = getValueInfo(binding.edtAddress, sharePrefer.getUserAddress())

                viewModel.updateInfo(
                    convertToRequestBody(sharePrefer.getUserId().toString()),
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
            sharePrefer.getUserName()
        )

        binding.edtEmail.setTextHint(
            sharePrefer.getUserEmail()
        )

        binding.rbMan.setLabel(getString(R.string.males))
        binding.rbWomen.setLabel(getString(R.string.females))
        if (sharePrefer.getUserSex() == 0) checkMan() else checkWomen()

        binding.rbMan.setOnClickListener {
            if (!binding.rbMan.isCheck) {
                if (sharePrefer.getUserSex() == 0) disableButtonChangeInfo() else enableButtonChangeInfo()
                checkMan()
            }
        }

        binding.rbWomen.setOnClickListener {
            if (!binding.rbWomen.isCheck) {
                if (sharePrefer.getUserSex() == 1) disableButtonChangeInfo() else enableButtonChangeInfo()
                checkWomen()
            }
        }

        binding.edtBirth.visibleViewBirth()
        binding.edtBirth.setTextHint(sharePrefer.getUserBirth())
        binding.edtPhone.inputTypePhone()
        binding.edtPhone.setTextHint(sharePrefer.getUserPhone())
        binding.edtAddress.setTextHint(sharePrefer.getUserAddress())
    }

    private fun getValueInfo(edt: CustomTextViewInfo, key: String): String? {
        return edt.getTextView().let {
            it.ifEmpty { key }
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
            intent.putExtra(URI_AVATAR, data?.data.toString())
            startActivity(intent)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentInformationBinding.inflate(inflater)

}
