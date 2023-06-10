package com.example.appkhambenh.ui.ui.user.medicine

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityMedicineDetailBinding
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.model.Medicine
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class MedicineDetailActivity : BaseActivity<EmptyViewModel, ActivityMedicineDetailBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    private fun setStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    @SuppressLint("SetTextI18n")
    private fun initUi() {

        setStatusBar()

        val medicine = intent.getSerializableExtra("medicine") as Medicine

        Picasso.get().load(medicine.image)
            .placeholder(R.drawable.loadimage)
            .error(R.drawable.errorimage)
            .into(binding.imageDetailMedicine)

        binding.nameDetailMedicine.text = medicine.name

        binding.dateDetailMedicine.text = "Ngày nhập: " + medicine.update_date

        binding.detailInfoMedicine.text = medicine.detail

        binding.backMedicineDetail.setOnClickListener {
            onBackPressed()
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityMedicineDetailBinding.inflate(inflater)
}