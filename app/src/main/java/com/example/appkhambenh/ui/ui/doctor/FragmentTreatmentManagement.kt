package com.example.appkhambenh.ui.ui.doctor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentTreatmentManagementBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.data.remote.entity.GetMedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.entity.MedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.entity.PatientModel
import com.example.appkhambenh.ui.data.remote.entity.ServiceOrderModel
import com.example.appkhambenh.ui.data.remote.request.AddServiceRequest
import com.example.appkhambenh.ui.data.remote.request.BloodTestRequest
import com.example.appkhambenh.ui.data.remote.request.DiagnoseRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateInfoClinicalExaminationRequest
import com.example.appkhambenh.ui.ui.common.dialog.DialogAddService
import com.example.appkhambenh.ui.ui.common.dialog.DialogConfirm
import com.example.appkhambenh.ui.ui.common.dialog.DialogTakeImage
import com.example.appkhambenh.ui.ui.common.dialog.DialogUpdateMedicalHistoryValue
import com.example.appkhambenh.ui.ui.doctor.adapter.ListOfServiceAdapter
import com.example.appkhambenh.ui.ui.doctor.controller.ActionRecord
import com.example.appkhambenh.ui.ui.doctor.viewmodel.FragmentTreatmentManagementViewModel
import com.example.appkhambenh.ui.utils.DateUtils
import com.example.appkhambenh.ui.utils.PersonalInformation
import com.example.appkhambenh.ui.utils.addFragmentByTag
import com.example.appkhambenh.ui.utils.collapseView
import com.example.appkhambenh.ui.utils.expandView
import com.example.appkhambenh.ui.utils.initTextComplete
import com.example.appkhambenh.ui.utils.rotationView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.tabs.TabLayout
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Create By NGUYEN HUU LINH 2024
 */

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

@Suppress("UNREACHABLE_CODE")
@AndroidEntryPoint
class FragmentTreatmentManagement : BaseFragment<FragmentTreatmentManagementViewModel, FragmentTreatmentManagementBinding>() {
    private var isExpand = false
    private var isExpandInfoIntoHospital = false
    private var currentImage: ImageView? = null
    private var imageUri: Uri? = null
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var fileName: String = "" // Init file record
    private var isRecording = false
    private var isListeningAgain = false
    private var isResumingListen = false
    private val handler by lazy { Handler(Looper.getMainLooper()) } // Set up time listen again record
    private var currentListen = 0
    private var permissionToRecordAccepted = false
    private var patient: PatientModel? = null
    private var medicalHistoryId = 0
    private var listOfServiceAdapter: ListOfServiceAdapter? = null
    private var services: ArrayList<ServiceOrderModel>? = null
    private val bloodGroups by lazy { arrayListOf("A", "B", "AB", "O") }
    private val permissions by lazy {
        arrayOf(
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }

    private val uCropContract = object : ActivityResultContract<List<Uri?>, Uri?>(){
        override fun createIntent(context: Context, input: List<Uri?>): Intent {
            val inputUri = input[0]
            val outputUri = input[1]

            val uCop = UCrop.of(inputUri!!, outputUri!!)
                .withAspectRatio(5f, 5f)
                .withMaxResultSize(800, 800)

            return uCop.getIntent(context)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return if(intent != null) UCrop.getOutput(intent) else null
        }

    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
        if(uri != null){
            val date = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.getDefault())
            val outputUri = File(requireActivity().filesDir, "${date.format(Date())}croppedImage.jpg").toUri()
            cropImage.launch(listOf(uri, outputUri))
        }
    }

    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            val date = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.getDefault())
            val outputUri = File(requireActivity().filesDir, "${date.format(Date())}croppedImage.jpg").toUri()
            cropImage.launch(listOf(imageUri, outputUri))
        }
    }

    private val cropImage = registerForActivityResult(uCropContract) {
        if (it != null && currentImage != null) {
            Log.e("Uri Crop: ", "$it")
            Glide.with(requireActivity())
                .load(it)
                .into(currentImage!!)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            captureImage()
        } else {
            Toast.makeText(requireActivity(), "Chưa cấp quyền truy cập Camera", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("FragmentTreatmentManagement", "FragmentTreatmentManagement")

        fillView()
        ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        showLoading()
        binding.root.postDelayed(1000L) {
            dismissLoading()
            initView()
            onClickView()
        }
    }

    private fun startRecording() {
        binding.diagnose.viewCoverRecord.isVisible = false
        binding.diagnose.viewListenAgain.isVisible = false
        binding.diagnose.tvRecord.isVisible = true
        fileName = "${activity?.externalCacheDir?.absolutePath}/audiorecordtest.3gp"

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e("AUDIO_RECORDING", "prepare() failed")
            }

            start()
        }
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null

        setMinutesListen()
        binding.diagnose.viewCoverRecord.isVisible = true
        binding.diagnose.viewListenAgain.isVisible = true
        binding.diagnose.tvRecord.isVisible = false
    }

    /**
     * Get Time Of Recording
     */
    private fun getRecordingDuration(): Int {
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(fileName)
        mediaPlayer.prepare()
        val duration = mediaPlayer.duration // Độ dài của bản ghi tính bằng milliseconds
        mediaPlayer.release()
        return duration
    }

    /**
     * Calculator Time For Recording And Listen Again
     */
    @SuppressLint("SimpleDateFormat")
    private fun setMinutesListen(isTotal: Boolean = true) {
        binding.diagnose.minutes.text = SimpleDateFormat(DateUtils.MINUTES).format(
            if(isTotal) getRecordingDuration() else (getRecordingDuration() - player?.currentPosition!!)
        )
    }

    /**
     * This Function Handle Recording Events
     */
    private fun handleActionRecord(action: ActionRecord) {
        when(action) {
            ActionRecord.START -> {
                isListeningAgain = true
                isResumingListen = true
                startListen()
            }

            ActionRecord.RESUME -> {
                isListeningAgain = true
                resumeListen()
            }

            ActionRecord.PAUSE -> {
                isListeningAgain = false
                pauseListen()
            }

            ActionRecord.FINISH -> {
                isListeningAgain = false
                isResumingListen = false
                finishListen()
            }

            ActionRecord.STOP -> {
                stopListen()
            }
        }
    }

    private fun startListen() {
        binding.diagnose.listenAgain.setImageResource(R.drawable.ic_pause)
        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                setOnPreparedListener {
                    it.start()
                    setMinutesListen()
                }
                prepareAsync()
            } catch (e: IOException) {
                Log.e("AUDIO_PLAYBACK", "prepare() failed")
            }
        }

        handler.postDelayed(object : Runnable {
            override fun run() {
                if (isListeningAgain) {
                    setMinutesListen(isTotal = false)
                    player?.setOnCompletionListener {
                        isListeningAgain = false
                        handleActionRecord(ActionRecord.FINISH)
                    }
                }
                handler.postDelayed(this, 1000)
            }
        }, 0)
    }

    private fun resumeListen() {
        binding.diagnose.listenAgain.setImageResource(R.drawable.ic_pause)
        player?.apply {
            seekTo(currentListen)
            start()
        }
    }

    private fun pauseListen() {
        binding.diagnose.listenAgain.setImageResource(R.drawable.ic_play)
        player?.apply {
            currentListen = currentPosition
            pause()
        }
    }

    private fun stopListen() {
        recorder?.release()
        recorder = null
        player?.release()
        player = null
        isListeningAgain = false
    }

    private fun finishListen() {
        binding.diagnose.listenAgain.setImageResource(R.drawable.ic_play)
        player?.release()
        player = null
        setMinutesListen()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            Toast.makeText(requireActivity(), "Chưa cấp quyền truy cập micro", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initView() {
        listOfServiceAdapter = ListOfServiceAdapter(requireActivity())
        binding.listOfService.rcvTreatmentManagement.adapter = listOfServiceAdapter

        patient = arguments?.getParcelable(FragmentAdminDoctor.OBJECT_PATIENT)
        patient?.let {
            binding.tvName.text = patient?.fullname
            binding.tvAddress.text = patient?.address
            binding.tvCccd.text = patient?.citizenId
            binding.tvPhone.text = patient?.phoneNumber
            binding.tvSex.text = if(patient?.sex == "0") "Nam" else "Nữ"
        }

        medicalHistoryId = arguments?.getInt(FragmentAdminDoctor.MEDICAL_HISTORY_ID)!!
        medicalHistoryId.let {
            lifecycleScope.launch {
                delay(500L)
                withContext(Dispatchers.Main) {
                    viewModel.getMedicalHistory(patient?.id ?: 0, medicalHistoryId)
                    viewModel.services.collect { serviceModels ->
                        services = serviceModels
                        serviceModels?.let {
                            initListOfService(serviceModels)
                            initClinical()
                            initBloodTest()
                            initDiagnose()
                            checkDisableService()
                        }
                    }
                    viewModel.medicalHistorys.collect {
                        initInfoIntoHospital(it)
                    }
                }
            }
        }

        hideAllViewService()
        binding.listOfService.layout.isVisible = true
        val examinations = arrayListOf("Kê dịch vụ", "Bệnh sử tiền sử", "Khám lâm sàng, tổng quát", "Xét nghiệm máu", "Siêu âm", "X-quang", "MRI", "CT", "Chẩn đoán")
        examinations.forEach {
            binding.tabExamination.addTab(binding.tabExamination.newTab().setText(it))
        }

        drawLineChart(binding.chart.lineChartTemplate, R.color.green_chart_template)
        drawLineChart(binding.chart.lineChartBloodPressure, R.color.red_chart_blood_pressure)
        drawLineChart(binding.chart.lineChartBloodSugarAndHeart, R.color.blue_chart_heart_and_blood_sugar)
    }

    private fun checkDisableService() {
        val serviceIds = arrayListOf<Int>()
        val serviceStatus = arrayListOf<String>()
        services?.forEach {
            serviceIds.add(it.serviceId)
            serviceStatus.add(it.status)
        }

        for (i in 3..8) {
            if (serviceIds.contains(i) && (i - 3) <= serviceStatus.size - 1) {
                if(serviceStatus[i - 3] == "1") {
                    enableTab(i - 1)
                } else {
                    disableTab(i - 1)
                }
            } else disableTab(i - 1)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun disableTab(index: Int) {
        val tab = binding.tabExamination.getTabAt(index)
        tab?.view?.setOnTouchListener { _, _ -> true}
        tab?.view?.alpha = 0.5f
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun enableTab(index: Int) {
        val tab = binding.tabExamination.getTabAt(index)
        tab?.view?.setOnTouchListener { _, _ -> false}
        tab?.view?.alpha = 1f
    }

    private fun initDiagnose() {
        val diagnose = getServiceModelAt(2)
        //Show pull down sick for sick main and sick cover
        binding.diagnose.apply {
            val data: List<String?> = PersonalInformation.sick()
            sickMain.initTextComplete(requireActivity(), data)
            sickCover.initTextComplete(requireActivity(), data)

            diagnose?.let {
                diagnose.principalDiagnosisCode?.let { sickMain.setText(it) }
                diagnose.secondaryDiagnosisCode?.let { sickCover.setText(it) }
                diagnose.prognosis?.let { prognosis.setText(it) }
                diagnose.forecast?.let { forecast.setText(it) }
                diagnose.treatmentPlan?.let { treatmentPlan.setText(it) }
            }
        }
    }

    private fun initBloodTest() {
        val bloodTest = getServiceModelAt(4)
        binding.bloodTest.apply {
            bloodGroup.setUpListSpinner(bloodGroups)
            if (bloodTest != null) {
                bloodTest.glu?.let { glu.setText(it) }
                bloodTest.hb?.let { hb.setText(it) }
                bloodTest.htc?.let { hct.setText(it) }
                bloodTest.lym?.let { lym.setText(it) }
                bloodTest.mch?.let { mch.setText(it) }
                bloodTest.mcv?.let { mcv.setText(it) }
                bloodTest.mono?.let { mono.setText(it) }
                bloodTest.neut?.let { neut.setText(it) }
                bloodTest.plt?.let { plt.setText(it) }
                bloodTest.rbc?.let { rbc.setText(it) }
                bloodTest.ure?.let { ure.setText(it) }
                bloodTest.wbc?.let { wbc.setText(bloodTest.wbc) }
                bloodGroup.setUpIndexSpinner(
                    if (bloodTest.bloodGroup != null) bloodTest.bloodGroup.toString().toInt() else 0
                )
            }
        }
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

            val clinical = getServiceModelAt(3)
            edtCirculatoryDiagnosis.setText(clinical?.circulatoryDiagnosis)
            edtRespiratoryDiagnosis.setText(clinical?.respiratoryDiagnosis)
            edtGastrointestinalDiagnosis.setText(clinical?.gastrointestinalDiagnosis)
            edtUrogenitalDiagnosis.setText(clinical?.urogenitalDiagnosis)
            edtNeurologicalDiagnosis.setText(clinical?.neurologicalDiagnosis)
            edtMusculoskeletalDiagnosis.setText(clinical?.musculoskeletalDiagnosis)
            edtOtolaryngologicalDiagnosis.setText(clinical?.otolaryngologicalDiagnosis)
            edtMaxillofacialDiagnosis.setText(clinical?.maxillofacialDiagnosis)
            edtOphthalmicDiagnosis.setText(clinical?.ophthalmicDiagnosis)
            otherDiagnosis.setText(clinical?.otherDiagnosis)
            syndrome.setText(clinical?.syndrome)

            update.setOnClickListener {
                val updateInfoClinicalExaminationRequest = UpdateInfoClinicalExaminationRequest(
                    edtCirculatoryDiagnosis.text.toString(),
                    edtRespiratoryDiagnosis.text.toString(),
                    edtGastrointestinalDiagnosis.text.toString(),
                    edtUrogenitalDiagnosis.text.toString(),
                    edtNeurologicalDiagnosis.text.toString(),
                    edtMusculoskeletalDiagnosis.text.toString(),
                    edtOtolaryngologicalDiagnosis.text.toString(),
                    edtMaxillofacialDiagnosis.text.toString(),
                    edtOphthalmicDiagnosis.text.toString(),
                    otherDiagnosis.text.toString(),
                    syndrome.text.toString(),
                )
                lifecycleScope.launch {
                    withContext(Dispatchers.Main) {
                        viewModel.updateClinicalExamination(
                            clinical?.id ?: 0,
                            updateInfoClinicalExaminationRequest,
                            medicalHistoryId
                        )
                    }
                }
            }
        }
    }

    /**
     * Get ServiceOrderModel By Id Service
     */
    private fun getServiceModelAt(serviceId: Int): ServiceOrderModel? {
        var serviceModel: ServiceOrderModel? = null
        services?.forEach {
            if(it.serviceId == serviceId) serviceModel = it
        }
        return serviceModel
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

    private fun initInfoIntoHospital(medicalHistory: GetMedicalHistoryResponse.Data?) {
        binding.contentInfoIntoHospital.apply {
            titleReason.title.text = "Lý do vào viện"
            titleIntroductionPlace.title.text = "Nơi giới thiệu"
            titleTime.title.text = "Nhập viện lúc"
            titleFacultyTreatment.title.text = "Khoa điều trị"
            titleIntoHospitalNumber.title.text = "Vào viện lần thứ"
            titleRoom.title.text = "Phòng"
            titleBed.title.text = "Giường"
            titleDoctor.title.text = "Bác sĩ điều trị"
            titleDiagnosePast.title.text = "Chẩn đoán nơi chuyển đến"
            titleEmergencyDiagnose.title.text = "Chẩn đoán KKB, Cấp cứu"
            titleDiagnoseNow.title.text = "Chẩn đoán hiện tại"
            titleDiagnoseMove.title.text = "Chẩn đoán ra viện"

            edtReason.setText(medicalHistory?.reason)
            edtIntroductionPlace.setText(medicalHistory?.introductionPlace)
            medicalHistory?.createdAt?.let { edtTime.setText(DateUtils.convertIsoDateTimeToDate(it)) }
            edtFacultyTreatment.setText(medicalHistory?.facultyTreatment)
            medicalHistory?.patient?.countMedicalVisit?.let { edtIntoHospitalNumber.setText(it) }
            edtRoom.setText(medicalHistory?.room)
            edtBed.setText(medicalHistory?.bed)
            edtDiagnosePast.setText(medicalHistory?.diagnosePast)
            edtEmergencyDiagnose.setText(medicalHistory?.emergencyDiagnose)
            edtDiagnoseNow.setText(medicalHistory?.diagnoseNow)
            edtDiagnoseMove.setText(medicalHistory?.diagnoseMove)

        }
    }

    private fun initListOfService(serviceModels: ArrayList<ServiceOrderModel>) {
        listOfServiceAdapter?.onClickPay = {
            val dialog = DialogConfirm()
            dialog.agree = {
                viewModel.payService(it, medicalHistoryId)
                dialog.dismiss()
            }
            val bundle = Bundle()
            bundle.putString(DialogConfirm.NOTIFICATION_CONFIRM, "Bạn xác nhận thanh toán dịch vụ này?")
            dialog.show(parentFragmentManager, "")
            dialog.arguments = bundle
        }
        listOfServiceAdapter?.resetList(serviceModels)
    }

    /**
     * This Function Handle Even Click View
     */
    @SuppressLint("DiscouragedPrivateApi")
    private fun onClickView() {
        binding.prescription.setOnClickListener {
            val fragment = FragmentPrescription()
            val bundle = Bundle()
            bundle.putParcelable(FragmentAdminDoctor.OBJECT_PATIENT, patient)
            fragment.arguments = bundle
            addFragmentByTag(fragment, R.id.changeIdDoctorVn, "FragmentTreatmentManagement")
        }

        binding.chart.updateValue.setOnClickListener {
            val dialog = DialogUpdateMedicalHistoryValue()
            dialog.errorInfo = { show(it) }
            dialog.updateValue = {
                lifecycleScope.launch {
                    dialog.updateChartRequest?.let { updateChartRequest ->
                        viewModel.updateChart(services?.get(0)?.id ?: 0, updateChartRequest, medicalHistoryId)
                    }
                }
            }
            dialog.show(parentFragmentManager, "DialogUpdateMedicalHistoryValue")
        }

        binding.diagnose.apply {
            viewRecord.setOnClickListener {
                if(isListeningAgain) {
                    handleActionRecord(ActionRecord.PAUSE)
                }
                isResumingListen = false
                isRecording = !isRecording
                if(isRecording) {
                    record.setImageResource(R.drawable.ic_stop)
                    startRecording()
                } else {
                    record.setImageResource(R.drawable.ic_micro)
                    stopRecording()
                }
            }

            btnListenAgain.setOnClickListener {
                if(!isListeningAgain) {
                    handleActionRecord(if(!isResumingListen) ActionRecord.START else ActionRecord.RESUME)
                } else {
                    handleActionRecord(ActionRecord.PAUSE)
                }
            }

            removeRecord.setOnClickListener {
                viewListenAgain.isVisible = false
                handleActionRecord(ActionRecord.PAUSE)
            }
        }

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

        binding.contentInfoIntoHospital.layout.post {
            binding.titleInfoIntoHospital.setOnClickListener {
                isExpandInfoIntoHospital = !isExpandInfoIntoHospital
                if (isExpandInfoIntoHospital) {
                    binding.iconInfoIntoHospital.rotationView(270f, 90f)
                    binding.contentInfoIntoHospital.layout.expandView()
                } else {
                    binding.iconInfoIntoHospital.rotationView(90f, 270f)
                    binding.contentInfoIntoHospital.layout.collapseView()
                }
            }
        }

        binding.supersonic.viewImage.setOnClickListener {
            takeImageFromUcop(binding.supersonic.imgSuperSonic)
        }

        binding.xray.viewImage.setOnClickListener {
            takeImageFromUcop(binding.xray.imgXray)
        }

        binding.mri.viewImage.setOnClickListener {
            takeImageFromUcop(binding.mri.imgMri)
        }

        binding.ct.viewImage.setOnClickListener {
            takeImageFromUcop(binding.ct.imgCt)
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
                        binding.diagnose.layout.isVisible = true
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

        binding.listMedicalRecord.setOnClickListener {
            addFragmentByTag(FragmentListMedicalRecord(), R.id.changeIdDoctorVn, "FragmentTreatmentManagement")
        }

        binding.listOfService.addService.setOnClickListener {
            val dialogAddService = DialogAddService()
            dialogAddService.show(parentFragmentManager, "DialogAddService")
            dialogAddService.addService = {
                if(existService(dialogAddService.idService)) {
                    show("Dịch vụ này đã được sử dụng")
                } else {
                    lifecycleScope.launch {
                        withContext(Dispatchers.Main) {
                            viewModel.addService(AddServiceRequest(dialogAddService.idService, medicalHistoryId), medicalHistoryId)
                        }
                    }
                    dialogAddService.dismiss()
                }
            }
        }

        binding.bloodTest.apply {
            update.setOnClickListener {
                if(!isTextBloodNotTextEmpty()) {
                    show("Bạn cần nhập đầy đủ thông tin")
                } else {
                    if(!correctInputBloodTest()) {
                        show("Thông tin bạn nhập chưa chính xác")
                    } else {
                        val bloodTest = getServiceModelAt(4)
                        lifecycleScope.launch {
                            withContext(Dispatchers.Main) {
                                val updateBloodTestRequest = BloodTestRequest(
                                    bloodGroup = bloodGroup.indexSelected.toString(),
                                    glu.getText(),
                                    hb.getText(),
                                    hct.getText(),
                                    lym.getText(),
                                    mch.getText(),
                                    mcv.getText(),
                                    mono.getText(),
                                    neut.getText(),
                                    plt.getText(),
                                    rbc.getText(),
                                    ure.getText(),
                                    wbc.getText()
                                )
                                viewModel.updateBloodTest(
                                    bloodTest?.id ?: 0,
                                    updateBloodTestRequest,
                                    medicalHistoryId
                                )
                            }
                        }
                    }
                }
            }
        }

        binding.diagnose.apply {
           update.setOnClickListener {
                if(!isTextDiagnoseNotEmpty()) {
                    show("Bạn chưa nhập đầy đủ thông tin")
                } else {
                    val diagnose = getServiceModelAt(2)
                    val diagnoseRequest = DiagnoseRequest(
                        principalDiagnosisCode = sickMain.text.toString(),
                        secondaryDiagnosisCode = sickCover.text.toString(),
                        prognosis = prognosis.text.toString(),
                        forecast = forecast.text.toString(),
                        treatmentPlan = treatmentPlan.text.toString()
                    )
                    lifecycleScope.launch {
                        withContext(Dispatchers.Main) {
                            viewModel.updateDiagnose(
                                diagnose?.id ?: 0,
                                diagnoseRequest,
                                medicalHistoryId
                            )
                        }
                    }
                }
            }
        }
    }

    private fun isTextDiagnoseNotEmpty(): Boolean {
        return binding.diagnose.sickCover.text.isNotEmpty() &&
                binding.diagnose.sickCover.text.isNotEmpty() &&
                binding.diagnose.prognosis.text.isNotEmpty() &&
                binding.diagnose.forecast.text.isNotEmpty() &&
                binding.diagnose.treatmentPlan.text.isNotEmpty()
    }

    /**
     * Check If EditText Inputs Are Qualified
     */
    private fun correctInputBloodTest(): Boolean {
        return binding.bloodTest.glu.passCondition &&
                binding.bloodTest.hb.passCondition &&
                binding.bloodTest.hct.passCondition &&
                binding.bloodTest.lym.passCondition &&
                binding.bloodTest.mch.passCondition &&
                binding.bloodTest.mcv.passCondition &&
                binding.bloodTest.mono.passCondition &&
                binding.bloodTest.neut.passCondition &&
                binding.bloodTest.plt.passCondition &&
                binding.bloodTest.rbc.passCondition &&
                binding.bloodTest.ure.passCondition &&
                binding.bloodTest.wbc.passCondition
    }

    private fun isTextBloodNotTextEmpty(): Boolean {
        return binding.bloodTest.glu.getText().isNotEmpty() &&
                binding.bloodTest.hb.getText().isNotEmpty() &&
                binding.bloodTest.hct.getText().isNotEmpty() &&
                binding.bloodTest.lym.getText().isNotEmpty() &&
                binding.bloodTest.mch.getText().isNotEmpty() &&
                binding.bloodTest.mcv.getText().isNotEmpty() &&
                binding.bloodTest.mono.getText().isNotEmpty() &&
                binding.bloodTest.neut.getText().isNotEmpty() &&
                binding.bloodTest.plt.getText().isNotEmpty() &&
                binding.bloodTest.rbc.getText().isNotEmpty() &&
                binding.bloodTest.ure.getText().isNotEmpty() &&
                binding.bloodTest.wbc.getText().isNotEmpty()
    }

    /**
     * Check Whether The Service Is Registered Or Not Based On Its Id
     */
    private fun existService(idService: Int): Boolean {
        var exist = false
        services?.forEach {
            if(it.serviceId == idService) {
                exist = true
            }
        }
        return exist
    }

    /**
     * Show Dialog Choose Camera Or Gallery
     */
    private fun takeImageFromUcop(image: ImageView) {
        val dialog = DialogTakeImage()
        dialog.show(parentFragmentManager, "")
        dialog.onClickCamera = {
            currentImage = image
            checkCameraPermissionAndCaptureImage()
        }
        dialog.onClickGallery = {
            currentImage = image
            getContent.launch("image/*")
        }
    }

    private fun captureImage() {
        val photoFile = File.createTempFile(
            "JPEG_${System.currentTimeMillis()}_",
            ".jpg",
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        imageUri = FileProvider.getUriForFile(
            requireActivity(),
            "com.example.appkhambenh.provider",
            photoFile
        )
        takePicture.launch(imageUri)
    }

    private fun checkCameraPermissionAndCaptureImage() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CAMERA) -> {
                captureImage()
            }
            else -> {
                requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }
    }

    private fun hideAllViewService() {
        binding.apply {
            listOfService.layout.isVisible = false
            chart.layout.isVisible = false
            clinical.layout.isVisible = false
            bloodTest.layout.isVisible = false
            supersonic.layout.isVisible = false
            xray.layout.isVisible = false
            mri.layout.isVisible = false
            ct.layout.isVisible = false
            diagnose.layout.isVisible = false
        }
    }

    override fun onStop() {
        super.onStop()
        handleActionRecord(ActionRecord.STOP)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentTreatmentManagementBinding.inflate(inflater)

}