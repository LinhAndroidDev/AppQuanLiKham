package com.example.appkhambenh.ui.ui.user.navigation.setting.address

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityAddressBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.model.District
import com.example.appkhambenh.ui.model.Ward
import com.example.appkhambenh.ui.ui.user.navigation.setting.adapter.AddressAdapter
import com.example.appkhambenh.ui.ui.user.navigation.setting.adapter.DistrictAdapter
import com.example.appkhambenh.ui.ui.user.navigation.setting.adapter.WardAdapter

class AddressActivity : BaseActivity<AddressViewModel, ActivityAddressBinding>() {
    private var districts = arrayListOf<District>()
    private var wards = arrayListOf<Ward>()
    private var strAddress = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    override fun bindData() {
        super.bindData()
        getData()
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityAddressBinding.inflate(inflater)

    @SuppressLint("NotifyDataSetChanged")
    private fun initUi() {
        binding.headerAddress.setTitle(getString(R.string.title_address))
        binding.headerAddress.visibleSearch()
        binding.headerAddress.setHintSearch(getString(R.string.search_address))
        binding.rbProvince.isChecked = true

        disableFootView()

        binding.footViewAddress.tvComplete.setOnClickListener {
            val intent = intent.putExtra("address", strAddress)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData(){
        viewModel.getProvince()
        viewModel.provinces.observe(this) { provinces ->
            val addressAdapter = AddressAdapter(this@AddressActivity, provinces)
            binding.headerAddress.searchItem = {
                addressAdapter.filter.filter(it)
            }
            binding.rcvAddress.adapter = addressAdapter
            addressAdapter.onClickItem = { nameProvince ->
                var idxDistrict = -1
                for(i in provinces.indices){
                    if(provinces[i].name == nameProvince){
                        idxDistrict = i
                    }
                }
                for(district in provinces[idxDistrict].districts!!.indices){
                    districts.add(provinces[idxDistrict].districts!![district])
                }
                val districtAdapter = DistrictAdapter(this@AddressActivity, districts)
                binding.headerAddress.searchItem = {
                    districtAdapter.filter.filter(it)
                }
                binding.rcvAddress.adapter = districtAdapter
                binding.rbProvince.text = provinces[idxDistrict].name
                binding.rbProvince.setTextColor(getColor(R.color.black))
                binding.rbProvince.typeface = Typeface.DEFAULT_BOLD
                binding.rbDistrict.visibility = View.VISIBLE
                binding.rbDistrict.isChecked = true
                binding.headerAddress.clearSearch()

                districtAdapter.onClickItem = { nameDistrict ->
                    var idxWard = -1
                    for(i in provinces[idxDistrict].districts!!.indices){
                        if(provinces[idxDistrict].districts!![i].name == nameDistrict){
                            idxWard = i
                        }
                    }
                    for(ward in provinces[idxDistrict].districts?.get(idxWard)?.wards!!.indices){
                        wards.add(provinces[idxDistrict].districts?.get(idxWard)?.wards!![ward])
                    }
                    val wardAdapter = WardAdapter(this@AddressActivity, wards)
                    binding.headerAddress.searchItem = {
                        wardAdapter.filter.filter(it)
                    }
                    binding.rcvAddress.adapter = wardAdapter
                    binding.rbDistrict.text = provinces[idxDistrict].districts!![idxWard].name
                    binding.rbDistrict.setTextColor(getColor(R.color.black))
                    binding.rbDistrict.typeface = Typeface.DEFAULT_BOLD
                    binding.rbWard.visibility = View.VISIBLE
                    binding.rbWard.isChecked = true
                    binding.headerAddress.clearSearch()

                    wardAdapter.onClickItem = { nameWard ->
                        enableFootView()
                        val pr = provinces[idxDistrict].name
                        val dtr = provinces[idxDistrict].districts!![idxWard].name
                        val w = nameWard

                        strAddress = "$w, $dtr, $pr"
                    }
                }
            }
        }
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