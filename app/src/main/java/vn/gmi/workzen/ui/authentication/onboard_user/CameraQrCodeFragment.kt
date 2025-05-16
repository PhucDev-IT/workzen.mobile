package vn.gmi.workzen.ui.authentication.onboard_user

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.ImageAnalysis
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.ZoomSuggestionOptions
import com.google.mlkit.vision.barcode.common.Barcode
import vn.gemini.passport.R
import vn.mobile.verifysdk.data.BasicInformation
import vn.gemini.passport.base.CameraAdvanceFragment
import vn.gemini.passport.databinding.FragmentCameraQrCodeBinding
import vn.gmi.core.ui.CameraAdvanceFragment
import vn.gmi.workzen.vision.QRCodeAndBarcodeAnalyzer


class CameraQrCodeFragment : CameraAdvanceFragment(), QRCodeAndBarcodeAnalyzer.QRCodeListener {
    private var binding:FragmentCameraQrCodeBinding?=null
    private var basicInformation:BasicInformation?=null
    private var options = BarcodeScannerOptions.Builder()
        .enableAllPotentialBarcodes()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE
        )

    private var runnableDetected : Runnable? = null

    fun setRunnableDetected(runnable: Runnable){
        runnableDetected = runnable
    }

    fun getBasicInformation():BasicInformation?{
        return basicInformation
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraQrCodeBinding.inflate(inflater,container,false)
        binding?.layoutHead?.toolbar?.setOnClickListener{
            requireActivity().finish()
        }


        return binding?.root
    }


    override val imageAnalyzer: ImageAnalysis
        get() { return ImageAnalysis.Builder()
            .setTargetResolution(Size(1280, 720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build().apply {
                val zoomCallback = ZoomSuggestionOptions.ZoomCallback { zoomLevel: Float ->
                    Log.i(TAG, "Set zoom ratio $zoomLevel")
                    val ignored = setZoomRatioCamera(zoomLevel)
                    true
                }
                options.setZoomSuggestionOptions(ZoomSuggestionOptions.Builder(zoomCallback).build())
                setAnalyzer(
                    ContextCompat.getMainExecutor(requireContext()),
                    QRCodeAndBarcodeAnalyzer(this@CameraQrCodeFragment,options.build())
                )
            }
        }

    override val cameraView: PreviewView
        get() {
            return binding?.cameraPreview!!
        }


    override fun onQrCode(result: BasicInformation) {
        basicInformation = result
        runnableDetected?.run()
    }

    override fun onError(message: String) {

    }

    companion object{
        private val TAG = CameraQrCodeFragment::class.java.name
    }
}