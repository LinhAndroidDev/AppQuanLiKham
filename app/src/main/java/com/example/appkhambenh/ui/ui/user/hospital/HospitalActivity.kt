package com.example.appkhambenh.ui.ui.user.hospital

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityHospitalBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.user.hospital.adapter.HospitalAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HospitalActivity : BaseActivity<HospitalViewModel, ActivityHospitalBinding>() {
    private lateinit var hospitalAdapter: HospitalAdapter

    companion object {
        const val OBJECT_HOSPITAL = "OBJECT_HOSPITAL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    override fun bindData() {
        super.bindData()

        viewModel.loading.observe(this) {
            binding.show.loading.visibility = if(it) View.VISIBLE else View.GONE
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getListHospital()

            viewModel.hospitalMutableLiveData.observe(this@HospitalActivity) {
                hospitalAdapter = HospitalAdapter()
                hospitalAdapter.items = it
                binding.rcvHospital.adapter = hospitalAdapter
                binding.headerHospital.searchItem = {
                    hospitalAdapter.filter.filter(it)
                }
                hospitalAdapter.onClickItem = { hospital ->
                    val intent = Intent(this@HospitalActivity, InfoHospitalActivity::class.java)
                    intent.putExtra(OBJECT_HOSPITAL, hospital)
                    startActivity(intent)
                }
            }
        }
    }

    private fun initUi() {
        binding.headerHospital.visibleSearch()
        binding.headerHospital.setTitle(getString(R.string.csyt))
        binding.headerHospital.setHintSearch(getString(R.string.search_csyt))
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityHospitalBinding.inflate(inflater)
}