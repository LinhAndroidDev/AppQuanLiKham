package com.example.appkhambenh.ui.ui.user.navigation.setting.address

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityAddressBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.model.Address
import com.example.appkhambenh.ui.ui.user.navigation.setting.adapter.AddressAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddressActivity : BaseActivity<AddressViewModel, ActivityAddressBinding>() {
    private var strAddress = ""
    private val addressAdapter by lazy { AddressAdapter() }
    private var strProvince = ""
    private var strDistrict = ""
    private var strWard = ""
    private var codeProvince = ""

    companion object {
        const val ADDRESS = "ADDRESS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    override fun bindData() {
        super.bindData()
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                delay(10L)
                viewModel.getDataAddress(this@AddressActivity)
                viewModel.successful.observe(this@AddressActivity) {
                    getDataProvince()
                }
            }
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityAddressBinding.inflate(inflater)

    @SuppressLint("NotifyDataSetChanged", "ClickableViewAccessibility")
    private fun initUi() {
        binding.headerAddress.setTitle(getString(R.string.title_address))
        binding.headerAddress.visibleIconSearch()
        binding.headerAddress.setHintSearch(getString(R.string.search_address))
        binding.rbProvince.isChecked = true
        disableFootView()

        binding.headerAddress.searchItem = {
            addressAdapter.filter.filter(it)
        }

        binding.footViewAddress.tvComplete.setOnClickListener {
            binding.nameRoad.text.let {
                if (it.isNotEmpty()) strAddress = "${it}, $strAddress"
            }
            val intent = intent.putExtra(ADDRESS, strAddress)
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.rbProvince.setOnCheckedChangeListener { _, b ->
            if (b) {
                resetAddress(binding.rbProvince, R.string.select_province)
                resetAddress(binding.rbDistrict, R.string.select_district)
                resetAddress(binding.rbWard, R.string.select_ward)
                binding.rbDistrict.visibility = View.GONE
                binding.rbWard.visibility = View.GONE
                getDataProvince()
            }
        }

        binding.rbDistrict.setOnCheckedChangeListener { _, b ->
            if (b) {
                resetAddress(binding.rbDistrict, R.string.select_district)
                resetAddress(binding.rbWard, R.string.select_ward)
                binding.rbWard.visibility = View.GONE
                getDataDistrict(codeProvince)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getDataProvince() {
        viewModel.address.let { address ->
            binding.rcvAddress.adapter = addressAdapter
            addressAdapter.resetList(address)
            addressAdapter.onClickItem = { provinceSelected ->
                rbRegistered(
                    rbRegistered = binding.rbProvince,
                    name = provinceSelected.name,
                    rbShow = binding.rbDistrict
                )
                strProvince = provinceSelected.name
                codeProvince = provinceSelected.code
                getDataDistrict(codeProvince)
            }
        }
    }

    private fun getDataDistrict(code: String) {
        viewModel.districts.filter {
            it.getDistrictByCodeProvince(code)
        }.let { districts ->
            addressAdapter.resetList(districts as ArrayList<Address>)
            addressAdapter.onClickItem = { districtSelected ->
                rbRegistered(
                    rbRegistered = binding.rbDistrict,
                    name = districtSelected.name,
                    rbShow = binding.rbWard
                )
                strDistrict = districtSelected.name
                getDataWard(districtSelected.code)
            }
        }
    }

    private fun getDataWard(code: String) {
        viewModel.wards.filter {
            it.getWardByCodeDistrict(code)
        }.let { wards ->
            addressAdapter.resetList(wards as ArrayList<Address>)
            binding.headerAddress.clearSearch()
            addressAdapter.onClickItem = { wardSelected ->
                rbRegistered(
                    rbRegistered = binding.rbWard,
                    name = wardSelected.name
                )
                strWard = wardSelected.name
                binding.nameRoad.visibility = View.VISIBLE
                binding.rcvAddress.visibility = View.GONE
                binding.rbWard.isChecked = false
                enableFootView()
                strAddress = "$strWard, $strDistrict, $strProvince"
            }
        }
    }

    private fun resetAddress(rb: RadioButton, name: Int) {
        rb.setText(name)
        rb.setTextColor(getColor(R.color.background))
        rb.typeface = Typeface.DEFAULT
        binding.nameRoad.visibility = View.GONE
        binding.rcvAddress.visibility = View.VISIBLE
        disableFootView()
    }

    private fun rbRegistered(
        rbRegistered: RadioButton,
        name: String,
        rbShow: RadioButton = RadioButton(this),
    ) {
        rbRegistered.text = name
        rbRegistered.setTextColor(getColor(R.color.black))
        rbRegistered.typeface = Typeface.DEFAULT_BOLD
        rbShow.visibility = View.VISIBLE
        rbShow.isChecked = true
        binding.headerAddress.clearSearch()
    }

    private fun disableFootView() {
        binding.footViewAddress.tvComplete.alpha = 0.5f
        binding.footViewAddress.tvComplete.isEnabled = false
    }

    private fun enableFootView() {
        binding.footViewAddress.tvComplete.alpha = 1f
        binding.footViewAddress.tvComplete.isEnabled = true
    }
}