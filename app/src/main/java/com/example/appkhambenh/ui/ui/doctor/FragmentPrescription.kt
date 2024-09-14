package com.example.appkhambenh.ui.ui.doctor

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import androidx.lifecycle.lifecycleScope
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentPrescriptionBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.data.remote.entity.PatientModel
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.doctor.adapter.PrescriptionMedicalAdapter
import com.example.appkhambenh.ui.ui.doctor.adapter.SearchMedicine
import com.example.appkhambenh.ui.ui.doctor.adapter.SearchMedicineAdapter
import com.example.appkhambenh.ui.utils.DateUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.oned.EAN13Writer
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class InfoMedicine(
    val stt: Int,
    val name: String,
    val dvt: String,
    val quantity: Int,
    val dosage: String
)

data class PrescriptionInformation(
    val namePatient: String?,
    val age: Int?,
    val sex: String?,
    val healthInsurance: String?,
    val diagnose: String?,
    val includingDiseases: String?,
    val listMedicine: ArrayList<InfoMedicine>?,
    val dateCurrent: String?,
    val advice: String? = null
)

class FragmentPrescription : BaseFragment<EmptyViewModel, FragmentPrescriptionBinding>() {
    private val pdfDocument = PdfDocument()
    private var medicines = arrayListOf(
        SearchMedicine("Panadol cảm cúm", uses = "Panadol Cảm Cúm chứa paracetamol, một thành phần chính có tác dụng giảm đau hiệu quả cho các triệu chứng như đau đầu, đau họng, đau cơ, và đau xương", dosage = "Thông thường, liều lượng cho người lớn và trẻ em trên 12 tuổi là 1-2 viên mỗi 4-6 giờ, không dùng quá 8 viên trong 24 giờ"),
        SearchMedicine("Paracetamol", uses = "Paracetamol có tác dụng hạ sốt, giúp giảm nhiệt độ cơ thể khi bị sốt do cảm cúm"),
        SearchMedicine("Ibuprofen", uses = "Ibuprofen là một loại thuốc kháng viêm không steroid (NSAID) phổ biến được sử dụng để giảm đau, hạ sốt, và giảm viêm", dosage = "Thông thường là 200-400 mg mỗi 4-6 giờ nếu cần, không dùng quá 1200 mg trong 24 giờ nếu không có chỉ định của bác sĩ"),
        SearchMedicine("Aspirin", uses = "Aspirin là một loại thuốc kháng viêm không steroid (NSAID), thường được sử dụng để giảm đau, hạ sốt, và giảm viêm. Ngoài ra, aspirin còn có tác dụng ức chế tiểu cầu, giúp ngăn ngừa các vấn đề liên quan đến tim mạch", dosage = "Để giảm đau hoặc hạ sốt, thường dùng 325-650 mg mỗi 4-6 giờ nếu cần, không dùng quá 4000 mg trong 24 giờ"),
        SearchMedicine("Amoxicillin", uses = "Amoxicillin là một loại kháng sinh thuộc nhóm penicillin, được sử dụng để điều trị nhiều loại nhiễm trùng do vi khuẩn gây ra.", dosage = "Liều thông thường là 250-500 mg mỗi 8 giờ hoặc 500-875 mg mỗi 12 giờ."),
        SearchMedicine("Ciprofloxacin", uses = "Ciprofloxacin là một loại kháng sinh thuộc nhóm fluoroquinolone, được sử dụng để điều trị nhiều loại nhiễm trùng do vi khuẩn gây ra", dosage = "Nhiễm trùng đường tiết niệu: 250-500 mg mỗi 12 giờ.\n" +
                "Nhiễm trùng đường hô hấp: 500-750 mg mỗi 12 giờ.\n" +
                "Nhiễm trùng da và mô mềm: 500-750 mg mỗi 12 giờ."),
        SearchMedicine("Azithromycin", uses = "Azithromycin là một loại kháng sinh thuộc nhóm macrolide, được sử dụng để điều trị nhiều loại nhiễm trùng do vi khuẩn gây ra. ", dosage = "Liều thông thường là 500 mg trong ngày đầu tiên, sau đó là 250 mg mỗi ngày trong 4 ngày tiếp theo."),
        SearchMedicine("Cetirizine", uses = "Cetirizine là một loại thuốc kháng histamine, thường được sử dụng để điều trị các triệu chứng dị ứng", dosage = "Liều thông thường là 10 mg mỗi ngày, có thể uống 1 viên 10 mg hoặc 2 viên 5 mg."),
        SearchMedicine("Loratadine", uses = "Loratadine là một loại thuốc kháng histamine không gây buồn ngủ, thường được sử dụng để điều trị các triệu chứng dị ứng", dosage = "Liều thông thường là 10 mg mỗi ngày, thường dưới dạng một viên nén hoặc viên nang."),
        SearchMedicine("Diphenhydramine", uses = "Diphenhydramine là một loại thuốc kháng histamine có tác dụng chống dị ứng và có tác dụng gây buồn ngủ.", dosage = "Liều thông thường là 25-50 mg mỗi 4-6 giờ cần thiết để giảm triệu chứng dị ứng hoặc giúp ngủ."),
        SearchMedicine("Lisinopril", uses = "Lisinopril là một loại thuốc được sử dụng để điều trị cao huyết áp (hypertension), cũng như một số bệnh lý khác liên quan đến tim mạch.", dosage = "Thông thường, liều ban đầu là 10 mg mỗi ngày, có thể điều chỉnh tối đa lên đến 40 mg mỗi ngày, tùy thuộc vào phản ứng của bệnh nhân và hướng dẫn của bác sĩ."),
    )
    private var medicinesSelected = arrayListOf<SearchMedicine>()
    private var patient: PatientModel? = null

    companion object {
        private const val CREATE_FILE_REQUEST_CODE = 123
        private const val WIDTH_PAGE = 792
        private const val HEIGHT_PAGE = 1120
        private const val MARGIN_START = 20f
    }
    
    private var canvas: Canvas? = null
    private val bottomSheetInformation by lazy { BottomSheetBehavior.from(binding.bottomSelectInfo.layoutSelect) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillView()
        initView()
        binding.exportFilePdf.setOnClickListener {
            if(medicinesSelected.size > 5) {
                show("Số lượng thuốc trong đơn lớn hơn 5")
            } else {
                createPdf()
            }
        }
    }

    private fun initView() {
        patient = arguments?.getParcelable(FragmentAdminDoctor.OBJECT_PATIENT)
//        val prescription = PrescriptionInformation(
//            namePatient = patient?.fullname,
//            age = DateUtils.getAgeFromDate(patient?.DoB),
//            sex = patient?.sex,
//            healthInsurance = patient?.healthInsurance,
//            diagnose = binding.diagnose.getText(),
//            includingDiseases = binding.includingDiseases.getText(),
//            dateCurrent = DateUtils.getDateCurrent(),
//            listMedicine = listMedicine,
//            advice = binding.advice.getText()
//        )

        binding.addMedicine.setOnClickListener {
            bottomSheetInformation.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.addMoreMedicine.setOnClickListener {
            bottomSheetInformation.state = BottomSheetBehavior.STATE_EXPANDED
        }

        initBottomSelect()
        initDataMedicine()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initBottomSelect() {
        bottomSheetInformation.isGestureInsetBottomIgnored = false
        bottomSheetInformation.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                binding.bottomSelectInfo.layoutCoverSheet.apply {
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        this.isEnabled = true
                        this.visibility = View.VISIBLE
                    } else {
                        this.visibility = View.GONE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        binding.bottomSelectInfo.layoutMainSheet.setOnTouchListener { _, _ -> true }

        binding.bottomSelectInfo.layoutCoverSheet.setOnTouchListener { _, _ ->
            this.closeKeyboard()
            binding.bottomSelectInfo.layoutCoverSheet.isEnabled = false
            bottomSheetInformation.state = BottomSheetBehavior.STATE_COLLAPSED
            true
        }
    }

    private fun initDataMedicine() {
        with(binding.bottomSelectInfo) {
            title.text = "Danh sách thuốc"
            search.hint = "Tìm kiếm thuốc"
            update.isVisible = true
            val adapterMedicine = SearchMedicineAdapter()
            adapterMedicine.items = medicines
            medicines.forEach {
                if(it.stateChecked) {
                    adapterMedicine.listMedicineSelected.add(medicines.indexOf(it))
                }
            }
            rcvInformation.adapter = adapterMedicine
            update.setOnClickListener {
                bottomSheetInformation.state = BottomSheetBehavior.STATE_COLLAPSED
                showLoading()
                binding.root.postDelayed(1000L) {
                    dismissLoading()
                    val medicinesSelected = adapterMedicine.listMedicineSelected
                    for (i in medicinesSelected.indices) {
                        medicines[medicinesSelected[i]].stateChecked = true
                        medicines[medicinesSelected[i]].quantity = 1
                    }
                    initListMedicine()
                }
            }
//            search.doOnTextChanged { text, _, _, _ ->
//                informationAdapter.filter.filter(text)
//            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initListMedicine() {
        medicinesSelected = medicines.filter { it.stateChecked } as ArrayList<SearchMedicine>
        if(medicinesSelected.isNotEmpty()) {
            binding.rcvMedicine.isVisible = true
            binding.addMedicine.isVisible = false
            binding.addMoreMedicine.isVisible = true
            val adapterMedicine = PrescriptionMedicalAdapter(lifecycleScope)
            adapterMedicine.onClickReduce = { reduce: Pair<Int, Int> ->
                medicines[reduce.first].quantity = reduce.second
            }
            adapterMedicine.onCLickIncrease = { increase: Pair<Int, Int> ->
                medicines[increase.first].quantity = increase.second
            }
            adapterMedicine.items = medicinesSelected
            binding.rcvMedicine.adapter = adapterMedicine
        } else {
            binding.rcvMedicine.isVisible = false
            binding.addMedicine.isVisible = true
            binding.addMoreMedicine.isVisible = false
        }
    }

    private fun createPdf() {
        val time = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
            putExtra(Intent.EXTRA_TITLE, "${time.format(Date())}_my_document.pdf")
        }
        startActivityForResult(intent, CREATE_FILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                // Tạo file PDF và lưu vào URI đã chọn
                writePdfToUri(uri)
            }
        }
    }

    private fun writePdfToUri(uri: Uri) {
        val outputStream = activity?.contentResolver?.openOutputStream(uri)
        outputStream?.use { stream ->
            generateFilePdf(stream)
        }
    }

    private fun generateFilePdf(stream: OutputStream) {
        val myPageInfo: PdfDocument.PageInfo? =
            PdfDocument.PageInfo.Builder(WIDTH_PAGE, HEIGHT_PAGE, 1).create()
        val myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)
        canvas = myPage.canvas
        activity?.let {
            val title = Paint()

            title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
            title.color = ContextCompat.getColor(it, R.color.black)
            title.textSize = 30f
            title.textAlign = Paint.Align.CENTER
            canvas?.drawText("THÔNG TIN ĐƠN THUỐC", (WIDTH_PAGE/2).toFloat(), 160f, title)

            title.textSize = 20f
            title.textAlign = Paint.Align.LEFT
            canvas?.drawText("PHÒNG KHÁM ĐA KHOA NỤ CƯỜI VIỆT", MARGIN_START, 50f, title)

            // Giả sử mã số người bệnh là một chuỗi
            val patientId = "123456789012"
            // Tạo mã vạch từ mã số người bệnh
            val barcodeBitmap = generateBarcode(patientId)
            // Vẽ mã vạch lên canvas
            val paint = Paint()
            val barcodeX = WIDTH_PAGE - barcodeBitmap.width - 10f
            canvas?.drawBitmap(barcodeBitmap, barcodeX, 57f, paint)

            val txtPatientCode = "Mã số người bệnh"
            title.textSize = 12f
            val textWidth = title.measureText(txtPatientCode)
            val textX = barcodeX + (barcodeBitmap.width - textWidth) / 2
            canvas?.drawText(txtPatientCode, textX, 50f, title)

            val txtCode = "123456789012"
            val txtCodeWidth = title.measureText(txtCode)
            val txtCodeX = barcodeX + (barcodeBitmap.width - txtCodeWidth) / 2
            canvas?.drawText(txtCode, txtCodeX, 102f, title)

            title.setTypeface(Typeface.SERIF)
            title.textSize = 15f
            canvas?.drawText("Số điện thoại: 02903883397", MARGIN_START, 75f, title)
            canvas?.drawText("Phòng: Phòng 18 Khám Nội", MARGIN_START, 100f, title)
            canvas?.drawText("Số phiếu: 92070/2020", MARGIN_START, 125f, title)
            canvas?.drawText("Tên bệnh nhân: ${patient?.fullname}", MARGIN_START, 190f, title)

            canvas?.drawText("Tuổi: ${patient?.getAge()}", WIDTH_PAGE/2 + 50f, 190f, title)
            val sex = if(patient?.sex == "0") "Nam" else "Nữ"
            val txtSex = "Giới tính: $sex"
            val txtSexX = WIDTH_PAGE - title.measureText(txtSex) - 15f
            canvas?.drawText(txtSex, txtSexX, 190f, title)

            canvas?.drawText("Địa chỉ: ${patient?.address}", MARGIN_START, 215f, title)
            val txtBHYT = "Số BHYT (nếu có):"
            canvas?.drawText(txtBHYT, MARGIN_START, 240f, title)
            drawViewBHYT(title.measureText(txtBHYT) + MARGIN_START + 15, 243f)

            canvas?.drawText("Chẩn đoán: ${binding.diagnose.getText()}", MARGIN_START, 265f, title)
            canvas?.drawText("Bệnh kèm theo: ${binding.includingDiseases.getText()}", MARGIN_START, 290f, title)

            val y = drawInfoMedicine(310f)

            canvas?.drawText("Cộng khoản: 2", MARGIN_START, y + 30, title)

            title.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC))
            val txtDate = DateUtils.getDateCurrentFromVietNam()
            canvas?.drawText(txtDate, WIDTH_PAGE - MARGIN_START - title.measureText(txtDate), y + 60, title)
            title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
            val txtDoctor = "Bác sĩ điều trị"
            val txtDoctorX = WIDTH_PAGE - MARGIN_START - (title.measureText(txtDate) + title.measureText(txtDoctor))/2
            canvas?.drawText(txtDoctor, txtDoctorX, y + 85, title)

            title.setTypeface(Typeface.SERIF)
            val txtNote = "Bệnh nhân đi khám lần sau xin mang theo đơn này"
            canvas?.drawText(txtNote, MARGIN_START, y + 175, title)
            title.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC))
            val txtAdvice = "Lời dặn của bác sĩ:"
            canvas?.drawText(txtAdvice, MARGIN_START, y + 200, title)
            title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC))
            canvas?.drawText(binding.advice.getText(), title.measureText(txtAdvice) + MARGIN_START + 10, y + 200, title)

            pdfDocument.finishPage(myPage)

            // Ghi tài liệu PDF ra file
            try {
                pdfDocument.writeTo(stream)
                Toast.makeText(it, "PDF file generated..", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(it, "Fail to generate PDF file..", Toast.LENGTH_SHORT).show()
            } finally {
                // Đóng tài liệu PDF sau khi hoàn tất ghi file
                pdfDocument.close()
            }
        }
    }

    private fun drawInfoMedicine(txtY: Float): Float {
        //Draw line max width margin horizontal 15
        val line = Paint()
        line.color = ContextCompat.getColor(requireActivity(), R.color.grey_1)
        line.style = Paint.Style.STROKE
        line.strokeWidth = 1f
        canvas?.drawLine(MARGIN_START, txtY, WIDTH_PAGE - MARGIN_START, txtY, line)

        val textPaint = TextPaint().apply {
            color = Color.BLACK
            textSize = 16f
            isAntiAlias = true
            typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        }

        val text1 = "STT"
        val width1 = textPaint.measureText(text1)
        val height1 = textPaint.descent() - textPaint.ascent()

        val text2 = "Tên thuốc/hàm lượng"
        val width2 = textPaint.measureText(text2)

        val text3 = "ĐVT"
        val width3 = textPaint.measureText(text3)

        val text4 = "Số lượng"
        val width4 = textPaint.measureText(text4)

        //Draw line vertical
        val marginTop = 3f
        val lineBottomY = txtY + height1 + marginTop*2
        canvas?.drawLine(MARGIN_START, txtY, MARGIN_START, lineBottomY, line)
        //Draw first box
        canvas?.drawText(text1, MARGIN_START + 15f, txtY + height1, textPaint)
        //Draw line vertical
        val start1 = MARGIN_START + width1 + 15f*2
        canvas?.drawLine(start1, txtY, start1, lineBottomY, line)
        //Draw second box
        canvas?.drawText(text2, start1 + 150f, txtY + height1, textPaint)
        //Draw line vertical
        val start2 = start1 + width2 + 150f*2
        canvas?.drawLine(start2, txtY, start2, lineBottomY, line)
        //Draw third box
        canvas?.drawText(text3, start2 + 30, txtY + height1, textPaint)
        //Draw line vertical
        val start3 = start2 + width3 + 30f*2
        canvas?.drawLine(start3, txtY, start3, lineBottomY, line)
        //Draw fourth box
        canvas?.drawText(text4, (WIDTH_PAGE - MARGIN_START + start3)/2 - width4/2, txtY + height1, textPaint)
        //Draw line vertical end
        canvas?.drawLine(WIDTH_PAGE - MARGIN_START, txtY, WIDTH_PAGE - MARGIN_START, lineBottomY, line)

        //Draw line horizontal
        canvas?.drawLine(MARGIN_START, lineBottomY, WIDTH_PAGE - MARGIN_START, lineBottomY, line)

//        val medicineInfo = arrayListOf<InfoMedicine>()
//        medicineInfo.add(InfoMedicine(1, "Viên uống Kudos Pregnance bổ sung vitamin, khoáng chất cho bà bầu", "Viên", 30, "Uống Sáng 2 Viên, Trưa 2 Viên, Chiều 2 Viên"))
//        medicineInfo.add(InfoMedicine(2, "Viên uống Pharmacity Bone Health với Calcium, Magnesium, Vitamin D3, K2, Zinc hỗ trợ duy trì xương chắc khỏe", "Viên", 60, "Uống Sáng 2 Viên, Trưa 2 Viên, Chiều 2 Viên"))
//        medicineInfo.add(InfoMedicine(3, "Xịt họng thảo dược Pharmacity Herbal Throat Spray hỗ trợ bổ phổi, giảm đau họng và ho (Chai 25ml)", "Chai", 1, "Uống Sáng 2 Viên, Trưa 2 Viên, Chiều 2 Viên"))

        var textItemNextY = lineBottomY
        medicinesSelected.forEach {
            textPaint.setTypeface(Typeface.SERIF)

            //Draw text STT
            val sttX = (start1 + MARGIN_START)/2 - textPaint.measureText("${medicinesSelected.indexOf(it) + 1}")/2
            canvas?.drawText("${medicinesSelected.indexOf(it) + 1}", sttX, textItemNextY + 20f, textPaint)
            //Draw text name medicine
            canvas?.save()
            canvas?.translate(start1 + 5, textItemNextY + 5f) // Vị trí vẽ văn bản (x, y)
            val heightLineBreak1 = drawTextWithLineBreaks(it.uses,(width2 + 150f * 2 - 5*2).toInt())
            canvas?.restore()
            //Draw text ĐVT
            val dvtX = (start3 + start2)/2 - textPaint.measureText("Vỉ")/2
            canvas?.drawText("Vỉ", dvtX, textItemNextY + 20f, textPaint)
            //Draw text quality
            val qualityX = (WIDTH_PAGE - MARGIN_START + start3)/2 - textPaint.measureText(it.quantity.toString())/2
            canvas?.drawText(it.quantity.toString(), qualityX, textItemNextY + 20f, textPaint)

            textPaint.typeface = Typeface.defaultFromStyle(Typeface.BOLD_ITALIC)

            //Get the y coordinate position for the next item
            //Draw text dosage
            val staticLayout = StaticLayout.Builder.obtain(
                it.dosage, 0, it.dosage.length, textPaint,
                (WIDTH_PAGE - 2* MARGIN_START - 5*2).toInt()
            )
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setLineSpacing(0f, 1f)
                .setIncludePad(false)
                .build()
            val heightLineBreak2 = staticLayout.height.toFloat()

            val itemInfoY = textItemNextY + heightLineBreak1 + 8f
            //Draw line vertical
            canvas?.drawLine(MARGIN_START, textItemNextY, MARGIN_START, itemInfoY + heightLineBreak2 + 10, line)
            //Draw line vertical
            canvas?.drawLine(start1, textItemNextY, start1, itemInfoY, line)
            //Draw line vertical
            canvas?.drawLine(start2, textItemNextY, start2, itemInfoY, line)
            //Draw line vertical
            canvas?.drawLine(start3, textItemNextY, start3, itemInfoY, line)
            //Draw line vertical
            canvas?.drawLine(WIDTH_PAGE - MARGIN_START, textItemNextY, WIDTH_PAGE - MARGIN_START, itemInfoY + heightLineBreak2 + 10, line)
            //Draw line horizontal
//            setSingleLineText(itemInfoY)
            canvas?.drawLine(MARGIN_START, itemInfoY, WIDTH_PAGE - MARGIN_START, itemInfoY, line)

//            canvas?.drawText(it.dosage, MARGIN_START + 10, itemInfoY + txtDosageHeight, textPaint)
            canvas?.save()
            canvas?.translate(MARGIN_START + 10, itemInfoY + 5f) // Vị trí vẽ văn bản (x, y)
            drawTextWithLineBreaks(it.dosage,
                (WIDTH_PAGE - 2* MARGIN_START - 5*2).toInt(), Typeface.BOLD_ITALIC
            )
            canvas?.restore()

            //Draw line horizontal
            canvas?.drawLine(MARGIN_START, itemInfoY + heightLineBreak2 + 10, WIDTH_PAGE - MARGIN_START, itemInfoY + heightLineBreak2 + 10, line)
            textItemNextY = itemInfoY + heightLineBreak2 + 10
        }
        return textItemNextY
    }

    private fun drawTextWithLineBreaks(text: String, width: Int, typeText: Int = 0): Float {
        val textPaint = TextPaint().apply {
            color = Color.BLACK
            typeface = if(typeText == 0) Typeface.SERIF else Typeface.defaultFromStyle(Typeface.BOLD_ITALIC)
            textSize = 16f
        }
        val staticLayout = StaticLayout.Builder.obtain(
            text, 0, text.length, textPaint,
            width
        )
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(0f, 1f)
            .setIncludePad(false)
            .build()

        staticLayout.draw(canvas)
        return staticLayout.height.toFloat()
    }

    private fun setSingleLineText(y: Float) {
        var text = "--------------------------------------------------------------------------------------------------------------------"
//        for (i in 0 until 100) { text += "-" }

        val paint = Paint()
        paint.color = ContextCompat.getColor(requireActivity(), R.color.grey_1)
        paint.textSize = 15f
        val maxTextWidth = WIDTH_PAGE - 30f // Chiều rộng của View trừ 15 đơn vị từ mỗi bên
        var measuredWidth = 0f
        var endIndex = 0

        for (i in text.indices) {
            measuredWidth = paint.measureText(text, 0, i + 1)
            if (measuredWidth > maxTextWidth) {
                endIndex = i
                break
            }
        }

        var displayedText = ""
        displayedText = if (endIndex < text.length) {
            text.substring(0, endIndex - 3)
        } else {
            text
        }
        canvas?.drawText(displayedText, MARGIN_START, y, paint)
    }

    private fun generateBarcode(text: String): Bitmap {
        // Kiểm tra nếu chuỗi dữ liệu có đúng 12 chữ số
        if (text.length != 12) {
            throw IllegalArgumentException("EAN-13 requires 12 digits of data")
        }

        val writer = EAN13Writer()
        val bitMatrix: BitMatrix = writer.encode(text, BarcodeFormat.EAN_13, 210, 30)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
            }
        }

        return bitmap
    }

    private fun drawViewBHYT(start: Float, txtY: Float) {
        val textPaint = Paint()
        textPaint.color = Color.BLACK
        textPaint.textSize = 15f
        textPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

        val text1 = "HC"
        val width1 = textPaint.measureText(text1)
        val height1 = textPaint.descent() - textPaint.ascent()

        val text2 = "3"
        val width2 = textPaint.measureText(text2)

        val text3 = "01"
        val width3 = textPaint.measureText(text3)

        val text4 = "011 016 9971"
        val width4 = textPaint.measureText(text4)

        val line = Paint()
        val top = txtY - height1 // Position y of Top
        //Draw line horizontal bottom
        val widthBHYT = width1 + width2 + width3 + width4 + 10*2*3 + 15*2
        canvas?.drawLine(start, txtY, widthBHYT + start, txtY, line)
        //Draw line horizontal top
        canvas?.drawLine(start, top, widthBHYT + start, top, line)

        //Draw line vertical
        val marginBottom = 3f
        canvas?.drawLine(start, txtY, start, top, line)
        //Draw first box
        canvas?.drawText(text1, start + 10f, txtY - marginBottom, textPaint)
        //Draw line vertical
        val start1 = start + width1 + 10*2
        canvas?.drawLine(start1, txtY, start1, top, line)
        //Draw second box
        canvas?.drawText(text2, start1 + 10f, txtY - marginBottom, textPaint)
        //Draw line vertical
        val start2 = start1 + width2 + 10*2
        canvas?.drawLine(start2, txtY, start2, top, line)
        //Draw third box
        canvas?.drawText(text3, start2 + 10f, txtY - marginBottom, textPaint)
        //Draw line vertical
        val start3 = start2 + width3 + 10*2
        canvas?.drawLine(start3, txtY, start3, top, line)
        //Draw fourth box
        canvas?.drawText(text4, start3 + 15f, txtY - marginBottom, textPaint)
        //Draw line vertical
        val start4 = start3 + width4 + 15*2
        canvas?.drawLine(start4, txtY, start4, top, line)

        //Draw Text Into Rect
        val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        borderPaint.color = Color.BLACK
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = 1f // Độ dày của viền
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentPrescriptionBinding.inflate(inflater)
}
