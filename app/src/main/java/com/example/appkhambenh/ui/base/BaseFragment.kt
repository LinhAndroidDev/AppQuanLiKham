package com.example.appkhambenh.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appkhambenh.ui.utils.PreferenceUtil
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<V : BaseViewModel> : Fragment(),IonFragmentBack{
    lateinit var viewModel: V

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this)[(this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<V>]
            activity?.let {
                viewModel.mPreferenceUtil = PreferenceUtil(it)
            }

        bindData()
    }

    open fun bindData() {
        viewModel.errorApiLiveData.observe(viewLifecycleOwner){
            Toast.makeText(
                requireActivity(),
                it,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onFragmentBack(): Boolean {
        return false
    }
}