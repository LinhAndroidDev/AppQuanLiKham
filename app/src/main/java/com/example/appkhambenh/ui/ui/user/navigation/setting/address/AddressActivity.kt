package com.example.appkhambenh.ui.ui.user.navigation.setting.address

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityAddressBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.model.District
import com.example.appkhambenh.ui.model.Province
import com.example.appkhambenh.ui.model.Ward
import com.example.appkhambenh.ui.ui.user.navigation.setting.adapter.AddressAdapter
import com.example.appkhambenh.ui.ui.user.navigation.setting.adapter.DistrictAdapter
import com.example.appkhambenh.ui.ui.user.navigation.setting.adapter.WardAdapter

class AddressActivity : BaseActivity<AddressViewModel, ActivityAddressBinding>() {
    private var strAddress = ""
    private var address = arrayListOf<Province>()
    private var districts = arrayListOf<District>()
    private var wards = arrayListOf<Ward>()
    private var idDistrict = -1
    private var idWard = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    override fun bindData() {
        super.bindData()
        getDataProvince()
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityAddressBinding.inflate(inflater)

    @SuppressLint("NotifyDataSetChanged")
    private fun initUi() {
        binding.headerAddress.setTitle(getString(R.string.title_address))
        binding.headerAddress.visibleIconSearch()
        binding.headerAddress.setHintSearch(getString(R.string.search_address))
        binding.rbProvince.isChecked = true
        disableFootView()

        binding.footViewAddress.tvComplete.setOnClickListener {
            binding.nameRoad.text.let {
                if (it.isNotEmpty()) strAddress = "${it}, $strAddress"
            }
            val intent = intent.putExtra("address", strAddress)
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
                getDataDistrict(address, idDistrict)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getDataProvince() {
        viewModel.getProvince()
        viewModel.provinces.observe(this) { provinces ->
            address = provinces
            val addressAdapter = AddressAdapter(this@AddressActivity, provinces)
            binding.headerAddress.searchItem = {
                addressAdapter.filter.filter(it)
            }
            binding.rcvAddress.adapter = addressAdapter
            addressAdapter.onClickItem = { nameProvince ->
                idDistrict = provinces.indexOfFirst { it.name == nameProvince }
                districts.clear()
                for (district in provinces[idDistrict].districts!!.indices) {
                    districts.add(provinces[idDistrict].districts!![district])
                }
                getDataDistrict(provinces, idDistrict)
            }
        }
    }

    private fun getDataDistrict(provinces: ArrayList<Province>, idxDistrict: Int) {
        val districtAdapter = DistrictAdapter(this@AddressActivity, districts)
        binding.headerAddress.searchItem = {
            districtAdapter.filter.filter(it)
        }
        binding.rcvAddress.adapter = districtAdapter
        rbRegistered(
            rbRegistered = binding.rbProvince,
            name = provinces[idxDistrict].name,
            rbShow = binding.rbDistrict
        )

        districtAdapter.onClickItem = { nameDistrict ->
            idWard = provinces[idxDistrict].districts?.indexOfFirst { it.name == nameDistrict }!!
            wards.clear()
            for (ward in provinces[idxDistrict].districts?.get(idWard)?.wards!!.indices) {
                wards.add(provinces[idxDistrict].districts?.get(idWard)?.wards!![ward])
            }
            getDataWard(provinces, idxDistrict, idWard)
        }
    }

    private fun getDataWard(provinces: ArrayList<Province>, idxDis: Int, idxWard: Int) {
        val wardAdapter = WardAdapter(this@AddressActivity, wards)
        binding.headerAddress.searchItem = {
            wardAdapter.filter.filter(it)
        }
        binding.rcvAddress.adapter = wardAdapter
        rbRegistered(
            rbRegistered = binding.rbDistrict,
            name = provinces[idxDis].districts!![idxWard].name,
            rbShow = binding.rbWard
        )
        binding.headerAddress.clearSearch()

        wardAdapter.onClickItem = { nameWard ->
            rbRegistered(
                rbRegistered = binding.rbWard,
                name = nameWard
            )
            binding.nameRoad.visibility = View.VISIBLE
            binding.rcvAddress.visibility = View.GONE
            enableFootView()
            val pr = provinces[idxDis].name
            val dtr = provinces[idxDis].districts!![idxWard].name
            val w = nameWard

            strAddress = "$w, $dtr, $pr"
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