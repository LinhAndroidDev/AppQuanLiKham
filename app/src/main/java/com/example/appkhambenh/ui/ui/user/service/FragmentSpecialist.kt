package com.example.appkhambenh.ui.ui.user.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentSpecialistBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.user.service.adapter.SpecialAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentSpecialist : BaseFragment<FragmentSpecialistViewModel, FragmentSpecialistBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        onClickView()
    }

    private fun initUi() {
        binding.searchHeader.setHint("Chuyên khoa")
        binding.searchHeader.setBgGreySearch()
    }

    private fun onClickView() {
        binding.backHeader.setOnClickListener { this.back() }
    }

    override fun bindData() {
        super.bindData()

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.show.loading.isVisible = it
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getListSpecialist()
            viewModel.specialists.collect { special ->
                if (special != null) {
                    val adapter = SpecialAdapter()
                    adapter.items = special
                    binding.rcvSpecialist.adapter = adapter
                    adapter.onClickItem = {
                        replaceFragment(FragmentService(), R.id.changeIdExaminationService)
                    }
                    binding.searchHeader.keySearch = {
                        adapter.filter.filter(it)
                    }
                }
            }
        }
    }

    override fun onFragmentBack(): Boolean {
        return true
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentSpecialistBinding.inflate(inflater)
}