package vn.gmi.workzen.ui.authentication.onboard_user

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import vn.gmi.workzen.R
import vn.gmi.workzen.core.base.CoreActivity
import vn.gmi.workzen.utils.IntentData
import vn.mobile.verifysdk.data.BasicInformation

class ScanQrCodeActivity : CoreActivity() {
    private var isDetected = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_scan_qr_code)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val cameraQrCodeFragment = CameraQrCodeFragment()
        cameraQrCodeFragment.setRunnableDetected {
            cameraQrCodeFragment.getBasicInformation()?.let { onResultQrCode(it) }
        }
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, cameraQrCodeFragment)
                .commitNow()
        }
    }

    private fun onResultQrCode(basicInformation: BasicInformation){
        if(isDetected) return
        isDetected = true
        val intent = Intent(this,NfcActivity::class.java)
        intent.putExtra(IntentData.KEY_QRCODE_INFO,basicInformation)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        isDetected = false
        //requestPermission(listOf())
    }

}