package com.example.appkhambenh.ui.ui.doctor.treatment

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.appkhambenh.R
import com.example.appkhambenh.databinding.FragmentDiagnoseBinding
import com.example.appkhambenh.ui.base.BaseFragment
import com.example.appkhambenh.ui.ui.doctor.FragmentTreatmentManagement.Companion.REQUEST_RECORD_AUDIO_PERMISSION
import com.example.appkhambenh.ui.ui.doctor.controller.ActionRecord
import com.example.appkhambenh.ui.ui.doctor.viewmodel.FragmentDiagnoseViewModel
import com.example.appkhambenh.ui.utils.DateUtils
import com.example.appkhambenh.ui.utils.PersonalInformation
import com.example.appkhambenh.ui.utils.initTextComplete
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.text.SimpleDateFormat

@AndroidEntryPoint
class FragmentDiagnose : BaseFragment<FragmentDiagnoseViewModel, FragmentDiagnoseBinding>() {
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var fileName: String = "" // Init file record
    private var isRecording = false
    private var isListeningAgain = false
    private var isResumingListen = false
    private val handler by lazy { Handler(Looper.getMainLooper()) } // Set up time listen again record
    private var currentListen = 0
    private var permissionToRecordAccepted = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickView()
    }

    private fun onClickView() {
        binding.apply {
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

            //Show pull down sick for sick main and sick cover
            val data: List<String?> = PersonalInformation.sick()
            sickMain.initTextComplete(requireActivity(), data)
            sickCover.initTextComplete(requireActivity(), data)
        }
    }

    private fun startRecording() {
        binding.viewCoverRecord.isVisible = false
        binding.viewListenAgain.isVisible = false
        binding.tvRecord.isVisible = true
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
        binding.viewCoverRecord.isVisible = true
        binding.viewListenAgain.isVisible = true
        binding.tvRecord.isVisible = false
    }

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
        binding.listenAgain.setImageResource(R.drawable.ic_pause)
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
        binding.listenAgain.setImageResource(R.drawable.ic_pause)
        player?.apply {
            seekTo(currentListen)
            start()
        }
    }

    private fun pauseListen() {
        binding.listenAgain.setImageResource(R.drawable.ic_play)
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
        binding.listenAgain.setImageResource(R.drawable.ic_play)
        player?.release()
        player = null
        setMinutesListen()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setMinutesListen(isTotal: Boolean = true) {
        binding.minutes.text = SimpleDateFormat(DateUtils.MINUTES).format(
            if(isTotal) getRecordingDuration() else (getRecordingDuration() - player?.currentPosition!!)
        )
    }

    private fun getRecordingDuration(): Int {
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(fileName)
        mediaPlayer.prepare()
        val duration = mediaPlayer.duration // Độ dài của bản ghi tính bằng milliseconds
        mediaPlayer.release()
        return duration
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            Toast.makeText(requireActivity(), "Chưa cấp quyền truy cập micro", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        super.onStop()
        handleActionRecord(ActionRecord.STOP)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentDiagnoseBinding.inflate(inflater)
}