package com.example.appkhambenh.ui.ui.doctor.adapter

import android.os.ParcelFileDescriptor
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class PdfDocumentAdapter(private val filePath: String) : PrintDocumentAdapter() {

    override fun onStart() {
        super.onStart()
    }

    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes?,
        cancellationSignal: android.os.CancellationSignal?,
        callback: LayoutResultCallback?,
        extras: android.os.Bundle?
    ) {
        if (cancellationSignal?.isCanceled == true) {
            callback?.onLayoutCancelled()
            return
        }

        val documentInfo = PrintDocumentInfo.Builder("file_name")
            .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
            .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
            .build()

        callback?.onLayoutFinished(documentInfo, true)
    }

    override fun onWrite(
        pages: Array<out android.print.PageRange>?,
        destination: ParcelFileDescriptor?,
        cancellationSignal: android.os.CancellationSignal?,
        callback: WriteResultCallback?
    ) {
        try {
            val fileInputStream = FileInputStream(filePath)
            val fileOutputStream = FileOutputStream(destination?.fileDescriptor)

            fileInputStream.copyTo(fileOutputStream)

            fileInputStream.close()
            fileOutputStream.close()

            callback?.onWriteFinished(arrayOf(android.print.PageRange.ALL_PAGES))
        } catch (e: IOException) {
            callback?.onWriteFailed(e.toString())
        }
    }
}