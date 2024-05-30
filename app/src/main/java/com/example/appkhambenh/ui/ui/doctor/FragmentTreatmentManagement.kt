package com.example.appkhambenh.ui.ui.doctor

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentTreatmentManagementBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.doctor.adapter.ListOfService
import com.example.appkhambenh.ui.ui.doctor.adapter.ListOfServiceAdapter
import com.example.appkhambenh.ui.ui.doctor.adapter.Patient
import com.example.appkhambenh.ui.utils.collapseView
import com.example.appkhambenh.ui.utils.expandView
import com.example.appkhambenh.ui.utils.rotationView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.tabs.TabLayout

enum class ServiceTreatmentManagement {
    LIST_OF_SERVICE,
    MEDICAL_HISTORY,
    CLINICAL_AND_GENERAL_EXAMINATION,
    BLOOD_TESTS,
    SUPERSONIC,
    X_RAY,
    MRI,
    CT,
    DIAGNOSE
}

class FragmentTreatmentManagement : BaseFragment<EmptyViewModel, FragmentTreatmentManagementBinding>() {
    private var isExpand = false
    private var isExpandInfoIntoHospital = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        binding.root.layoutParams = layoutParams

        hideAllViewService()

        showLoading()
        binding.root.postDelayed(1000L) {
            dismissLoading()
            initView()
            onClickView()
        }
    }

    private fun initView() {

        val patient = arguments?.getParcelable<Patient>(FragmentAdminDoctor.OBJECT_PATIENT)
        patient?.let {
            binding.tvName.text = patient.name
            binding.tvAddress.text = patient.address
            binding.tvCccd.text = patient.cccd
            binding.tvPhone.text = patient.phone
            binding.tvSex.text = patient.sex
        }

        hideAllViewService()
        binding.listOfService.layout.isVisible = true
        binding.tabExamination.apply {
            addTab(this.newTab().setText("Kê dịch vụ"))
            addTab(this.newTab().setText("Bệnh sử tiền sử"))
            addTab(this.newTab().setText("Khám lâm sàng, tổng quát"))
            addTab(this.newTab().setText("Xét nghiệm máu"))
            addTab(this.newTab().setText("Siêu âm"))
            addTab(this.newTab().setText("X-quang"))
            addTab(this.newTab().setText("MRI"))
            addTab(this.newTab().setText("CT"))
            addTab(this.newTab().setText("Chẩn đoán"))
        }

        initInfoIntoHospital()
        initListOfService()
        initClinical()
        drawLineChart(binding.chart.lineChartTemplate, R.color.green_chart_template)
        drawLineChart(binding.chart.lineChartBloodPressure, R.color.red_chart_blood_pressure)
        drawLineChart(binding.chart.lineChartBloodSugarAndHeart, R.color.blue_chart_heart_and_blood_sugar)
    }

    private fun initClinical() {
        binding.clinical.apply {
            titleDiagnosisOfCirculatorySystem.title.text = "Chẩn đoán hệ tuần hoàn"
            titleDiagnosisOfDigestiveSystem.title.text = "Chẩn đoán hệ tiêu hoá"
            titleNervousSystemDiagnosis.title.text = "Chẩn đoán hệ thần kinh"
            titleENTDiagnosis.title.text = "Chẩn đoán tai mũi họng"
            titleDiagnosisOfRespiratorySystem.title.text = "Chẩn đoán hệ hô hấp"
            titlEurogenitalDiagnosis.title.text = "Chẩn đoán niệu sinh dục"
            titleDiagnosisOfMusculoskeletalSystem.title.text = "Chẩn đoán hệ xương khớp"
            titleDiagnosisOfMaxillofacialSystem.title.text = "Chẩn đoán hệ răng hàm mặt"
            titleHumanDiagnosis.title.text = "Chẩn đoán nhân khoa"
            titleSyndrome.title.text = "Hội chứng"
            titleOtherDiagnosis.title.text = "Chẩn đoán khác"
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

    private fun initInfoIntoHospital() {
        binding.apply {
            titleReason.title.text = "Lý do vào viện"
            titleIntoHospitalNumber.title.text = "Vào viện lần thứ"
            titleDiagnosisOfDestination.title.text = "Chẩn đoán nơi chuyển đến"
            titleIntroductionPlace.title.text = "Nơi giới thiệu"
            titleRoom.title.text = "Phòng"
            titleDiagnosisOfKKBEmergency.title.text = "Chẩn đoán KKB, Cấp cứu"
        }
    }

    private fun initListOfService() {
        val listOfService = arrayListOf<ListOfService>()
        listOfService.add(ListOfService(7, "Xét nghiệm máu", true))
        listOfService.add(ListOfService(8, "Chụp siêu âm", true))
        listOfService.add(ListOfService(9, "Chụp MRI", false))
        listOfService.add(ListOfService(10, "Chụp CT", true))
        val adapter = ListOfServiceAdapter()
        adapter.items = listOfService
        binding.listOfService.rcvTreatmentManagement.adapter = adapter
    }

    private fun onClickView() {
        binding.contentInfomation.post {
            binding.titleInfoPatient.setOnClickListener {
                isExpand = !isExpand
                if (isExpand) {
                    binding.iconDown.rotationView(270f, 90f)
                    binding.contentInfomation.expandView()
                } else {
                    binding.iconDown.rotationView(90f, 270f)
                    binding.contentInfomation.collapseView()
                }
            }
        }

        binding.contentInfoIntoHospital.post {
            binding.titleInfoIntoHospital.setOnClickListener {
                isExpandInfoIntoHospital = !isExpandInfoIntoHospital
                if (isExpandInfoIntoHospital) {
                    binding.iconInfoIntoHospital.rotationView(270f, 90f)
                    binding.contentInfoIntoHospital.expandView()
                } else {
                    binding.iconInfoIntoHospital.rotationView(90f, 270f)
                    binding.contentInfoIntoHospital.collapseView()
                }
            }
        }

        binding.tabExamination.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                hideAllViewService()
                when(ServiceTreatmentManagement.values()[tab.position]) {
                    ServiceTreatmentManagement.LIST_OF_SERVICE -> {
                        binding.listOfService.layout.isVisible = true
                    }

                    ServiceTreatmentManagement.MEDICAL_HISTORY -> {
                        binding.chart.layout.isVisible = true
                    }

                    ServiceTreatmentManagement.CLINICAL_AND_GENERAL_EXAMINATION -> {
                        binding.clinical.layout.isVisible = true
                    }

                    ServiceTreatmentManagement.BLOOD_TESTS -> {
                        binding.bloodTest.layout.isVisible = true
                    }

                    ServiceTreatmentManagement.SUPERSONIC -> {
                        binding.supersonic.layout.isVisible = true
                    }

                    ServiceTreatmentManagement.X_RAY -> {
                        binding.xray.layout.isVisible = true
                    }

                    ServiceTreatmentManagement.MRI -> {
                        binding.mri.layout.isVisible = true
                    }

                    ServiceTreatmentManagement.CT -> {
                        binding.ct.layout.isVisible = true
                    }

                    ServiceTreatmentManagement.DIAGNOSE -> {

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Xử lý khi tab không được chọn nữa (đã chọn tab khác)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Xử lý khi tab đã được chọn và người dùng chọn lại tab đó
            }
        })
    }

    private fun hideAllViewService() {
        binding.listOfService.layout.isVisible = false
        binding.chart.layout.isVisible = false
        binding.clinical.layout.isVisible = false
        binding.bloodTest.layout.isVisible = false
        binding.supersonic.layout.isVisible = false
        binding.xray.layout.isVisible = false
        binding.mri.layout.isVisible = false
        binding.ct.layout.isVisible = false
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentTreatmentManagementBinding.inflate(inflater)

}