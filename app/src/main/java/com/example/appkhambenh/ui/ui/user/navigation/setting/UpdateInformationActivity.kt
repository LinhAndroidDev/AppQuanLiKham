package com.example.appkhambenh.ui.ui.user.navigation.setting

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityUpdateInformationBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.navigation.setting.adapter.InformationAdapter
import com.example.appkhambenh.ui.ui.user.navigation.setting.address.AddressActivity
import com.example.appkhambenh.ui.utils.Address
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date

@Suppress("DEPRECATION")
class UpdateInformationActivity : BaseActivity<EmptyViewModel, ActivityUpdateInformationBinding>() {

    private val bottomSheetInformation by lazy { BottomSheetBehavior.from(binding.bottomSelectInfo.layoutSelect) }
    private lateinit var adapter: InformationAdapter

    companion object {
        const val REQUEST_SCAN = 2
        const val REQUEST_ADDRESS = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    private fun initUi() {

        initTitle()

        initBottomSelect()

        val avatar = viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.USER_AVATAR, "").toString()
        if(avatar.isNotEmpty()) {
            Picasso.get().load(avatar)
                .into(binding.avatar)
        }

        binding.bottomSelectInfo.search.doOnTextChanged { text, _, _, _ ->
            adapter.filter.filter(text)
        }

        binding.headerUpdateInfo.setTitle(getString(R.string.update_info))

        binding.scanQr.setOnClickListener {
            val intent = Intent(this@UpdateInformationActivity, ScanActivity::class.java)
            startActivityForResult(intent, REQUEST_SCAN)
        }

        binding.scanCCCD.setOnClickListener {
            val intent = Intent(this@UpdateInformationActivity, ScanActivity::class.java)
            startActivityForResult(intent, REQUEST_SCAN)
        }

        binding.address.setOnClickListener {
            val intent = Intent(this@UpdateInformationActivity, AddressActivity::class.java)
            startActivityForResult(intent, REQUEST_ADDRESS)
        }

        binding.ethnic.setOnClickListener {
            bottomSheetInformation.state = BottomSheetBehavior.STATE_EXPANDED
            binding.bottomSelectInfo.title.text = getString(R.string.ethnic)
            binding.bottomSelectInfo.search.hint = getString(R.string.search_ethnic)

            adapter = InformationAdapter(Address.ethnics())
            binding.bottomSelectInfo.rcvInformation.adapter = adapter
            adapter.onClickItem = {
                binding.txtEthnic.text = it
                binding.bottomSelectInfo.search.setText("")
                binding.bottomSelectInfo.search.clearFocus()
                bottomSheetInformation.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        binding.nationality.setOnClickListener {
            bottomSheetInformation.state = BottomSheetBehavior.STATE_EXPANDED
            binding.bottomSelectInfo.title.text = getString(R.string.nationality)
            binding.bottomSelectInfo.search.hint = getString(R.string.search_nationality)

            adapter = InformationAdapter(Address.nationality())
            binding.bottomSelectInfo.rcvInformation.adapter = adapter
            adapter.onClickItem = {
                binding.txtNationality.text = it
                binding.bottomSelectInfo.search.setText("")
                binding.bottomSelectInfo.search.clearFocus()
                bottomSheetInformation.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        binding.job.setOnClickListener {
            bottomSheetInformation.state = BottomSheetBehavior.STATE_EXPANDED
            binding.bottomSelectInfo.title.text = getString(R.string.job)
            binding.bottomSelectInfo.search.hint = getString(R.string.search_job)

            adapter = InformationAdapter(Address.job())
            binding.bottomSelectInfo.rcvInformation.adapter = adapter
            adapter.onClickItem = {
                binding.txtJob.text = it
                binding.bottomSelectInfo.search.setText("")
                binding.bottomSelectInfo.search.clearFocus()
                bottomSheetInformation.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun initTitle() {
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

    @SuppressLint("SimpleDateFormat")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_SCAN -> {
                if (resultCode == Activity.RESULT_OK) {
                    val strInformation = data?.getStringExtra("results")
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
                    binding.address.text = data?.getStringExtra("address")
                }
            }
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityUpdateInformationBinding.inflate(inflater)

    override fun onBackPressed() {
        closeKeyboard()

        if (bottomSheetInformation.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetInformation.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            super.onBackPressed()
        }
    }
}