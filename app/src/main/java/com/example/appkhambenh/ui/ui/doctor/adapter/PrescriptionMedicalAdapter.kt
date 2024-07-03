package com.example.appkhambenh.ui.ui.doctor.adapter

import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ItemPrescriptionMedicalBinding
import com.example.appkhambenh.ui.base.BaseAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PrescriptionMedicalAdapter(val scope: CoroutineScope) : BaseAdapter<SearchMedicine, ItemPrescriptionMedicalBinding>() {
    var onClickReduce: ((Pair<Int, Int>) -> Unit)? = null
    var onCLickIncrease: ((Pair<Int, Int>) -> Unit)? = null

    override fun getLayout(): Int = R.layout.item_prescription_medical

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemPrescriptionMedicalBinding>,
        position: Int,
    ) {
        holder.v.apply {
            nameMedicine.text = items[position].name
            quantityMedicine.text = items[position].quantity.toString()

            reduce.setOnClickListener {
                var quantity = items[position].quantity
                if(quantity > 1) {
                    scope.launch(Dispatchers.Main) {
                        async {
                            quantity--
                            items[position].quantity = quantity
                            notifyItemChanged(position)
                        }.await()
                    }
                    onClickReduce?.invoke(Pair(position, quantity))
                }
            }

            increase.setOnClickListener {
                var quantity = items[position].quantity
                scope.launch(Dispatchers.Main) {
                    async {
                        quantity++
                        items[position].quantity = quantity
                        notifyItemChanged(position)
                    }.await()
                }
                onCLickIncrease?.invoke(Pair(position, quantity))
            }
        }

    }
}