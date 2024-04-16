package com.example.appkhambenh.ui.ui.user.doctor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivitySearchDoctorBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.user.doctor.adapter.DoctorAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchDoctorActivity : BaseActivity<SearchDoctorViewModel, ActivitySearchDoctorBinding>() {
    private val adapter by lazy { DoctorAdapter() }
    companion object {
        const val OBJECT_DOCTOR = "OBJECT_DOCTOR"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    private fun initUi() {
        binding.headerDoctor.apply {
            visibleSearch()
            setTitle(getString(R.string.consulting_doctor))
            setHintSearch(getString(R.string.search_doctor))
        }
    }

    override fun bindData() {
        super.bindData()
        viewModel.loading.observe(this) {
            binding.show.loading.isVisible = it
        }
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getListDoctor()
            viewModel.doctors.collect { doctors ->
                if (doctors != null) {
                    adapter.items = doctors
                    binding.rcvDoctor.adapter = adapter
                    binding.headerDoctor.searchItem = {
                        adapter.filter.filter(it)
                    }
                    adapter.onClickItem = {
                        val intent = Intent(this@SearchDoctorActivity, InfoDoctorActivity::class.java)
                        intent.putExtra(OBJECT_DOCTOR, it)
                        startActivity(intent)
                    }
                }

            }
        }
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivitySearchDoctorBinding.inflate(inflater)
}