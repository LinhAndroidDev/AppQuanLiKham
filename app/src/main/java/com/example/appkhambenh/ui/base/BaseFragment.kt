package com.example.appkhambenh.ui.base

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import androidx.viewbinding.ViewBinding
import com.example.appkhambenh.ui.utils.PreferenceUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.reflect.ParameterizedType

@Suppress("UNREACHABLE_CODE")
abstract class BaseFragment<V : BaseViewModel, B : ViewBinding> : Fragment(),IonFragmentBack{

    protected lateinit var viewModel: V
    protected lateinit var binding: B

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = getFragmentBinding(inflater, container)
        viewModel =
            ViewModelProvider(this)[(this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<V>]
            activity?.let {
                viewModel.mPreferenceUtil = PreferenceUtil(it)
            }

        bindData()

        return binding.root
    }

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

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

    fun convertToRequestBody(str: String): RequestBody{
        return str.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}