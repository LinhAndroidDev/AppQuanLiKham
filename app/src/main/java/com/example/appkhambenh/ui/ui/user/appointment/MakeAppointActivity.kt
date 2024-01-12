package com.example.appkhambenh.ui.ui.user.appointment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityMakeAppointBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.model.Member
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.appointment.adapter.ImageCameraAdapter
import com.example.appkhambenh.ui.ui.user.appointment.adapter.MemberAppointAdapter
import com.example.appkhambenh.ui.ui.user.doctor.InfoDoctorActivity
import com.example.appkhambenh.ui.ui.user.navigation.setting.UpdateInformationActivity

class MakeAppointActivity : BaseActivity<EmptyViewModel, ActivityMakeAppointBinding>() {
    private var uris = arrayListOf<Uri>()
    private lateinit var imageCameraAdapter: ImageCameraAdapter
    private lateinit var memberAdapter: MemberAppointAdapter

    companion object {
        const val REQUEST_CODE_MULTI_PICTURE = 1
        const val SELECT_MULTI_PICTURE = "SELECT_MULTI_PICTURE"
        const val REQUEST_CODE_EDIT_TIME = 2
        const val EDIT_TIME = "EDIT_TIME"
        const val ADD_MEMBER = "ADD_MEMBER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    @SuppressLint("ClickableViewAccessibility", "NotifyDataSetChanged")
    private fun initUi() {
        binding.header.setTitle(getString(R.string.make_an_appoint))
        binding.footView.tvComplete.text = getString(R.string.contine)

        binding.hour.text = intent.getStringExtra(OnlineConsultationActivity.HOUR_ONLINE_CONSULT)
            ?: intent.getStringExtra(InfoDoctorActivity.HOUR_INFORMATION_DOCTOR)

        val members = arrayListOf(
            Member(sharePrefer.getUserName(), sharePrefer.getUserAvatar()),
            Member("Nguyễn Hữu Quang", ""),
            Member("Trần Đức Ngọc", ""),
            Member("Phan Văn Hùng", ""),
            Member("Nguyễn Thế Dương", ""),
            Member("", "")
        )
        memberAdapter = MemberAppointAdapter(this@MakeAppointActivity, members)
        memberAdapter.addMember = {
            val intent = Intent(this@MakeAppointActivity, UpdateInformationActivity::class.java)
            intent.putExtra(ADD_MEMBER, 1)
            startActivity(intent)
        }
        binding.rcvMember.apply {
            layoutManager =
                LinearLayoutManager(this@MakeAppointActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = memberAdapter
        }

        binding.scrollAppoint.setOnTouchListener { _, _ ->
            closeKeyboard()
            binding.edtReason.clearFocus()
            false
        }

        binding.editTime.setOnClickListener {
            val intent = Intent(this@MakeAppointActivity, OnlineConsultationActivity::class.java)
            intent.putExtra(EDIT_TIME, 1)
            startActivityForResult(intent, REQUEST_CODE_EDIT_TIME)
        }

        binding.edtReason.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= binding.edtReason.right - binding.edtReason.compoundDrawables[2].bounds.width()) {
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(
                        Intent.createChooser(intent, SELECT_MULTI_PICTURE),
                        REQUEST_CODE_MULTI_PICTURE
                    )
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }

        imageCameraAdapter = ImageCameraAdapter(uris, this@MakeAppointActivity)
        binding.rcvImageCamera.adapter = imageCameraAdapter
        imageCameraAdapter.deleteImage = {
            uris.removeAt(it)
            imageCameraAdapter.notifyDataSetChanged()
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityMakeAppointBinding.inflate(layoutInflater)

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                REQUEST_CODE_MULTI_PICTURE -> {
                    if (data.clipData != null) {
                        val count: Int = data.clipData!!.itemCount
                        if (count + uris.size > 5) {
                            show(getString(R.string.choose_a_maximum_of_5_photos))
                        } else {
                            for (i in 0 until count) {
                                uris.add(data.clipData!!.getItemAt(i).uri)
                            }
                            imageCameraAdapter.notifyDataSetChanged()
                        }
                    }
                }

                REQUEST_CODE_EDIT_TIME -> {
                    binding.hour.text = data.getStringExtra(OnlineConsultationActivity.TIME_EDIT)
                }
            }
        }
    }
}