package com.example.duoblogistics.ui.barcodereader

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Intent
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.duoblogistics.R
import com.example.duoblogistics.barcode.barcodedetection.BarcodeProcessor
import com.example.duoblogistics.ui.camera.CameraSource
import com.example.duoblogistics.ui.camera.CameraSourcePreview
import com.example.duoblogistics.ui.camera.GraphicOverlay
import com.example.duoblogistics.ui.camera.WorkflowModel
import com.example.duoblogistics.ui.settings.SettingsActivity
import com.google.android.gms.common.internal.Objects
import com.google.android.material.chip.Chip
import java.io.IOException
import java.util.ArrayList

class LiveBarcodeScanFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_barcode_scan, container, false)
    }

    private var cameraSource: CameraSource? = null
    private var preview: CameraSourcePreview? = null
    private var graphicOverlay: GraphicOverlay? = null
    private var settingsButton: View? = null
    private var flashButton: View? = null
    private var promptChip: Chip? = null
    private var promptChipAnimator: AnimatorSet? = null
    private var workflowModel: WorkflowModel? = null
    private var currentWorkflowState: WorkflowModel.WorkflowState? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preview = getView()?.findViewById(R.id.camera_preview)
        graphicOverlay =
            getView()?.findViewById<GraphicOverlay>(R.id.camera_preview_graphic_overlay)?.apply {
                setOnClickListener(this@LiveBarcodeScanFragment)
                cameraSource = CameraSource(this)
            }

        promptChip = getView()?.findViewById(R.id.bottom_prompt_chip)
        promptChipAnimator =
            (AnimatorInflater.loadAnimator(activity, R.animator.bottom_prompt_chip_enter) as AnimatorSet).apply {
                setTarget(promptChip)
            }

//        getView()?.findViewById<View>(R.id.close_button)?.setOnClickListener(this)
        flashButton = getView()?.findViewById<View>(R.id.flash_button)?.apply {
            setOnClickListener(this@LiveBarcodeScanFragment)
        }
        settingsButton = getView()?.findViewById<View>(R.id.settings_button)?.apply {
            setOnClickListener(this@LiveBarcodeScanFragment)
        }

        setUpWorkflowModel()
    }

    override fun onResume() {
        super.onResume()

        workflowModel?.markCameraFrozen()
        settingsButton?.isEnabled = true
        currentWorkflowState = WorkflowModel.WorkflowState.NOT_STARTED
        cameraSource?.setFrameProcessor(BarcodeProcessor(graphicOverlay!!, workflowModel!!))
        workflowModel?.setWorkflowState(WorkflowModel.WorkflowState.DETECTING)
    }

//    override fun onPostResume() {
//        super.onPostResume()
//        BarcodeResultFragment.dismiss(supportFragmentManager)
//    }

    override fun onPause() {
        super.onPause()
        currentWorkflowState = WorkflowModel.WorkflowState.NOT_STARTED
        stopCameraPreview()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraSource?.release()
        cameraSource = null
    }

    override fun onClick(view: View) {
        when (view.id) {
//            R.id.close_button -> onBackPressed()
            R.id.flash_button -> {
                flashButton?.let {
                    if (it.isSelected) {
                        it.isSelected = false
                        cameraSource?.updateFlashMode(Camera.Parameters.FLASH_MODE_OFF)
                    } else {
                        it.isSelected = true
                        cameraSource!!.updateFlashMode(Camera.Parameters.FLASH_MODE_TORCH)
                    }
                }
            }
            R.id.settings_button -> {
                settingsButton?.isEnabled = false
                activity?.let{
                    val intent = Intent (it, SettingsActivity::class.java)
                    it.startActivity(intent)
                }
            }
        }
    }

    private fun startCameraPreview() {
        val workflowModel = this.workflowModel ?: return
        val cameraSource = this.cameraSource ?: return
        if (!workflowModel.isCameraLive) {
            try {
                workflowModel.markCameraLive()
                preview?.start(cameraSource)
            } catch (e: IOException) {
                Log.e("TAG", "Failed to start camera preview!", e)
                cameraSource.release()
                this.cameraSource = null
            }
        }
    }

    private fun stopCameraPreview() {
        val workflowModel = this.workflowModel ?: return
        if (workflowModel.isCameraLive) {
            workflowModel.markCameraFrozen()
            flashButton?.isSelected = false
            preview?.stop()
        }
    }

    private fun setUpWorkflowModel() {
        workflowModel = ViewModelProviders.of(this).get(WorkflowModel::class.java)

        // Observes the workflow state changes, if happens, update the overlay view indicators and
        // camera preview state.
        activity?.let {
            workflowModel!!.workflowState.observe(it, Observer { workflowState ->
                if (workflowState == null || Objects.equal(currentWorkflowState, workflowState)) {
                    return@Observer
                }

                currentWorkflowState = workflowState
                Log.d("TAG", "Current workflow state: ${currentWorkflowState!!.name}")

                val wasPromptChipGone = promptChip?.visibility == View.GONE

                when (workflowState) {
                    WorkflowModel.WorkflowState.DETECTING -> {
                        promptChip?.visibility = View.VISIBLE
                        promptChip?.setText(R.string.prompt_point_at_a_barcode)
                        startCameraPreview()
                    }
                    WorkflowModel.WorkflowState.CONFIRMING -> {
                        promptChip?.visibility = View.VISIBLE
                        promptChip?.setText(R.string.prompt_move_camera_closer)
                        startCameraPreview()
                    }
                    WorkflowModel.WorkflowState.SEARCHING -> {
                        promptChip?.visibility = View.VISIBLE
                        promptChip?.setText(R.string.prompt_searching)
                        stopCameraPreview()
                    }
                    WorkflowModel.WorkflowState.DETECTED, WorkflowModel.WorkflowState.SEARCHED -> {
                        promptChip?.visibility = View.GONE
                        stopCameraPreview()
                    }
                    else -> promptChip?.visibility = View.GONE
                }

                val shouldPlayPromptChipEnteringAnimation =
                    wasPromptChipGone && promptChip?.visibility == View.VISIBLE
                promptChipAnimator?.let {
                    if (shouldPlayPromptChipEnteringAnimation && !it.isRunning) it.start()
                }
            })
        }

        workflowModel?.detectedBarcode?.observe(viewLifecycleOwner, Observer { barcode ->
            if (barcode != null) {
                Log.d("RESULTING_CODE", barcode.rawValue.toString())
                workflowModel?.setWorkflowState(WorkflowModel.WorkflowState.DETECTING)
//                startCameraPreview()
//                val barcodeFieldList = ArrayList<BarcodeField>()
//                barcodeFieldList.add(BarcodeField("Raw Value", barcode.rawValue ?: ""))
//                BarcodeResultFragment.show(supportFragmentManager, barcodeFieldList)
            }
        })
    }
}