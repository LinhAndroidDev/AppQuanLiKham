package com.example.appkhambenh.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.appkhambenh.ui.utils.PreferenceUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.reflect.ParameterizedType
import java.util.zip.Inflater

abstract class BaseActivity<V : BaseViewModel, B : ViewBinding> : AppCompatActivity() {
    lateinit var viewModel: V
    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getActivityBinding(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this)[(this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<V>]
        viewModel.mPreferenceUtil = PreferenceUtil(this)

        bindData()
    }

    abstract fun getActivityBinding(inflater: LayoutInflater): B

    open fun bindData() {
        viewModel.errorApiLiveData.observe(this, Observer {
            Toast.makeText(
                this,
                it,
                Toast.LENGTH_SHORT
            ).show()
        })
    }

    fun convertToRequestBody(str: String): RequestBody {
        return str.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    }

    fun show(str: String){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}