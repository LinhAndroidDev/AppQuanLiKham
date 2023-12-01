package com.example.appkhambenh.ui.ui.user.doctor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentSearchDoctorBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.user.doctor.adapter.DoctorAdapter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentSearchDoctor : BaseFragment<EmptyViewModel, FragmentSearchDoctorBinding>(){
    private val doctors by lazy { arrayListOf<Int>() }
    private val adapter by lazy { DoctorAdapter(doctors) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {

        binding.headerDoctor.visibleSearch()
        binding.headerDoctor.setTitle(getString(R.string.consulting_doctor))
        binding.headerDoctor.setHint(getString(R.string.search_doctor))

        lifecycleScope.launch  {
            delay(300L)
            withContext(Dispatchers.Main){
                listDoctor()
            }
        }
    }

    private fun listDoctor() {
        for (i in 0..10){
            doctors.add(1)
        }
        binding.rcvDoctor.adapter = adapter
        adapter.onClickItem = {
            if(it) {
                val intent = Intent(requireActivity(), InfoDoctorActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentSearchDoctorBinding.inflate(inflater)

    override fun onFragmentBack(): Boolean {
        return true
    }
}