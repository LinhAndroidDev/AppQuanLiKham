package com.example.appkhambenh.ui.ui.user.medicine

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewpager2.widget.ViewPager2
import com.example.appkhambenh.databinding.ActivityMedicineDetailBinding
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.user.medicine.adapter.ImageDetailMedicineAdapter
import com.example.appkhambenh.ui.ui.user.medicine.adapter.MedicineAdapter
import com.example.appkhambenh.ui.utils.strikethroughText

class MedicineDetailActivity : BaseActivity<EmptyViewModel, ActivityMedicineDetailBinding>() {
    private var numberProduct = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
        onClickView()
    }

    private fun onClickView() {
        binding.btnPlus.setOnClickListener {
            numberProduct++
            binding.edtNumberProduct.setText("$numberProduct")
            binding.btnMinus.enableBtn()
            binding.edtNumberProduct.clearFocus()
            this.closeKeyboard()
        }

        binding.btnMinus.setOnClickListener {
            if(numberProduct > 1) {
                numberProduct--
                binding.edtNumberProduct.setText("$numberProduct")
                if(numberProduct == 1) binding.btnMinus.disableBtn()
                binding.edtNumberProduct.clearFocus()
                this.closeKeyboard()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initUi() {
        binding.header.setTitle("Chi tiết sản phẩm")
        binding.btnPlus.apply {
            enableBtn()
            setIconPlus()
        }
        binding.btnMinus.disableBtn()
        val adapter = ImageDetailMedicineAdapter(this)
        val medicines = arrayListOf(
            "https://iris-buck.s3.ap-southeast-1.amazonaws.com/production/17ebafc6-2a6c-4f15-bb55-20429cb8cb46.png",
            "https://iris-buck.s3.ap-southeast-1.amazonaws.com/production/4e81613e-007c-441c-aff6-633b8ea33c7c.png"
        )
        adapter.items = medicines
        binding.pagerMedicine.adapter = adapter
        binding.pagerMedicine.currentItem = 0
        binding.tvIndexPager.text =
            "${binding.pagerMedicine.currentItem + 1}/${medicines.size}"
        binding.pagerMedicine.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                binding.tvIndexPager.text =
                    "${binding.pagerMedicine.currentItem + 1}/${medicines.size}"
            }
        })
        binding.priceReduce.text = strikethroughText("85.000 đ")
        initListRelatedProduct()
    }

    private fun initListRelatedProduct() {
        binding.rcvRelatedProducts.adapter = MedicineAdapter(this)
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityMedicineDetailBinding.inflate(inflater)
}