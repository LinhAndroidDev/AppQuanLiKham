package com.example.appkhambenh.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.appkhambenh.ui.utils.PreferenceUtil
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
}