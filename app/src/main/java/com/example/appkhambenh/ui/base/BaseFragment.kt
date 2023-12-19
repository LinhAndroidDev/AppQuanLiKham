package com.example.appkhambenh.ui.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.utils.PreferenceKey
import com.example.appkhambenh.ui.utils.PreferenceUtil
import com.example.appkhambenh.ui.utils.dpToPx
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.reflect.ParameterizedType
import java.util.*


@Suppress("DEPRECATION")
abstract class BaseFragment<V : BaseViewModel, B : ViewBinding> : Fragment(), IonFragmentBack {

    protected lateinit var viewModel: V
    protected lateinit var binding: B
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    val loading by lazy { ProgressDialog(requireActivity()) }
    val userId by lazy {
        viewModel.mPreferenceUtil.defaultPref()
            .getInt(PreferenceKey.USER_ID, -1)
    }
    val avatarUser by lazy {
        viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.USER_AVATAR, "")
    }
    val nameUser by lazy {
        viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.USER_NAME, "")
    }
    val sex by lazy {
        viewModel.mPreferenceUtil.defaultPref()
            .getInt(PreferenceKey.USER_SEX, -1)
    }
    val birthUser by lazy {
        viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.USER_BIRTH, "")
    }
    val addressUser by lazy {
        viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.USER_ADDRESS, "")
    }
    val typeUser by lazy {
        viewModel.mPreferenceUtil.defaultPref()
            .getInt(PreferenceKey.USER_TYPE, -1)
    }
    private val language by lazy {
        viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.LANGUAGE, "vi").toString()
    }

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
        activity?.let {
            viewModel.mPreferenceUtil = PreferenceUtil(it)
        }

        loading.setMessage("Please wait...")
        loading.setTitle(getString(R.string.notification))
        loading.setCancelable(false)

        bindData()

        if (language.isNotEmpty()) setLanguage(requireActivity(), language)

        return binding.root
    }

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    open fun bindData() {
        viewModel.errorApiLiveData.observe(viewLifecycleOwner) {
            show(it)
        }
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

    private fun getSizeWindow() {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels
        screenWidth = displayMetrics.widthPixels
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}