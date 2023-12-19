package com.example.appkhambenh.ui.ui.user.qr

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.appkhambenh.databinding.ActivityQrBinding
import com.example.appkhambenh.ui.base.BaseActivity
import com.example.appkhambenh.ui.ui.EmptyViewModel
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView


@Suppress("DEPRECATION")
class QrActivity : BaseActivity<EmptyViewModel, ActivityQrBinding>(), ZXingScannerView.ResultHandler {

    private var scannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        scannerView = ZXingScannerView(this)

        setContentView(scannerView)

        setPermission()
    }

    override fun getActivityBinding(inflater: LayoutInflater)
    = ActivityQrBinding.inflate(inflater)

    override fun handleResult(p0: Result?) {
        val resultIntent = Intent()
        resultIntent.putExtra("results", p0.toString())
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onResume() {
        super.onResume()

        scannerView?.setResultHandler(this)
        scannerView?.startCamera()
    }

    override fun onStop() {
        super.onStop()

        scannerView?.stopCamera()
        onBackPressed()
    }

    private fun setPermission(){
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
            1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            1 -> {
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(
                        applicationContext,
                        "You need camera permission",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}