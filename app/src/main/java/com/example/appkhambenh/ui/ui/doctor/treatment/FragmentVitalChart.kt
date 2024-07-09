package com.example.appkhambenh.ui.ui.doctor.treatment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentVitalChartBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.common.dialog.DialogUpdateMedicalHistoryValue
import com.example.appkhambenh.ui.ui.doctor.viewmodel.FragmentVitalChartViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentVitalChart : BaseFragment<FragmentVitalChartViewModel, FragmentVitalChartBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        drawLineChart(binding.lineChartTemplate, R.color.green_chart_template)
        drawLineChart(binding.lineChartBloodPressure, R.color.red_chart_blood_pressure)
        drawLineChart(binding.lineChartBloodSugarAndHeart, R.color.blue_chart_heart_and_blood_sugar)

        onClickView()
    }

    private fun onClickView() {
        binding.updateValue.setOnClickListener {
            val dialog = DialogUpdateMedicalHistoryValue()
            dialog.show(parentFragmentManager, "DialogUpdateMedicalHistoryValue")
        }
    }

    private fun drawLineChart(chart: LineChart, color: Int) {
        val lineEntries: List<Entry> = getDataSet()

        val lineDataSet = LineDataSet(lineEntries, "Work")
        lineDataSet.axisDependency = YAxis.AxisDependency.LEFT
        lineDataSet.lineWidth = 2f
        lineDataSet.color = ContextCompat.getColor(requireActivity(), color)
        lineDataSet.setCircleColor(ContextCompat.getColor(requireActivity(), color))
        lineDataSet.circleHoleRadius = 10f
        lineDataSet.setDrawCircleHole(true)
        lineDataSet.setDrawCircles(true)
        lineDataSet.setDrawValues(false)
        lineDataSet.isHighlightEnabled = false
        lineDataSet.valueTextSize = 12f
        lineDataSet.valueTextColor = Color.DKGRAY
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

        val lineData = LineData(lineDataSet)
        chart.apply {
            description.textSize = 12f
            description.isEnabled = false
            setDrawMarkers(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            animateXY(1400, 1400)
            xAxis.isGranularityEnabled = true
            xAxis.granularity = 0.1f
            axisRight.setDrawLabels(false)
            data = lineData
            setTouchEnabled(true)
            setPinchZoom(true)
        }

        val xAxisLabel = ArrayList<String>()
        for(i in lineEntries.indices) {
            xAxisLabel.add("Lần ${i+1}")
        }

        val xAxis = chart.xAxis
        xAxis.axisMaximum = 10f
        xAxis.granularity = 0.1f
        xAxis.valueFormatter =  IndexAxisValueFormatter(xAxisLabel)
    }

    private fun getDataSet(): List<Entry> {
        // Replace with your data points
        val entries = ArrayList<Entry>()
        entries.add(Entry(0f, 20f))
        entries.add(Entry(1f, 15f))
        entries.add(Entry(2f, 9f))
        entries.add(Entry(3f, 40f))
        entries.add(Entry(4f, 20f))
        entries.add(Entry(5f, 8f))
        entries.add(Entry(6f, 30f))
        entries.add(Entry(7f, 6f))
        entries.add(Entry(8f, 10f))
        entries.add(Entry(9f, 20f))
        entries.add(Entry(10f, 10f))
        return entries
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentVitalChartBinding.inflate(inflater)
}