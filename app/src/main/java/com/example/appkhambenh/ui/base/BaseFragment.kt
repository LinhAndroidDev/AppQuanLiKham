package com.example.appkhambenh.ui.base

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import androidx.viewbinding.ViewBinding
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.example.appkhambenh.ui.utils.PreferenceUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.reflect.ParameterizedType

@Suppress("UNREACHABLE_CODE")
abstract class BaseFragment<V : BaseViewModel, B : ViewBinding> : Fragment(),IonFragmentBack{

    protected lateinit var viewModel: V
    protected lateinit var binding: B
    var screenWidth: Int = 0
    var screenHeight: Int = 0
    var id_user = -1

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

        id_user = viewModel.mPreferenceUtil.defaultPref()
            .getInt(PreferenceKey.USER_ID, -1)

        bindData()

        return binding.root
    }

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    open fun bindData() {
        viewModel.errorApiLiveData.observe(viewLifecycleOwner){
            show(it)
        }
    }

    override fun onFragmentBack(): Boolean {
        return false
    }

    fun convertToRequestBody(str: String): RequestBody{
        return str.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    }

    fun show(str: String){
        val toast: View = View.inflate(requireActivity(), R.layout.custom_toast, null)
        val txtToast: TextView = toast.findViewById(R.id.txtToast)
        txtToast.text = str
        Toast(requireActivity()).apply {
            duration = Toast.LENGTH_SHORT
            setGravity(Gravity.BOTTOM, 0, 10.dpToPx())
            view = toast
        }.show()
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun getSizeWindow() {
        val display: Display = requireActivity().windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        screenWidth = size.x
        screenHeight = size.y
    }

    private fun Int.dpToPx(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            resources.displayMetrics
        ).toInt()
    }
}