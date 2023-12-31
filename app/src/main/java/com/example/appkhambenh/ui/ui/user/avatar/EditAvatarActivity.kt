package com.example.appkhambenh.ui.ui.user.avatar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityEditAvatarBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.user.HomeActivity
import com.example.appkhambenh.ui.ui.user.home.FragmentHome
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class EditAvatarActivity : BaseActivity<UploadImageViewModel, ActivityEditAvatarBinding>() {
    private var imgUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    override fun bindData() {
        super.bindData()
        viewModel.loadingLiveData.observe(this) {
            if (!it) loading.dismiss()
        }

        viewModel.isSuccessfulLiveData.observe(this) {
            if (it) {
                show(getString(R.string.update_avatar_successful))
                val intent = Intent(this@EditAvatarActivity, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    private fun initUi() {

        overridePendingTransition(R.anim.enter_avt, R.anim.exit_avt)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        val strUri: String = intent.getStringExtra(FragmentHome.URI_AVATAR).toString()
        imgUri = Uri.parse(strUri)
        binding.imgAvatarEdit.setImageURI(imgUri)

        binding.txtUpdateAvatar.setOnClickListener {
            uploadImage()
        }
    }

    private fun uploadImage() {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storage = FirebaseStorage.getInstance().getReference("images/$fileName")
        loading.show()

        storage.putFile(imgUri!!).addOnSuccessListener {
            val storageRef = Firebase.storage.reference.child("images/$fileName")
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                sendToServer(uri.toString())
            }.addOnFailureListener {
                Toast.makeText(this, " Fail", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Lỗi ko tải lên được", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendToServer(avatar: String) {
        viewModel.uploadImage(
            convertToRequestBody(sharePrefer.getUserId().toString()),
            convertToRequestBody(avatar)
        )
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityEditAvatarBinding.inflate(inflater)

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.push_avt, R.anim.pop_avt)
    }
}