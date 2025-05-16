package vn.gmi.workzen.vision

import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import vn.mobile.verifysdk.data.BasicInformation

/**
 * Class handle detect and parser QRCODE
 * * If not transmit special data to constructor, default SDK will detect all type QRCODE
 * * With options, you can set zoom camera.
 * ```kotlin
 * private var options = BarcodeScannerOptions.Builder()
 *         .enableAllPotentialBarcodes()
 *         .setBarcodeFormats(
 *             Barcode.FORMAT_QR_CODE
 *         )
 * options.setZoomSuggestionOptions(ZoomSuggestionOptions.Builder(zoomCallback).build())
 * scanner = BarcodeScanning.getClient(options.build())
 * ```
 */
class QRCodeAndBarcodeAnalyzer(private val callback:QRCodeListener,private var options: BarcodeScannerOptions?=null): ImageAnalysis.Analyzer {

    private var barcodeScanner = if(options==null){
        val default = BarcodeScannerOptions.Builder()
            .enableAllPotentialBarcodes()
            .setBarcodeFormats(
                Barcode.FORMAT_ALL_FORMATS
            )
        BarcodeScanning.getClient(default.build())
    }else {
        BarcodeScanning.getClient(options!!)
    }

    @OptIn(ExperimentalGetImage::class)
    private fun handleImage(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val result = barcodeScanner.process(image)
                .addOnSuccessListener { barcodes ->
                    processBarcodes(barcodes)
                }
                .addOnFailureListener {
                    Log.e(TAG, "Fail: ${it.message}")
                }.addOnCompleteListener {
                    imageProxy.close()
                    mediaImage.close()
                }
        }
    }

    override fun analyze(image: ImageProxy) {
        handleImage(image)
    }

    private fun processBarcodes(barcodes: List<Barcode>) {
        if (barcodes.isNotEmpty()) {
            for (barcode in barcodes) {
                val displayValue = barcode.displayValue
                if(!displayValue.isNullOrEmpty()){
                    val result = parserQrCode(displayValue)
                    callback.onQrCode(result)
                    break
                }else{
                    callback.onError("Can not parser data from this QRCODE")
                }
            }

        }else{
            callback.onError("Not found valid QRCODE")
        }
    }

    /**
     * return BasicInformation
     * @param result: data string read from citizen identification card
     */
    private fun parserQrCode(result: String): BasicInformation {
        if (result.isEmpty()) {
            throw NullPointerException("result is empty")
        }

        val parser = result.split("|")
        if (parser.size < 7) throw Exception("Data invalid")
        val informationQrCode = BasicInformation().apply {
            eidNumber = parser[0].trim()
            oldEidNumber = parser[1].trim()
            fullName = parser[2].trim()
            dateOfBirth = parser[3].trim()
            gender = parser[4].trim()
            placeOfResidence = parser[5].trim()
            dateOfIssue = parser[6].trim()
        }
        if(parser.size == 11){
            informationQrCode.fatherName = parser[8].trim()
            informationQrCode.motherName = parser[9].trim()
        }

        return informationQrCode
    }


    companion object {
        private val TAG = QRCodeAndBarcodeAnalyzer::class.java.name
    }

    interface QRCodeListener{
        fun onQrCode(result:BasicInformation)
        fun onError(message:String)
    }
}
