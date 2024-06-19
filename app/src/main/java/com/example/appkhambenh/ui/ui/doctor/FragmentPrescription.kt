package com.example.appkhambenh.ui.ui.doctor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.print.pdf.PrintedPdfDocument
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentPrescriptionBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.example.appkhambenh.ui.ui.doctor.adapter.PdfDocumentAdapter
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.oned.EAN13Writer
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class InfoMedicine(
    val stt: Int,
    val name: String,
    val dvt: String,
    val quantity: Int
)

class FragmentPrescription : BaseFragment<EmptyViewModel, FragmentPrescriptionBinding>() {
    private var bmp: Bitmap? = null
    private var scaledbmp: Bitmap? = null
    private val pdfDocument = PdfDocument()

    companion object {
        private const val CREATE_FILE_REQUEST_CODE = 123
        private const val WIDTH_PAGE = 792
        private const val HEIGHT_PAGE = 1120
        private const val MARGIN_START = 20f
    }

    private val myPageInfo: PdfDocument.PageInfo? =
            PdfDocument.PageInfo.Builder(WIDTH_PAGE, HEIGHT_PAGE, 1).create()

    private val myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)
    private val canvas: Canvas = myPage.canvas

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillView()
        binding.exportFilePdf.setOnClickListener {
            createPdf()
        }

        // Đường dẫn đến file PDF
//        val pdfFilePath = "/path/to/your/file.pdf"
//        printPdfFile(requireActivity(), pdfFilePath)a
    }

    private fun printPdfFile(context: Context, filePath: String) {
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
        try {
            val file = File(filePath)
            val document = PrintedPdfDocument(context, PrintAttributes.Builder().build())

            val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
            val page = document.startPage(pageInfo)

            val fileInputStream = FileInputStream(file)
            val buffer = ByteArray(file.length().toInt())
            fileInputStream.read(buffer)

            // Giả sử bạn đã tải dữ liệu PDF vào buffer
            // Bạn cần vẽ nội dung PDF lên trang, ví dụ:
            val paint = Paint()
            paint.textSize = 15f
            paint.color = Color.BLACK
            page.canvas.drawText("Hello, world!", 100f, 100f, paint)

            document.finishPage(page)

            // Lưu tài liệu in
            val fileOutputStream = FileOutputStream(file)
            document.writeTo(fileOutputStream)

            document.close()
            fileInputStream.close()
            fileOutputStream.close()

            // Sử dụng PrintManager để in file PDF
            printManager.print("Document", PdfDocumentAdapter(filePath), null)
        } catch (e: IOException) {
            Log.e("PrintPdfActivity", "Error printing PDF", e)
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
        activity?.let {
            val title = Paint()

            title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
            title.color = ContextCompat.getColor(it, R.color.black)
            title.textSize = 30f
            title.textAlign = Paint.Align.CENTER
            canvas.drawText("THÔNG TIN ĐƠN THUỐC", (WIDTH_PAGE/2).toFloat(), 160f, title)

            title.textSize = 20f
            title.textAlign = Paint.Align.LEFT
            canvas.drawText("PHÒNG KHÁM ĐA KHOA NỤ CƯỜI VIỆT", MARGIN_START, 50f, title)

            // Giả sử mã số người bệnh là một chuỗi
            val patientId = "123456789012"
            // Tạo mã vạch từ mã số người bệnh
            val barcodeBitmap = generateBarcode(patientId)
            // Vẽ mã vạch lên canvas
            val paint = Paint()
            val barcodeX = WIDTH_PAGE - barcodeBitmap.width - 10f
            canvas.drawBitmap(barcodeBitmap, barcodeX, 57f, paint)

            val txtPatientCode = "Mã số người bệnh"
            title.textSize = 12f
            val textWidth = title.measureText(txtPatientCode)
            val textX = barcodeX + (barcodeBitmap.width - textWidth) / 2
            canvas.drawText(txtPatientCode, textX, 50f, title)

            val txtCode = "123456789012"
            val txtCodeWidth = title.measureText(txtCode)
            val txtCodeX = barcodeX + (barcodeBitmap.width - txtCodeWidth) / 2
            canvas.drawText(txtCode, txtCodeX, 102f, title)

            title.setTypeface(Typeface.SERIF)
            title.textSize = 15f
            canvas.drawText("Số điện thoại: 02903883397", MARGIN_START, 75f, title)
            canvas.drawText("Phòng: Phòng 18 Khám Nội", MARGIN_START, 100f, title)
            canvas.drawText("Số phiếu: 92070/2020", MARGIN_START, 125f, title)
            canvas.drawText("Tên bệnh nhân: Nguyễn Hữu Linh", MARGIN_START, 190f, title)

            canvas.drawText("Tuổi: 24", WIDTH_PAGE/2 + 50f, 190f, title)
            val txtSex = "Giới tính: Nam"
            val txtSexX = WIDTH_PAGE - title.measureText(txtSex) - 15f
            canvas.drawText("Giới tính: Nam", txtSexX, 190f, title)

            canvas.drawText("Địa chỉ: Phường An Hải Đông, Quận Sơn Trà, Thành Phố Đà Nẵng", MARGIN_START, 215f, title)
            val txtBHYT = "Số BHYT (nếu có):"
            canvas.drawText(txtBHYT, MARGIN_START, 240f, title)
            drawViewBHYT(title.measureText(txtBHYT) + MARGIN_START + 15, 243f)

            canvas.drawText("Chẩn đoán: Thấp không ảnh hưởng đến tim", MARGIN_START, 265f, title)
            canvas.drawText("Bệnh kèm theo: ", MARGIN_START, 290f, title)

            val y = drawInfoMedicine(310f)

            canvas.drawText("Cộng khoản: 2", MARGIN_START, y + 30, title)

            title.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC))
            val txtDate = "Ngày 18 tháng 02 năm 2020"
            canvas.drawText(txtDate, WIDTH_PAGE - MARGIN_START - title.measureText(txtDate), y + 60, title)
            title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
            val txtDoctor = "Bác sĩ điều trị"
            val txtDoctorX = WIDTH_PAGE - MARGIN_START - (title.measureText(txtDate) + title.measureText(txtDoctor))/2
            canvas.drawText(txtDoctor, txtDoctorX, y + 85, title)

            title.setTypeface(Typeface.SERIF)
            val txtNote = "Bệnh nhân đi khám lần sau xin mang theo đơn này"
            canvas.drawText(txtNote, MARGIN_START, y + 175, title)
            title.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC))
            val txtAdvice = "Lời dặn của bác sĩ:"
            canvas.drawText(txtAdvice, MARGIN_START, y + 200, title)
            title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC))
            canvas.drawText("hết thuốc tái khám", title.measureText(txtAdvice) + MARGIN_START + 10, y + 200, title)

            pdfDocument.finishPage(myPage)

            try {
                pdfDocument.writeTo(stream)
                Toast.makeText(it, "PDF file generated..", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(it, "Fail to generate PDF file..", Toast.LENGTH_SHORT)
                    .show()
            }
            pdfDocument.close()
        }
    }

    private fun drawInfoMedicine(txtY: Float): Float {
        //Draw line max width margin horizontal 15
        val line = Paint()
        line.color = ContextCompat.getColor(requireActivity(), R.color.grey_1)
        line.style = Paint.Style.STROKE
        line.strokeWidth = 1f
        canvas.drawLine(MARGIN_START, txtY, WIDTH_PAGE - MARGIN_START, txtY, line)

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
        canvas.drawLine(MARGIN_START, txtY, MARGIN_START, lineBottomY, line)
        //Draw first box
        canvas.drawText(text1, MARGIN_START + 15f, txtY + height1, textPaint)
        //Draw line vertical
        val start1 = MARGIN_START + width1 + 15f*2
        canvas.drawLine(start1, txtY, start1, lineBottomY, line)
        //Draw second box
        canvas.drawText(text2, start1 + 150f, txtY + height1, textPaint)
        //Draw line vertical
        val start2 = start1 + width2 + 150f*2
        canvas.drawLine(start2, txtY, start2, lineBottomY, line)
        //Draw third box
        canvas.drawText(text3, start2 + 30, txtY + height1, textPaint)
        //Draw line vertical
        val start3 = start2 + width3 + 30f*2
        canvas.drawLine(start3, txtY, start3, lineBottomY, line)
        //Draw fourth box
        canvas.drawText(text4, (WIDTH_PAGE - MARGIN_START + start3)/2 - width4/2, txtY + height1, textPaint)
        //Draw line vertical end
        canvas.drawLine(WIDTH_PAGE - MARGIN_START, txtY, WIDTH_PAGE - MARGIN_START, lineBottomY, line)

        //Draw line bottom
        canvas.drawLine(MARGIN_START, lineBottomY, WIDTH_PAGE - MARGIN_START, lineBottomY, line)

        val medicineInfo = arrayListOf<InfoMedicine>()
        medicineInfo.add(InfoMedicine(1, "Viên uống Kudos Pregnance bổ sung vitamin, khoáng chất cho bà bầu", "Viên", 30))
        medicineInfo.add(InfoMedicine(2, "Viên uống Pharmacity Bone Health với Calcium, Magnesium, Vitamin D3, K2, Zinc hỗ trợ duy trì xương chắc khỏe", "Viên", 60))
        medicineInfo.add(InfoMedicine(3, "Xịt họng thảo dược Pharmacity Herbal Throat Spray hỗ trợ bổ phổi, giảm đau họng và ho (Chai 25ml)", "Chai", 1))

        textPaint.setTypeface(Typeface.SERIF)
        var textItemNextY = lineBottomY
        medicineInfo.forEach {
            val staticLayout = StaticLayout.Builder.obtain(
                it.name, 0, it.name.length, textPaint,
                (width2 + 150f * 2 - 5*2).toInt()
            )
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setLineSpacing(0f, 1f)
                .setIncludePad(false)
                .build()

            //Draw text STT
            val sttX = (start1 + MARGIN_START)/2 - textPaint.measureText(it.stt.toString())/2
            canvas.drawText(it.stt.toString(), sttX, textItemNextY + 20f, textPaint)
            //Draw text name medicine
            canvas.save()
            canvas.translate(start1 + 5, textItemNextY + 5f) // Vị trí vẽ văn bản (x, y)
            staticLayout.draw(canvas)
            canvas.restore()
            //Draw text ĐVT
            val dvtX = (start3 + start2)/2 - textPaint.measureText(it.dvt)/2
            canvas.drawText(it.dvt, dvtX, textItemNextY + 20f, textPaint)
            //Draw text quality
            val qualityX = (WIDTH_PAGE - MARGIN_START + start3)/2 - textPaint.measureText(it.quantity.toString())/2
            canvas.drawText(it.quantity.toString(), qualityX, textItemNextY + 20f, textPaint)

            //Get the y coordinate position for the next item
            textItemNextY += staticLayout.height.toFloat() + 8f
            //Draw line vertical
            canvas.drawLine(MARGIN_START, lineBottomY, MARGIN_START, textItemNextY, line)
            //Draw line vertical
            canvas.drawLine(start1, lineBottomY, start1, textItemNextY, line)
            //Draw line vertical
            canvas.drawLine(start2, lineBottomY, start2, textItemNextY, line)
            //Draw line vertical
            canvas.drawLine(start3, lineBottomY, start3, textItemNextY, line)
            //Draw line vertical
            canvas.drawLine(WIDTH_PAGE - MARGIN_START, lineBottomY, WIDTH_PAGE - MARGIN_START, textItemNextY, line)
            //Draw line bottom
            canvas.drawLine(MARGIN_START, textItemNextY, WIDTH_PAGE - MARGIN_START, textItemNextY, line)
        }
        return textItemNextY
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
        canvas.drawLine(start, txtY, widthBHYT + start, txtY, line)
        //Draw line horizontal top
        canvas.drawLine(start, top, widthBHYT + start, top, line)

        //Draw line vertical
        val marginBottom = 3f
        canvas.drawLine(start, txtY, start, top, line)
        //Draw first box
        canvas.drawText(text1, start + 10f, txtY - marginBottom, textPaint)
        //Draw line vertical
        val start1 = start + width1 + 10*2
        canvas.drawLine(start1, txtY, start1, top, line)
        //Draw second box
        canvas.drawText(text2, start1 + 10f, txtY - marginBottom, textPaint)
        //Draw line vertical
        val start2 = start1 + width2 + 10*2
        canvas.drawLine(start2, txtY, start2, top, line)
        //Draw third box
        canvas.drawText(text3, start2 + 10f, txtY - marginBottom, textPaint)
        //Draw line vertical
        val start3 = start2 + width3 + 10*2
        canvas.drawLine(start3, txtY, start3, top, line)
        //Draw fourth box
        canvas.drawText(text4, start3 + 15f, txtY - marginBottom, textPaint)
        //Draw line vertical
        val start4 = start3 + width4 + 15*2
        canvas.drawLine(start4, txtY, start4, top, line)

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
