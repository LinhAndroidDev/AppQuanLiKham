package com.example.appkhambenh.ui.ui.user.navigation.setting

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentSettingBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.MainActivity
import com.example.appkhambenh.ui.ui.user.HomeActivity
import com.example.appkhambenh.ui.ui.user.avatar.EditAvatarActivity
import com.example.appkhambenh.ui.ui.user.navigation.password.ChangePasswordActivity
import com.yalantis.ucrop.UCrop
import java.io.File


@Suppress("DEPRECATION")
class FragmentSetting : BaseFragment<EmptyViewModel, FragmentSettingBinding>() {

    companion object {
        const val URI_AVATAR = "URI_AVATAR"
        const val PICK_IMAGE_CODE = 100
    }

    private val uCropContract = object : ActivityResultContract<List<Uri?>, Uri?>(){
        override fun createIntent(context: Context, input: List<Uri?>): Intent {
            val inputUri = input[0]
            val outputUri = input[1]

            val uCop = UCrop.of(inputUri!!, outputUri!!)
                .withAspectRatio(5f, 5f)
                .withMaxResultSize(800, 800)

            return uCop.getIntent(context)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return if(intent != null) UCrop.getOutput(intent) else null
        }

    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
        if(uri != null){
            val outputUri = File(requireActivity().filesDir, "croppedImage.jpg").toUri()
            cropImage.launch(listOf(uri, outputUri))
        }
    }

    private val cropImage = registerForActivityResult(uCropContract) {
        if(it != null) {
            val intent = Intent(requireActivity(), EditAvatarActivity::class.java)
            intent.putExtra(URI_AVATAR, it.toString())
            startActivity(intent)
        }
    }

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
            getContent.launch("image/*")
//            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            startActivityForResult(galleryIntent, PICK_IMAGE_CODE)
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
        with(binding) {
            txtUpdateInfo.text = getString(R.string.update_info_person)
            txtNoteUpdateInfo.text = getString(R.string.note_update_info_person)
            txtSetting.text = getString(R.string.setting)
            titleLanguage.text = getString(R.string.title_language)
            txtChangePassword.text = getString(R.string.change_password)
            txtLanguage.text = getString(R.string.language)
            txtDifferent.text = getString(R.string.different)
            txtFail.text = getString(R.string.notification_fail)
            txtCondition.text = getString(R.string.condition)
            txtDeleteAccount.text = getString(R.string.delete_account)
            txtLogout.text = getString(R.string.logout)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_CODE) {
            if(data != null) {
                val intent = Intent(requireActivity(), EditAvatarActivity::class.java)
                intent.putExtra(URI_AVATAR, data.data.toString())
                startActivity(intent)
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentSettingBinding.inflate(inflater)

}