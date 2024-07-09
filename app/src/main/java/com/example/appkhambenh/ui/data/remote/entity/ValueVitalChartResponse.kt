package com.example.appkhambenh.ui.data.remote.entity

data class ValueVitalChartResponse(
    val data: ArrayList<VitalChartModel>
)

data class VitalChartModel(
    val id: Int,
    val temperature: Int,
    val height: Int,
    val weight: Int,
    val pulse: Int,
    val systolic: Int,
    val diastolic: Int,
    val bloodGlucose: Int
)