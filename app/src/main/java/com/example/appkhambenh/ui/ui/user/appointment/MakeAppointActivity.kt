package com.example.appkhambenh.ui.ui.user.appointment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityMakeAppointBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.data.remote.model.BookAppointmentModel
import com.example.appkhambenh.ui.data.remote.model.DoctorModel
import com.example.appkhambenh.ui.data.remote.model.HourModel
import com.example.appkhambenh.ui.model.Member
import com.example.appkhambenh.ui.ui.user.appointment.adapter.ImageCameraAdapter
import com.example.appkhambenh.ui.ui.user.appointment.adapter.MemberAppointAdapter
import com.example.appkhambenh.ui.ui.user.doctor.InfoDoctorActivity
import com.example.appkhambenh.ui.ui.user.manage_appointment.ExaminationScheduleActivity
import com.example.appkhambenh.ui.ui.user.navigation.setting.UpdateInformationActivity
import com.example.appkhambenh.ui.utils.DateUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MakeAppointActivity : BaseActivity<MakeAppointViewModel, ActivityMakeAppointBinding>() {
    private var uris = arrayListOf<Uri>()
    private lateinit var imageCameraAdapter: ImageCameraAdapter
    private lateinit var memberAdapter: MemberAppointAdapter
    private var doctorModel: DoctorModel? = null
    private var hourModel: HourModel? = null

    companion object {
        const val REQUEST_CODE_MULTI_PICTURE = 1
        const val REQUEST_CODE_EDIT_TIME = 2
        const val SELECT_MULTI_PICTURE = "SELECT_MULTI_PICTURE"
        const val EDIT_TIME = "EDIT_TIME"
        const val ADD_MEMBER = "ADD_MEMBER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    override fun bindData() {
        super.bindData()

        viewModel.loading.observe(this) {
            if(it) loading.show() else loading.dismiss()
        }

        lifecycleScope.launch {
            viewModel.successful.collect { isSuccess ->
                if(isSuccess) {
                    val intent = Intent(this@MakeAppointActivity, ExaminationScheduleActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility", "NotifyDataSetChanged")
    private fun initUi() {
        binding.header.setTitle(getString(R.string.make_an_appoint))
        binding.footView.tvComplete.text = getString(R.string.contine)

//        binding.hour.text = intent.getStringExtra(OnlineConsultationActivity.HOUR_ONLINE_CONSULT)
//            ?: intent.getStringExtra(InfoDoctorActivity.HOUR_INFORMATION_DOCTOR)

        with(intent) {
            getParcelableExtra<HourModel>(InfoDoctorActivity.HOUR_WORKING_DOCTOR)?.let {
                hourModel = it
                binding.tvHour.text = it.hour
            }

            getStringExtra(InfoDoctorActivity.TIME_WORKING_DOCTOR)?.let {
                binding.tvDate.text = it
            }

            getParcelableExtra<DoctorModel>(InfoDoctorActivity.INFORMATION_DOCTOR)?.let {
                doctorModel = it
                binding.tvNameDoctor.text = it.name
            }
        }

        initMembers()
        initImageSympton()

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
                    val intent = Intent().apply {
                        type = "image/*"
                        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                        action = Intent.ACTION_GET_CONTENT
                    }
                    startActivityForResult(
                        Intent.createChooser(intent, SELECT_MULTI_PICTURE),
                        REQUEST_CODE_MULTI_PICTURE
                    )
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }

        binding.footView.tvComplete.setOnClickListener {
            binding.edtReason.text.toString().let {
                if(it.isEmpty()) {
                    show("Bạn chưa nhập triệu chứng bệnh")
                } else {
                    val bookAppointmentModel = BookAppointmentModel(
                        address = sharePrefer.getUserAddress(),
                        birthday = DateUtils.convertDateToLong(sharePrefer.getUserBirth()),
                        day = DateUtils.convertDateToLong(binding.tvDate.text.toString()),
                        doctorId = doctorModel?.id,
                        email = sharePrefer.getUserEmail(),
                        hospitalId = doctorModel?.hospitalId,
                        identification = sharePrefer.getIdentification(),
                        name = sharePrefer.getUserName(),
                        phone = sharePrefer.getUserPhone(),
                        reason = binding.edtReason.text.toString(),
                        service = null,
                        time = hourModel?.id
                    )
                    viewModel.createBooking(bookAppointmentModel = bookAppointmentModel)
                }
            }
        }
    }

    private fun initMembers() {
        val members = arrayListOf(
            Member(sharePrefer.getUserName(), sharePrefer.getUserAvatar()),
            Member("Trần Đức Ngọc", ""),
            Member("Phan Văn Hùng", ""),
            Member("Nguyễn Thế Dương", ""),
            Member("", "")
        )
        memberAdapter = MemberAppointAdapter(this@MakeAppointActivity).apply {
            items = members
            binding.rcvMember.adapter = this
            addMember = {
                val intent = Intent(this@MakeAppointActivity, UpdateInformationActivity::class.java)
                intent.putExtra(ADD_MEMBER, 1)
                startActivity(intent)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initImageSympton() {
        imageCameraAdapter = ImageCameraAdapter(this@MakeAppointActivity).apply {
            items = uris
            binding.rcvImageCamera.adapter = this
            deleteImage = {
                uris.removeAt(it)
                imageCameraAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityMakeAppointBinding.inflate(layoutInflater)

    @Deprecated("Deprecated in Java")
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
                    binding.tvHour.text = data.getStringExtra(OnlineConsultationActivity.TIME_EDIT)
                }
            }
        }
    }
}