package com.example.appkhambenh.ui.ui.user.medicine

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.example.appkhambenh.databinding.ActivityMedicineBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.user.medicine.adapter.CatalogAdapter
import com.example.appkhambenh.ui.ui.user.medicine.adapter.MedicineAdapter

class MedicineActivity : BaseActivity<MedicineViewModel, ActivityMedicineBinding>() {
    private val adapterMedicine = MedicineAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.searchHeader.setBgGreySearch()
        binding.searchHeader.setHint("Tìm kiếm sản phẩm")
        binding.rcvCatalog.adapter = CatalogAdapter(this)
        binding.rcvMedicine.adapter = adapterMedicine
        adapterMedicine.onClickItem = {
            val intent = Intent(this, MedicineDetailActivity::class.java)
            startActivity(intent)
        }

        binding.backHeader.setOnClickListener { this.back() }
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityMedicineBinding.inflate(inflater)
}