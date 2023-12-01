package com.example.appkhambenh.ui.ui.doctor.statistical

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.ActivityStatisticalBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF

class StatisticalActivity : BaseActivity<EmptyViewModel, ActivityStatisticalBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.chart.setUsePercentValues(true)
        binding.chart.description.isEnabled = false
        binding.chart.setExtraOffsets(5f, 10f, 5f, 5f)

        // on below line we are setting drag for our pie chart
        binding.chart.dragDecelerationFrictionCoef = 0.95f

        // on below line we are setting hole
        // and hole color for pie chart
        binding.chart.isDrawHoleEnabled = true
        binding.chart.setHoleColor(Color.WHITE)

        // on below line we are setting circle color and alpha
        binding.chart.setTransparentCircleColor(Color.WHITE)
        binding.chart.setTransparentCircleAlpha(110)

        // on  below line we are setting hole radius
        binding.chart.holeRadius = 58f
        binding.chart.transparentCircleRadius = 61f

        // on below line we are setting center text
        binding.chart.setDrawCenterText(true)

        // on below line we are setting
        // rotation for our pie chart
        binding.chart.rotationAngle = 0f

        // enable rotation of the binding.chart by touch
        binding.chart.isRotationEnabled = true
        binding.chart.isHighlightPerTapEnabled = true

        // on below line we are setting animation for our pie chart
        binding.chart.animateY(1400, Easing.EaseInOutQuad)

        // on below line we are disabling our legend for pie chart
        binding.chart.legend.isEnabled = false
        binding.chart.setEntryLabelColor(Color.WHITE)
        binding.chart.setEntryLabelTextSize(12f)

        // on below line we are creating array list and
        // adding data to it to display in pie chart
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(70f))
        entries.add(PieEntry(20f))
        entries.add(PieEntry(10f))

        // on below line we are setting pie data set
        val dataSet = PieDataSet(entries, "Mobile OS")

        // on below line we are setting icons.
        dataSet.setDrawIcons(false)

        // on below line we are setting slice for pie
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors to list
        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.orange))
        colors.add(resources.getColor(R.color.background))
        colors.add(resources.getColor(R.color.grey))

        // on below line we are setting colors.
        dataSet.colors = colors

        // on below line we are setting pie data set
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(15f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)
        binding.chart.data = data

        // undo all highlights
        binding.chart.highlightValues(null)

        // loading chart
        binding.chart.invalidate()
    }

    override fun getActivityBinding(inflater: LayoutInflater) =
        ActivityStatisticalBinding.inflate(inflater)
}