package com.example.appkhambenh.ui.ui.user.navigation.setting

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityUpdateInformationBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.common.DialogCustom
import com.example.appkhambenh.ui.ui.user.appointment.MakeAppointActivity
import com.example.appkhambenh.ui.ui.user.navigation.setting.adapter.InformationAdapter
import com.example.appkhambenh.ui.ui.user.navigation.setting.address.AddressActivity
import com.example.appkhambenh.ui.utils.PersonalInformation
import com.example.appkhambenh.ui.utils.setStyleTextAtPosition
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date


@Suppress("DEPRECATION")
class UpdateInformationActivity : BaseActivity<EmptyViewModel, ActivityUpdateInformationBinding>() {

    private val bottomSheetInformation by lazy { BottomSheetBehavior.from(binding.bottomSelectInfo.layoutSelect) }
    private var informationAdapter = InformationAdapter(arrayListOf(""), "", this)
    private var isAddMember = false

    companion object {
        const val REQUEST_SCAN = 2
        const val REQUEST_ADDRESS = 3
        const val ETHNICS = "ETHNICS"
        const val NATIONALITY = "NATIONALITY"
        const val JOB = "JOB"
        const val REQUEST_CAMERA_PERMISSION = 100
        const val OPEN_INFORMATION_APP = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initUi() {

        intent.getIntExtra(MakeAppointActivity.ADD_MEMBER, 0).let {
            if (it > 0) isAddMember = true
        }

        initTitle()

        initBottomSelect()

        binding.scrollView.setOnTouchListener { _, _ ->
            super.closeKeyboard()
            false
        }

        binding.scanQr.setOnClickListener {
            startScanCCCD()
        }

        binding.scanCCCD.setOnClickListener {
            startScanCCCD()
        }

        binding.address.setOnClickListener {
            val intent = Intent(this@UpdateInformationActivity, AddressActivity::class.java)
            startActivityForResult(intent, REQUEST_ADDRESS)
        }

        binding.ethnic.setOnClickListener {
            informationAdapter.clearList()
            informationAdapter = InformationAdapter(PersonalInformation.ethnics(), ETHNICS, this)
            initDataInfo(
                title = getString(R.string.ethnic),
                hint = getString(R.string.search_ethnic)
            )
            informationAdapter.onClickItem = {
                binding.txtEthnic.text = it
                bottomSheetInformation.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        binding.nationality.setOnClickListener {
            informationAdapter.clearList()
            informationAdapter = InformationAdapter(PersonalInformation.nationality(), NATIONALITY, this)
            initDataInfo(
                title = getString(R.string.nationality),
                hint = getString(R.string.search_nationality)
            )
            informationAdapter.onClickItem = {
                binding.txtNationality.text = it
                bottomSheetInformation.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        binding.job.setOnClickListener {
            informationAdapter.clearList()
            informationAdapter = InformationAdapter(PersonalInformation.job(), JOB, this)
            initDataInfo(
                title = getString(R.string.job),
                hint = getString(R.string.job)
            )
            informationAdapter.onClickItem = {
                binding.txtJob.text = it
                bottomSheetInformation.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun startScanCCCD() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            when (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.CAMERA
            )) {
                false -> {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.CAMERA),
                        REQUEST_CAMERA_PERMISSION
                    )
                }

                else -> {
                    val dialog = DialogCustom()
                    dialog.show(supportFragmentManager, "")
                    dialog.title = "Bạn đã từ chối quyền truy cập"
                    val note =
                        "Để truy cập lại camera, bạn cần mở Cài đặt --> -Thông tin ứng dụng -> Quyền ứng dụng --> Máy ảnh --> Cho phép truy cập"
                    val spannable = SpannableString(note)
                    setStyleTextAtPosition(note, "Cài đặt", StyleSpan(Typeface.BOLD), spannable)
                    setStyleTextAtPosition(
                        note,
                        "Cài đặt",
                        ForegroundColorSpan(getColor(R.color.txt_hint)),
                        spannable
                    )
                    setStyleTextAtPosition(
                        note,
                        "Thông tin ứng dụng",
                        StyleSpan(Typeface.BOLD),
                        spannable
                    )
                    setStyleTextAtPosition(
                        note,
                        "Thông tin ứng dụng",
                        ForegroundColorSpan(getColor(R.color.txt_hint)),
                        spannable
                    )
                    setStyleTextAtPosition(
                        note,
                        "Quyền ứng dụng",
                        StyleSpan(Typeface.BOLD),
                        spannable
                    )
                    setStyleTextAtPosition(
                        note,
                        "Quyền ứng dụng",
                        ForegroundColorSpan(getColor(R.color.txt_hint)),
                        spannable
                    )
                    setStyleTextAtPosition(note, "Máy ảnh", StyleSpan(Typeface.BOLD), spannable)
                    setStyleTextAtPosition(
                        note,
                        "Máy ảnh",
                        ForegroundColorSpan(getColor(R.color.txt_hint)),
                        spannable
                    )
                    setStyleTextAtPosition(
                        note,
                        "Cho phép truy cập",
                        StyleSpan(Typeface.BOLD),
                        spannable
                    )
                    setStyleTextAtPosition(
                        note,
                        "Cho phép truy cập",
                        ForegroundColorSpan(getColor(R.color.txt_hint)),
                        spannable
                    )
                    dialog.message = spannable
                    dialog.yes = {
                        val intent =
                            Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivityForResult(intent, OPEN_INFORMATION_APP)
                        dialog.dismiss()
                    }
                    dialog.no = {
                        dialog.dismiss()
                    }
                }
            }
        } else {
            val intent = Intent(this@UpdateInformationActivity, ScanActivity::class.java)
            startActivityForResult(intent, REQUEST_SCAN)
        }
    }

    private fun initDataInfo(title: String, hint: String) {
        bottomSheetInformation.state = BottomSheetBehavior.STATE_EXPANDED
        binding.bottomSelectInfo.title.text = title
        binding.bottomSelectInfo.search.hint = hint
        lifecycleScope.launch {
            delay(400L)
            withContext(Dispatchers.Main) {
                binding.bottomSelectInfo.rcvInformation.apply {
                    adapter = informationAdapter
                }
            }
        }
        binding.bottomSelectInfo.search.doOnTextChanged { text, _, _, _ ->
            informationAdapter.filter.filter(text)
        }
    }

    private fun initTitle() {
        if (!isAddMember) {
            binding.headerUpdateInfo.setTitle(getString(R.string.update_info))
            sharePrefer.getUserAvatar().let {
                if (it.isNotEmpty()) {
                    Glide.with(this)
                        .load(it)
                        .into(binding.avatar)
                }
            }
        } else {
            binding.headerUpdateInfo.setTitle(getString(R.string.add_members))
            binding.layoutInfo.visibility = View.GONE
        }

        binding.titleName.title.text = getString(R.string.title_name)
        binding.titlePhone.title.text = getString(R.string.title_phone)
        binding.titleBirth.title.text = getString(R.string.title_date)
        binding.titleCCCD.title.text = getString(R.string.CCCD)
        binding.titleAddress.title.text = getString(R.string.title_address)
        binding.titleEthnic.title.text = getString(R.string.ethnic)
        binding.titleNationality.title.text = getString(R.string.nationality)
        binding.titleJob.title.text = getString(R.string.job)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initBottomSelect() {
        bottomSheetInformation.isGestureInsetBottomIgnored = false
        bottomSheetInformation.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.bottomSelectInfo.layoutCoverSheet.isEnabled = true
                    binding.bottomSelectInfo.layoutCoverSheet.visibility = View.VISIBLE
                } else {
                    binding.bottomSelectInfo.layoutCoverSheet.visibility = View.GONE
                    lifecycleScope.launch {
                        delay(300L)
                        resetBottomSheet()
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        binding.bottomSelectInfo.layoutMainSheet.setOnTouchListener { _, _ -> true }

        binding.bottomSelectInfo.layoutCoverSheet.setOnTouchListener { _, _ ->
            binding.bottomSelectInfo.layoutCoverSheet.isEnabled = false
            bottomSheetInformation.state = BottomSheetBehavior.STATE_COLLAPSED
            true
        }
    }

    private fun resetBottomSheet() {
        binding.bottomSelectInfo.search.setText("")
        binding.bottomSelectInfo.search.clearFocus()
    }

    @SuppressLint("SimpleDateFormat")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_SCAN -> {
                if (resultCode == Activity.RESULT_OK) {
                    val strInformation = data?.getStringExtra(ScanActivity.DATA_CCCD)
                    val information = strInformation?.split("|")?.toTypedArray()
                    binding.cccd.setText(information!![0])
                    binding.name.setText(information[2])
                    binding.address.text = information[5]

                    val format = SimpleDateFormat("dd/MM/yyyy")
                    binding.birth.setText(
                        format.format(
                            SimpleDateFormat("ddMMyyyy").parse(
                                information[3]
                            ) as Date
                        )
                    )

                    if (information[4] == getString(R.string.males)) binding.males.isChecked =
                        true else binding.females.isChecked = true
                }
            }

            REQUEST_ADDRESS -> {
                if (resultCode == Activity.RESULT_OK) {
                    binding.address.text = data?.getStringExtra(AddressActivity.ADDRESS)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(this@UpdateInformationActivity, ScanActivity::class.java)
                    startActivityForResult(intent, REQUEST_SCAN)
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityUpdateInformationBinding.inflate(inflater)

    override fun onBackPressed() {
        super.closeKeyboard()

        if (bottomSheetInformation.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetInformation.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            super.onBackPressed()
        }
    }
}