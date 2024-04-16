package com.example.appkhambenh.ui.ui.user.navigation.setting

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
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
import androidx.core.view.postDelayed
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityUpdateInformationBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.data.remote.model.ProfileModel
import com.example.appkhambenh.ui.ui.common.DialogRequestPermission
import com.example.appkhambenh.ui.ui.user.appointment.MakeAppointActivity
import com.example.appkhambenh.ui.ui.user.navigation.setting.adapter.InformationAdapter
import com.example.appkhambenh.ui.ui.user.navigation.setting.address.AddressActivity
import com.example.appkhambenh.ui.utils.DateUtils
import com.example.appkhambenh.ui.utils.PersonalInformation
import com.example.appkhambenh.ui.utils.setStyleTextAtPosition
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Suppress("DEPRECATION")
@AndroidEntryPoint
class UpdateInformationActivity :
    BaseActivity<UpdateInformationViewModel, ActivityUpdateInformationBinding>() {
    private val bottomSheetInformation by lazy { BottomSheetBehavior.from(binding.bottomSelectInfo.layoutSelect) }
    private var informationAdapter = InformationAdapter()
    private var isAddMember = false
    private val calendar by lazy { Calendar.getInstance() }

    companion object {
        const val REQUEST_SCAN = 2
        const val REQUEST_ADDRESS = 3
        const val REQUEST_CAMERA_PERMISSION = 100
        const val OPEN_INFORMATION_APP = 101
    }

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
            viewModel.getUserInfo()
            viewModel.infoUser.collect { user ->
                user?.name?.let { binding.edtName.setText(it) }
                user?.phoneNumber?.let { binding.edtPhone.setText(it) }
                user?.birthday?.let { binding.edtBirth.setText(DateUtils.convertLongToDate(it)) }
                user?.identification?.let { binding.edtCCCD.setText(it) }
                user?.address?.let { binding.tvAddress.text = it }
                user?.gender?.let {
                    if (it == 0) {
                        binding.males.isChecked = true
                    } else {
                        binding.females.isChecked = true
                    }
                }
            }
        }
    }

    private fun initUi() {
        intent.getIntExtra(MakeAppointActivity.ADD_MEMBER, 0).let {
            if (it > 0) isAddMember = true
        }
        initTitle()
        initBottomSelect()
        onClickView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onClickView() {
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
        binding.tvAddress.setOnClickListener {
            val intent = Intent(this@UpdateInformationActivity, AddressActivity::class.java)
            startActivityForResult(intent, REQUEST_ADDRESS)
        }
        binding.edtBirth.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    setDateWithText()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding.ethnic.setOnClickListener {
            informationAdapter.resetList(PersonalInformation.ethnics())
            initDataInfo(
                titleInfo = getString(R.string.ethnic),
                hint = getString(R.string.search_ethnic)
            )
            informationAdapter.onClickItem = {
                binding.txtEthnic.text = it
                bottomSheetInformation.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        binding.nationality.setOnClickListener {
            informationAdapter.resetList(PersonalInformation.nationality())
            initDataInfo(
                titleInfo = getString(R.string.nationality),
                hint = getString(R.string.search_nationality)
            )
            informationAdapter.onClickItem = {
                binding.txtNationality.text = it
                bottomSheetInformation.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        binding.job.setOnClickListener {
            informationAdapter.resetList(PersonalInformation.job())
            initDataInfo(
                titleInfo = getString(R.string.job),
                hint = getString(R.string.job)
            )
            informationAdapter.onClickItem = {
                binding.txtJob.text = it
                bottomSheetInformation.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        binding.complete.footView.setOnClickListener {
            callUpdateInformation()
        }
    }

    private fun callUpdateInformation() {
        with(binding) {
            val profile = ProfileModel(
                userName = edtName.text.toString(),
                userPhoneNumber = edtPhone.text.toString(),
                userBirthday = DateUtils.convertDateToLong(edtBirth.text.toString()),
                identification = edtCCCD.text.toString(),
                address = tvAddress.text.toString(),
                dan_toc = txtEthnic.text.toString(),
                quoc_tich = txtNationality.text.toString(),
                NgheNghip = txtJob.text.toString(),
                email = sharePrefer.getUserEmail(),
                userGender = if (binding.males.isChecked) 0 else 1
            )
            lifecycleScope.launch {
                viewModel.updateProfile(profile, sharePrefer.getUserId())
                viewModel.successful.collect {
                    if (it) this@UpdateInformationActivity.back()
                }
            }
        }
    }

    private fun setDateWithText() {
        val dateFormat = SimpleDateFormat(DateUtils.DAY_OF_YEAR, Locale.getDefault())
        binding.edtBirth.setText(dateFormat.format(calendar.time))
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
                    val dialog = DialogRequestPermission()
                    dialog.show(supportFragmentManager, "")
                    dialog.title = "Bạn đã từ chối quyền truy cập"
                    val note =
                        "Để truy cập lại camera, bạn cần mở Cài đặt --> Thông tin ứng dụng --> Quyền ứng dụng --> Máy ảnh --> Cho phép truy cập"
                    val spannable = SpannableString(note)
                    setStyleText(note, "Cài đặt", spannable)
                    setStyleText(note, "Thông tin ứng dụng", spannable)
                    setStyleText(note, "Quyền ứng dụng", spannable)
                    setStyleText(note, "Máy ảnh", spannable)
                    setStyleText(note, "Cho phép truy cập", spannable)
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

    private fun setStyleText(note: String, str: String, spannable: SpannableString) {
        setStyleTextAtPosition(note, str, StyleSpan(Typeface.BOLD), spannable)
        setStyleTextAtPosition(
            note,
            str,
            ForegroundColorSpan(getColor(R.color.txt_hint)),
            spannable
        )
    }

    private fun initDataInfo(titleInfo: String, hint: String) {
        bottomSheetInformation.state = BottomSheetBehavior.STATE_EXPANDED
        with(binding.bottomSelectInfo) {
            title.text = titleInfo
            search.hint = hint
            binding.root.postDelayed(400L) {
                rcvInformation.apply {
                    adapter = informationAdapter
                }
            }
            search.doOnTextChanged { text, _, _, _ ->
                informationAdapter.filter.filter(text)
            }
        }
    }

    private fun initTitle() {
        with(binding) {
            if (!isAddMember) {
                headerUpdateInfo.setTitle(getString(R.string.update_info))
                sharePrefer.getUserAvatar().let {
                    if (it.isNotEmpty()) {
                        Glide.with(this@UpdateInformationActivity)
                            .load(it)
                            .into(avatar)
                    }
                }
            } else {
                headerUpdateInfo.setTitle(getString(R.string.add_members))
                layoutInfo.visibility = View.GONE
            }

            titleName.title.text = getString(R.string.title_name)
            titlePhone.title.text = getString(R.string.title_phone)
            titleBirth.title.text = getString(R.string.title_date)
            titleCCCD.title.text = getString(R.string.CCCD)
            titleAddress.title.text = getString(R.string.title_address)
            titleEthnic.title.text = getString(R.string.ethnic)
            titleNationality.title.text = getString(R.string.nationality)
            titleJob.title.text = getString(R.string.job)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initBottomSelect() {
        bottomSheetInformation.isGestureInsetBottomIgnored = false
        bottomSheetInformation.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                binding.bottomSelectInfo.layoutCoverSheet.apply {
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        this.isEnabled = true
                        this.visibility = View.VISIBLE
                    } else {
                        this.visibility = View.GONE
                        binding.root.postDelayed(300L) {
                            resetBottomSheet()
                        }
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        binding.bottomSelectInfo.layoutMainSheet.setOnTouchListener { _, _ -> true }

        binding.bottomSelectInfo.layoutCoverSheet.setOnTouchListener { _, _ ->
            this.closeKeyboard()
            binding.bottomSelectInfo.layoutCoverSheet.isEnabled = false
            bottomSheetInformation.state = BottomSheetBehavior.STATE_COLLAPSED
            true
        }
    }

    private fun resetBottomSheet() {
        binding.bottomSelectInfo.search.apply {
            this.setText("")
            this.clearFocus()
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_SCAN -> {
                if (resultCode == Activity.RESULT_OK) {
                    val strInformation = data?.getStringExtra(ScanActivity.DATA_CCCD)
                    val information = strInformation?.split("|")?.toTypedArray()
                    information?.let {
                        binding.edtCCCD.setText(it[0])
                        binding.edtName.setText(it[2])
                        binding.tvAddress.text = it[5]

                        binding.edtBirth.setText(
                            SimpleDateFormat("dd/MM/yyyy").format(
                                SimpleDateFormat("ddMMyyyy").parse(
                                    it[3]
                                ) as Date
                            )
                        )

                        if (it[4] == "Nam") binding.males.isChecked =
                            true else binding.females.isChecked = true
                    }
                }
            }

            REQUEST_ADDRESS -> {
                if (resultCode == Activity.RESULT_OK) {
                    binding.tvAddress.text = data?.getStringExtra(AddressActivity.ADDRESS)
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