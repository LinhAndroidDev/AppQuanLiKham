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
import com.example.appkhambenh.ui.data.remote.entity.VitalChartModel
import com.example.appkhambenh.ui.data.remote.request.UpdateChartRequest
import com.example.appkhambenh.ui.ui.doctor.CustomEdtSearchInfoPatient

class DialogUpdateMedicalHistoryValue : DialogFragment() {
    private val binding by lazy { DialogUpdateMedicalHistoryValueBinding.inflate(layoutInflater) }
    var updateChartRequest: UpdateChartRequest? = null
    var updateValue: (() -> Unit)? = null
    var errorInfo: ((String) -> Unit)? = null
    private var valueChart: VitalChartModel? = null

    companion object {
        const val VITAL_CHART_MODEL = "VITAL_CHART_MODEL"
    }

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

        valueChart = arguments?.getParcelable(VITAL_CHART_MODEL)
        valueChart?.let {
            binding.temperature.setText(it.temperature.toString())
            binding.bloodSugar.setText(it.bloodGlucose.toString())
            binding.height.setText(it.height.toString())
            binding.weight.setText(it.weight.toString())
            binding.heartBeat.setText(it.pulse.toString())
            binding.SBP.setText(it.systolic.toString())
            binding.diastolicBloodPresure.setText(it.diastolic.toString())
        }

        binding.closeDialog.setOnClickListener { dismiss() }

        binding.cancel.setOnClickListener { dismiss() }

        binding.temperature.checkInputValue(36, 42)
        binding.height.checkInputValue(50, 250)
        binding.weight.checkInputValue(10, 300)
        binding.heartBeat.checkInputValue(50, 200)
        binding.SBP.checkInputValue(90, 140)
        binding.diastolicBloodPresure.checkInputValue(60, 90)
        binding.bloodSugar.checkInputValue(50, 150)

        binding.confirm.setOnClickListener {
            if(textIsEmpty()) {
                errorInfo?.invoke("Bạn chưa nhập đầy đủ thông tin")
            } else {
                val tmp = binding.temperature.checkValue(36, 42)
                val hgt = binding.height.checkValue(50, 250)
                val wht = binding.weight.checkValue(10, 300)
                val hb = binding.heartBeat.checkValue(50, 200)
                val sbp = binding.SBP.checkValue(90, 140)
                val dbp = binding.diastolicBloodPresure.checkValue(60, 90)
                val bs = binding.bloodSugar.checkValue(50, 150)

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
                    updateValue?.invoke()
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

    private fun CustomEdtSearchInfoPatient.checkValue(start: Int, end: Int): Boolean {
        var passCondition = false
        this.getText().let { text ->
            passCondition = if(text.isNotEmpty()) {
                val value = text.toInt()
                value in start..end
            } else {
                false
            }
        }
        return passCondition
    }
}