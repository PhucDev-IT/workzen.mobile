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
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.jmrtd.lds.icao.MRZInfo
import vn.gmi.workzen.MainActivity
import vn.gmi.workzen.R
import vn.gmi.workzen.core.constants.AppToast
import vn.gmi.workzen.core.constants.DialogLoading
import vn.gmi.workzen.databinding.ActivityNfcBinding
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.data.models.request.user.OnboardUserReqModel
import vn.gmi.workzen.domain.usecase.UpdateIdentificationUseCase
import vn.gmi.workzen.networks.rest.networkCallback
import vn.gmi.workzen.networks.rest.onError
import vn.gmi.workzen.networks.rest.onSuccess
import vn.gmi.workzen.utils.IntentData
import vn.gmi.workzen.utils.Utils
import vn.mobile.verifysdk.card.CardService
import vn.mobile.verifysdk.data.BasicInformation
import vn.mobile.verifysdk.data.EPassport
import javax.inject.Inject

@AndroidEntryPoint
class NfcActivity : AppCompatActivity() , ScanNfcFragment.NfcFragmentListener{

    private var mrzInfo: MRZInfo? = null
    private lateinit var binding: ActivityNfcBinding
    private var isReadSuccess = false
    private var nfcAdapter: NfcAdapter?=null
    private var pendingIntent: PendingIntent? = null
    private var mediaPlayer: MediaPlayer? = null
    private var basicInformation: BasicInformation? = null

    @Inject lateinit var userUseCase: UpdateIdentificationUseCase

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
        onDisableNfc()
        verifyUser(ePassport!!)
    }

    private fun verifyUser(ePassport: EPassport){
        val model = OnboardUserReqModel().apply {
            eidNumber = ePassport.personOptionalDetails?.eidNumber
            fullName = ePassport.personOptionalDetails?.fullName
            gender = ePassport.personOptionalDetails?.gender
            dateOfBirth = ePassport.personOptionalDetails?.dateOfBirth
            dateOfIssue = ePassport.personOptionalDetails?.dateOfIssue
            dateOfExpiry = ePassport.personOptionalDetails?.dateOfExpiry
            nationality = ePassport.personOptionalDetails?.nationality
            ethnicity = ePassport.personOptionalDetails?.ethnicity
            religion = ePassport.personOptionalDetails?.religion
            placeOfOrigin = ePassport.personOptionalDetails?.placeOfOrigin
            placeOfResidence = ePassport.personOptionalDetails?.placeOfResidence
            personalIdentification = ePassport.personOptionalDetails?.personalIdentification
            fatherName = ePassport.personOptionalDetails?.fatherName
            motherName = ePassport.personOptionalDetails?.motherName
            spouseName = ePassport.personOptionalDetails?.spouseName
            oldEidNumber = ePassport.personOptionalDetails?.oldEidNumber
            dg2 = Utils.bitmapToBase64(ePassport.faceImage!!)
        }

        lifecycleScope.launch {
            try {
                DialogLoading.showLoading(this@NfcActivity)
                val result = userUseCase.invoke(model)
                AppToast.showSuccess(this@NfcActivity, "Xác thực thành công")
                startActivity(Intent(this@NfcActivity, MainActivity::class.java))
                finishAffinity()
            }catch (e:Exception){
                Log.e(TAG,"Error: ${e.message}")
                AppToast.showError(this@NfcActivity,"Xác thực không thành công")
            }finally {
                DialogLoading.hideLoading()
            }
        }
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