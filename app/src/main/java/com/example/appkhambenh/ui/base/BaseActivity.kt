package com.example.appkhambenh.ui.base

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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
abstract class BaseActivity<V : BaseViewModel, B : ViewBinding> : AppCompatActivity() {
    lateinit var viewModel: V
    lateinit var binding: B
    var screenWidth: Int = 0
    var screenHeight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getActivityBinding(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this)[(this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<V>]
        viewModel.mPreferenceUtil = PreferenceUtil(this)

        fullScreen()

        animChangeScreen()

        bindData()

        val language = viewModel.mPreferenceUtil.defaultPref()
            .getString(PreferenceKey.LANGUAGE, "vi").toString()
        if(language.isNotEmpty()) setLanguage(this, language)
    }

    private fun animChangeScreen() {
        overridePendingTransition(R.anim.fade_in, R.anim.none)
    }

    private fun fullScreen() {
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
    }

    abstract fun getActivityBinding(inflater: LayoutInflater): B

    open fun bindData() {
        viewModel.errorApiLiveData.observe(this, Observer {
            show(it)
        })
    }

    fun convertToRequestBody(str: String): RequestBody {
        return str.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    }

    fun show(str: String){
        val toast: View = View.inflate(this, R.layout.custom_toast, null)
        val txtToast: TextView = toast.findViewById(R.id.txtToast)
        txtToast.text = str
        Toast(this).apply {
            duration = Toast.LENGTH_SHORT
            setGravity(Gravity.TOP, 0, 10.dpToPx(this@BaseActivity))
            view = toast
        }.show()
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    fun closeKeyboard(){
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    fun setLanguage(activity: Activity, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = activity.resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun getSizeWindow() {
        val display: Display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        screenWidth = size.x
        screenHeight = size.y
    }

    fun back(){
        closeKeyboard()
        onBackPressed()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.none, R.anim.fade_out)
    }

    override fun onBackPressed() {
        closeKeyboard()
        super.onBackPressed()
    }
}