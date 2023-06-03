package com.example.appkhambenh.ui.ui.user.appointment

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appkhambenh.databinding.FragmentTimeWorkingBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.model.Time
import com.example.appkhambenh.ui.ui.user.appointment.adapter.WorkingTimeAdapter

@Suppress("DEPRECATION")
class FragmentTimeWorking : BaseFragment<WorkingTimeViewModel, FragmentTimeWorkingBinding>() {
    lateinit var workingTimeAdapter: WorkingTimeAdapter

    companion object{
        var listTime: ArrayList<Time> = arrayListOf()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun bindData() {
        super.bindData()

        val loading = ProgressDialog(requireActivity())
        loading.setTitle("Thông báo")
        loading.setMessage("Please wait...")
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner){
            if(it){
                loading.show()
            }else{
                loading.dismiss()
            }
        }

        viewModel.getWorkingDate()

        viewModel.listWorkingTimeLiveData.observe(viewLifecycleOwner){
            for(i in 0 until it.size){
                for(j in 0 until it[i].list_working.size){
                    listTime.add(Time(it[i].list_working[j].hour))
                }
            }
            workingTimeAdapter = WorkingTimeAdapter(requireActivity(),listTime)
            val gridLayout = GridLayoutManager(requireActivity(),3)
            binding.listTimeWorking.layoutManager = gridLayout
            binding.listTimeWorking.adapter = workingTimeAdapter
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentTimeWorkingBinding.inflate(inflater)
}