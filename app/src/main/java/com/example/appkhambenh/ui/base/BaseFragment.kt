package com.example.appkhambenh.ui.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.ui.common.dialog.DialogLoading
import com.example.appkhambenh.ui.utils.ConvertUtils.dpToPx
import com.example.appkhambenh.ui.utils.SharePreferenceRepositoryImpl
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.reflect.ParameterizedType
import java.util.*


@Suppress("DEPRECATION")
abstract class BaseFragment<V : BaseViewModel, B : ViewBinding> : Fragment(), IonFragmentBack {

    protected lateinit var viewModel: V
    protected lateinit var binding: B
    var loading = DialogLoading()
    lateinit var sharePrefer: SharePreferenceRepositoryImpl

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = getFragmentBinding(inflater, container)
        viewModel =
            ViewModelProvider(this)[(this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<V>]
        sharePrefer = SharePreferenceRepositoryImpl(requireActivity())

        bindData()

        setLanguage(requireActivity(), sharePrefer.getLanguage())

        return binding.root
    }

    fun showLoading() {
        loading.show(requireActivity().supportFragmentManager, "")
    }

    fun dismissLoading() {
        loading.dismiss()
    }

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    open fun bindData() {
        viewModel.errorApiLiveData.observe(viewLifecycleOwner) {
            show(it)
        }
    }

    fun replaceFragment(fragment: Fragment, changeId: Int) {
        val fm: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        fm.replace(changeId, fragment).addToBackStack(null).commit()
    }

    override fun onFragmentBack(): Boolean {
        return false
    }

    fun convertToRequestBody(str: String): RequestBody {
        return str.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    }

    fun show(str: String) {
        val toast: View = View.inflate(requireActivity(), R.layout.custom_toast, null)
        val txtToast: TextView = toast.findViewById(R.id.txtToast)
        txtToast.text = str
        Toast(requireActivity()).apply {
            duration = Toast.LENGTH_SHORT
            setGravity(Gravity.TOP, 0, 10.dpToPx(requireActivity()))
            view = toast
        }.show()
    }

    fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    fun setLanguage(activity: Activity, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = activity.resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    fun closeKeyboard() {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    fun back() {
        closeKeyboard()
        activity?.onBackPressed()
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}