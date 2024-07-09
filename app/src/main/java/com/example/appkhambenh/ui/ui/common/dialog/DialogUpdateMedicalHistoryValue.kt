package com.example.appkhambenh.ui.ui.common.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.DialogUpdateMedicalHistoryValueBinding
import com.example.appkhambenh.ui.data.remote.request.UpdateChartRequest

class DialogUpdateMedicalHistoryValue : DialogFragment() {
    private val binding by lazy { DialogUpdateMedicalHistoryValueBinding.inflate(layoutInflater) }
    var updateChartRequest: UpdateChartRequest? = null
    var updateValue: (() -> Unit)? = null
    var errorInfo: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val width = resources.getDimensionPixelSize(R.dimen.dimen_330)
        val height = resources.getDimensionPixelSize(R.dimen.dimen_400)
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)

        binding.closeDialog.setOnClickListener { dismiss() }

        binding.cancel.setOnClickListener { dismiss() }

        val tmp = binding.temperature.checkInputValue(36, 42)
        val hgt = binding.height.checkInputValue(50, 250)
        val wht = binding.weight.checkInputValue(10, 300)
        val hb = binding.heartBeat.checkInputValue(50, 200)
        val sbp = binding.SBP.checkInputValue(90, 140)
        val dbp = binding.diastolicBloodPresure.checkInputValue(60, 90)
        val bs = binding.bloodSugar.checkInputValue(50, 150)

        binding.confirm.setOnClickListener {
            if(textIsEmpty()) {
                errorInfo?.invoke("Bạn chưa nhập đầy đủ thông tin")
            } else {
                if(tmp && hgt && wht && hb && sbp && dbp && bs) {
                    updateChartRequest = UpdateChartRequest(
                        temperature = binding.temperature.getText(),
                        bloodGlucose = binding.bloodSugar.getText(),
                        height = binding.height.getText(),
                        weight = binding.weight.getText(),
                        pulse = binding.heartBeat.getText(),
                        systolic = binding.SBP.getText(),
                        diastolic = binding.diastolicBloodPresure.getText()
                    )
                } else {
                    errorInfo?.invoke("Thông tin bạn nhập chưa chính xác")
                }
            }
        }
    }

    private fun textIsEmpty(): Boolean {
        return binding.temperature.getText().isEmpty() &&
                binding.bloodSugar.getText().isEmpty() &&
                binding.height.getText().isEmpty() &&
                binding.weight.getText().isEmpty() &&
                binding.heartBeat.getText().isEmpty() &&
                binding.SBP.getText().isEmpty() &&
                binding.diastolicBloodPresure.getText().isEmpty()
    }
}