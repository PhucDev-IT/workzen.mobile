package vn.gmi.workzen.ui.authentication.onboard_user

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.media.MediaPlayer
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.jmrtd.lds.icao.MRZInfo
import vn.gmi.workzen.R
import vn.gmi.workzen.databinding.ActivityNfcBinding
import vn.gmi.workzen.networks.models.request.OnboardUserReqModel
import vn.gmi.workzen.utils.IntentData
import vn.gmi.workzen.utils.Utils
import vn.mobile.verifysdk.card.CardAccessType
import vn.mobile.verifysdk.card.CardService
import vn.mobile.verifysdk.data.BasicInformation
import vn.mobile.verifysdk.data.EPassport
import vn.mobile.verifysdk.mlkit.TypeMrz
import vn.mobile.verifysdk.utils.StringUtils


class NfcActivity : AppCompatActivity() , ScanNfcFragment.NfcFragmentListener{

    private var mrzInfo: MRZInfo? = null
    private lateinit var binding: ActivityNfcBinding
    private var isReadSuccess = false
    private var nfcAdapter: NfcAdapter?=null
    private var pendingIntent: PendingIntent? = null
    private var mediaPlayer: MediaPlayer? = null
    private var basicInformation: BasicInformation? = null


    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        binding = ActivityNfcBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (intent.hasExtra(IntentData.KEY_QRCODE_INFO)) {
            basicInformation =
                intent.getSerializableExtra(IntentData.KEY_QRCODE_INFO) as BasicInformation
            mrzInfo =
                if (basicInformation!!.dateOfExpiry == null) {
                    CardService.createMrz(
                        basicInformation!!.eidNumber,
                        basicInformation!!.dateOfBirth,
                        basicInformation!!.dateOfIssue!!
                    )
                } else {
                    CardService.createMrzInfo(
                        basicInformation!!.eidNumber,
                        basicInformation!!.dateOfBirth,
                        basicInformation!!.dateOfExpiry!!
                    )
                }
        }else {
            Toast.makeText(this,"Không thể hoàn thành tác vụ",Toast.LENGTH_SHORT).show()
            finish()
        }

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_UPDATE_CURRENT)
        }

        if (nfcAdapter == null) {
            Toast.makeText(this, getString(R.string.warning_no_nfc), Toast.LENGTH_LONG).show()
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.ding_sound)
        if (null == savedInstanceState) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container_nfc,
                    ScanNfcFragment.newInstance(mrzInfo,basicInformation),
                    TAG_NFC
                )
                .commit()
        }
        setListener()
    }


    private fun handleNfc(tag: Tag) {
        if (!isReadSuccess) {
            val fragmentByTag = supportFragmentManager.findFragmentByTag(TAG_NFC)
            if (fragmentByTag is ScanNfcFragment) {
                fragmentByTag.handleNfcTag(tag)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        if (NfcAdapter.ACTION_TAG_DISCOVERED == intent.action || NfcAdapter.ACTION_TECH_DISCOVERED == intent.action) {
            val fragmentByTag = supportFragmentManager.findFragmentByTag(TAG_NFC)
            if (fragmentByTag is ScanNfcFragment) {
                val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
                if (!isReadSuccess  && tag != null) {
                    playSoundBeep()
                    handleNfc(tag)
                }
            }
        }
    }


    override fun onEnableNfc() {
        if (nfcAdapter != null) {
            if (!nfcAdapter!!.isEnabled)
                showWirelessSettings()
            nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, null)
        }else{
            Toast.makeText(this, getString(R.string.warning_no_nfc), Toast.LENGTH_LONG).show()
        }
    }

    override fun onDisableNfc() {
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onEidRead(ePassport: EPassport?) {
        isReadSuccess = true
       // OnboardManager.ePassport = ePassport
        onDisableNfc()
//        val intent = Intent(this, VerifyNfcSuccessActivity::class.java)
//        startActivity(intent)
     //   finish()
    }


    private fun showWirelessSettings() {
        Toast.makeText(this, getString(R.string.warning_enable_nfc), Toast.LENGTH_LONG).show()
        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
        startActivity(intent)
    }


    private fun setListener() {
        binding.layoutHead.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun playSoundBeep() {
        if (mediaPlayer != null && !mediaPlayer?.isPlaying!!) {
            mediaPlayer?.start()
        }
    }
    override fun onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }

        super.onDestroy()
    }

    private fun verifyData(model:EPassport){
        val request = OnboardUserReqModel().apply {
            eidNumber = model.personOptionalDetails?.eidNumber
            fullName = model.personOptionalDetails?.fullName
            gender = model.personOptionalDetails?.gender
            dateOfBirth = model.personOptionalDetails?.dateOfBirth
            dateOfIssue = model.personOptionalDetails?.dateOfIssue
            dateOfExpiry = model.personOptionalDetails?.dateOfExpiry
            nationality = model.personOptionalDetails?.nationality
            ethnicity = model.personOptionalDetails?.ethnicity
            religion = model.personOptionalDetails?.religion
            placeOfOrigin = model.personOptionalDetails?.placeOfOrigin
            placeOfResidence = model.personOptionalDetails?.placeOfResidence
            personalIdentification = model.personOptionalDetails?.personalIdentification
            fatherName = model.personOptionalDetails?.fatherName
            motherName = model.personOptionalDetails?.motherName
            spouseName = model.personOptionalDetails?.spouseName
            oldEidNumber = model.personOptionalDetails?.oldEidNumber
            dg2 = Utils.bitmapToBase64(model.faceImage!!)
        }
    }


    companion object {
        private val TAG = NfcActivity::class.java.simpleName
        private val TAG_NFC = "TAG_NFC"
    }
}