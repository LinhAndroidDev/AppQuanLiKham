package com.example.appkhambenh.ui.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Any, VB : ViewDataBinding> :
    RecyclerView.Adapter<BaseAdapter.BaseViewHolder<VB>>() {
    var items = arrayListOf<T>()
    val itemOlds by lazy { items }

    protected abstract fun getLayout(): Int

    class BaseViewHolder<VB : ViewDataBinding>(val v: VB) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder<VB>(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), getLayout(), parent, false)
    )

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun resetList(list: ArrayList<T>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearList() {
        items = arrayListOf()
        notifyDataSetChanged()
    }
}