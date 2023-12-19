package com.example.appkhambenh.ui.ui.user.medicine

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appkhambenh.databinding.ActivityMedicineBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.user.medicine.adapter.MedicineAdapter

class MedicineActivity : BaseActivity<MedicineViewModel, ActivityMedicineBinding>() {
    private lateinit var medicineAdapter: MedicineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun bindData() {
        super.bindData()

        viewModel.loadingLiveData.observe(this) { isLoading ->
            if (isLoading) loading.show() else loading.dismiss()
        }

        viewModel.getDataMedicine()

        viewModel.listMedicineLiveData.observe(this) {
            medicineAdapter = MedicineAdapter(this, it)
            val linear = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rcvMedicine.layoutManager = linear
            binding.rcvMedicine.adapter = medicineAdapter
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityMedicineBinding.inflate(inflater)
}