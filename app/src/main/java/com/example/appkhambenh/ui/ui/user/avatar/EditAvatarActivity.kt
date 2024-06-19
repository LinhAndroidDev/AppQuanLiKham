package com.example.appkhambenh.ui.ui.user.avatar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityEditAvatarBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.user.HomeActivity
import com.example.appkhambenh.ui.ui.user.navigation.setting.FragmentSetting
import com.example.appkhambenh.ui.utils.ConvertUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

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
        val strUri: String = intent.getStringExtra(FragmentSetting.URI_AVATAR).toString()
        imgUri = Uri.parse(strUri)
        binding.imgAvatar.setImageURI(imgUri)

        binding.txtUpdateAvatar.setOnClickListener {
            if (imgUri != null) {
                // Tạo RequestBody từ File
                val file = ConvertUtils.convertUriToFile(this, imgUri)
                val requestBody = file.asRequestBody("image/png".toMediaTypeOrNull()) // Thay đổi loại media tại đây

                // Tạo MultipartBody.Part từ RequestBody
                val multipartBody = MultipartBody.Part.createFormData("avatar", file.name, requestBody)

                // Gửi MultipartBody.Part lên server thông qua API
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.updateAvatar(sharePrefer.getUserId(), multipartBody)
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