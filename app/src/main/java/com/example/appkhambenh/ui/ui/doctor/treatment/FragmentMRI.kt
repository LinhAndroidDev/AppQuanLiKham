package com.example.appkhambenh.ui.ui.doctor.treatment

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.appkhambenh.databinding.FragmentMRIBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.common.dialog.DialogTakeImage
import com.example.appkhambenh.ui.ui.doctor.viewmodel.FragmentMRIViewModel
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class FragmentMRI : BaseFragment<FragmentMRIViewModel, FragmentMRIBinding>() {

    private var imageUri: Uri? = null

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
        if (it != null) {
            Glide.with(requireActivity())
                .load(it)
                .into(binding.imgMri)

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
        onClickView()
    }

    private fun onClickView() {
        binding.viewImage.setOnClickListener {
            takeImageFromUcop()
        }

        binding.update.setOnClickListener {

        }
    }

    private fun takeImageFromUcop() {
        val dialog = DialogTakeImage()
        dialog.show(parentFragmentManager, "")
        dialog.onClickCamera = {
            checkCameraPermissionAndCaptureImage()
        }
        dialog.onClickGallery = {
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


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentMRIBinding.inflate(inflater)

}