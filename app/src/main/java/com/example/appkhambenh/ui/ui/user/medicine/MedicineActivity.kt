package com.example.appkhambenh.ui.ui.user.medicine

import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityMedicineBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.user.medicine.adapter.MedicineAdapter

@Suppress("DEPRECATION")
class MedicineActivity : BaseActivity<MedicineViewModel, ActivityMedicineBinding>() {
    lateinit var medicineAdapter: MedicineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBar()
    }

    override fun bindData() {
        super.bindData()

        val loadData = ProgressDialog(this)
        loadData.setTitle("Thông báo")
        loadData.setMessage("Please wait...")

        viewModel.loadingLiveData.observe(this, Observer { isLoading->
            if(isLoading){
                loadData.show()
            }else{
               loadData.dismiss()
            }
        })

        viewModel.getDataMedicine()

        viewModel.listMedicineLiveData.observe(this, Observer{
            medicineAdapter = MedicineAdapter(this, it)
            val linear = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rcvMedicine.layoutManager = linear
            binding.rcvMedicine.adapter = medicineAdapter
        })
    }

    private fun setStatusBar() {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        //  set status text dark
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityMedicineBinding.inflate(inflater)
}