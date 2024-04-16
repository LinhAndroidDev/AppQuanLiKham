package com.example.appkhambenh.ui.ui.user.avatar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityEditAvatarBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.user.HomeActivity
import com.example.appkhambenh.ui.ui.user.navigation.setting.FragmentSetting
import com.example.appkhambenh.ui.utils.UriConvertFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

@Suppress("DEPRECATION")
@AndroidEntryPoint
class EditAvatarActivity : BaseActivity<UploadImageViewModel, ActivityEditAvatarBinding>() {
    private var imgUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    override fun bindData() {
        super.bindData()
        viewModel.loading.observe(this) {
            if (it) loading.show() else loading.dismiss()
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.isSuccessful.collect {
                if (it) {
                    val intent = Intent(this@EditAvatarActivity, HomeActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
        }
    }

    private fun initUi() {

        overridePendingTransition(R.anim.enter_avt, R.anim.exit_avt)

//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )

        val strUri: String = intent.getStringExtra(FragmentSetting.URI_AVATAR).toString()
        imgUri = Uri.parse(strUri)
        binding.imgAvatar.setImageURI(imgUri)

        binding.txtUpdateAvatar.setOnClickListener {
            if (imgUri != null) {
//                val strFile =
//                    UriConvertFile.getRealPath(this@EditAvatarActivity, imgUri!!).toString()
//                val file = File(strFile)
//                val multipartBodyAvatar = MultipartBody.Part.createFormData(
//                    "avatar",
//                    file.name,
//                    convertToRequestBody(file.toString())
//                )
                val strFile = UriConvertFile.getFileFromUri(this@EditAvatarActivity, imgUri).toString()
                val file = File(strFile)
                val requestBodyAvatar =RequestBody.create("multipart/form-data".toMediaTypeOrNull(),file)
                val multipartBodyAvt = MultipartBody.Part.createFormData("avatar",file.name,requestBodyAvatar)
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.updateAvatar(
                        sharePrefer.getUserId(),
                        multipartBodyAvt
                    )
                }
            }
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityEditAvatarBinding.inflate(inflater)

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.push_avt, R.anim.pop_avt)
    }
}