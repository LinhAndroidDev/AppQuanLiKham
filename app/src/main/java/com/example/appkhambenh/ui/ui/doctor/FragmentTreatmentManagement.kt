package com.example.appkhambenh.ui.ui.doctor

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
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
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class FragmentTreatmentManagement : BaseFragment<EmptyViewModel, FragmentTreatmentManagementBinding>() {
    private var isExpand = true
    private var isExpandInfoIntoHospital = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        binding.root.layoutParams = layoutParams

        initView()
        onClickView()
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
        drawLineChart()
    }

    private fun drawLineChart() {
        val lineEntries: List<Entry> = getDataSet()

        val lineDataSet = LineDataSet(lineEntries, "Work")
        lineDataSet.axisDependency = YAxis.AxisDependency.LEFT
        lineDataSet.lineWidth = 2f
        lineDataSet.color = ContextCompat.getColor(requireActivity(), R.color.green_dark)
        lineDataSet.setCircleColor(ContextCompat.getColor(requireActivity(), R.color.green_dark))
        lineDataSet.circleHoleRadius = 10f
        lineDataSet.setDrawCircleHole(true)
        lineDataSet.setDrawCircles(true)
        lineDataSet.isHighlightEnabled = false
        lineDataSet.valueTextSize = 12f
        lineDataSet.valueTextColor = Color.DKGRAY
        lineDataSet.mode = LineDataSet.Mode.STEPPED

        val lineData = LineData(lineDataSet)
        binding.chart.lineChart.apply {
            description.textSize = 12f
            description.isEnabled = false
            setDrawMarkers(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            animateXY(1400, 1400)
            xAxis.isGranularityEnabled = true
            xAxis.granularity = 0.1f
            data = lineData
            setTouchEnabled(true)
            setPinchZoom(true)
        }

        val xAxisLabel = ArrayList<String>()
        xAxisLabel.add("Rest")
        xAxisLabel.add("Work")
        xAxisLabel.add("2-up")

        val xAxis = binding.chart.lineChart.xAxis
        xAxis.axisMaximum = 10f
        xAxis.granularity = 0.1f
        xAxis.valueFormatter = object : IndexAxisValueFormatter(xAxisLabel) {
            override fun getValues(): Array<String> {
                return super.getValues()
            }
        }
    }

    private fun getDataSet(): List<Entry> {
        // Replace with your data points
        val entries = ArrayList<Entry>()
        entries.add(Entry(0f, 0f))
        entries.add(Entry(1f, 0f))
        entries.add(Entry(2f, 0f))
        entries.add(Entry(3f, 0f))
        entries.add(Entry(4f, 0f))
        entries.add(Entry(5f, 0f))
        entries.add(Entry(6f, 0f))
        entries.add(Entry(7f, 0f))
        entries.add(Entry(8f, 0f))
        entries.add(Entry(9f, 0f))
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
        binding.rcvTreatmentManagement.adapter = adapter
    }

    private fun onClickView() {
        binding.contentInfomation.post {
            val height = binding.contentInfomation.height

            binding.titleInfoPatient.setOnClickListener {
                isExpand = !isExpand
                if (isExpand) {
                    binding.iconDown.rotationView(270f, 90f)
                    expandView(binding.contentInfomation, height)
                } else {
                    binding.iconDown.rotationView(90f, 270f)
                    collapseView(binding.contentInfomation)
                }
            }
        }

        binding.contentInfoIntoHospital.post {
            val height = binding.contentInfoIntoHospital.height

            binding.titleInfoIntoHospital.setOnClickListener {
                isExpandInfoIntoHospital = !isExpandInfoIntoHospital
                if (isExpandInfoIntoHospital) {
                    binding.iconInfoIntoHospital.rotationView(270f, 90f)
                    expandView(binding.contentInfoIntoHospital, height)
                } else {
                    binding.iconInfoIntoHospital.rotationView(90f, 270f)
                    collapseView(binding.contentInfoIntoHospital)
                }
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentTreatmentManagementBinding.inflate(inflater)

}