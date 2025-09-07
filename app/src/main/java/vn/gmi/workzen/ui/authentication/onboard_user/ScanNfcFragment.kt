package vn.gmi.workzen.ui.authentication.onboard_user

import android.annotation.SuppressLint
import android.content.Context
import android.nfc.Tag
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import io.reactivex.disposables.CompositeDisposable

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jmrtd.lds.icao.DG13File
import org.jmrtd.lds.icao.DG1File
import org.jmrtd.lds.icao.DG2File
import org.jmrtd.lds.icao.MRZInfo
import vn.mobile.verifysdk.card.CardAccessType
import vn.mobile.verifysdk.card.CardService
import vn.mobile.verifysdk.card.ECallback
import vn.mobile.verifysdk.card.ECardNfcError
import vn.mobile.verifysdk.card.StateNfc
import vn.mobile.verifysdk.data.BasicInformation
import vn.mobile.verifysdk.data.EPassport
import vn.mobile.verifysdk.data.toPersonDetails
import vn.mobile.verifysdk.mlkit.TypeMrz
import vn.mobile.verifysdk.utils.NfcDocumentTag
import vn.mobile.verifysdk.utils.NfcUtils
import vn.gmi.workzen.databinding.FragmentScanNfcBinding
import vn.gmi.workzen.utils.IntentData

class ScanNfcFragment : Fragment() {

    private var _binding: FragmentScanNfcBinding? = null
    private val binding get() = _binding
    private var mrzInfo: MRZInfo? = null
    private var basicInformation: BasicInformation? = null

    private var nfcFragmentListener: NfcFragmentListener? = null
    private var handleNfc: NfcDocumentTag? = null
    private var mHandler = Handler(Looper.getMainLooper())
    private var ePassport:EPassport?=null
    private var disposable = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScanNfcBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = arguments

        basicInformation =
            arguments?.getSerializable(IntentData.KEY_QRCODE_INFO) as BasicInformation?
        if (arguments!!.containsKey(IntentData.KEY_MRZ_INFO)) {
            mrzInfo = arguments.getSerializable(IntentData.KEY_MRZ_INFO) as MRZInfo?
        }

        binding?.btnConfirm?.setOnClickListener { nfcFragmentListener?.onEidRead(ePassport) }
        handleNfc = NfcDocumentTag(CardAccessType.BAC)
    }


    private var eCallback = object : ECallback {
        override fun onReadStart() {
            mHandler.post { binding?.lavAnimScan?.playAnimation() }
        }

        override fun onReadFinish() {
           mHandler.post {  binding?.lavAnimScan?.pauseAnimation() }
        }

        override fun onError(message: String?, code: ECardNfcError) {
            handleExceptionNfc(message)
        }

        override fun onSuccess(ePassport: EPassport?) {
            this@ScanNfcFragment.ePassport = ePassport
            binding!!.btnConfirm.visibility = View.VISIBLE
            mHandler.postDelayed({
                binding!!.btnConfirm.visibility = View.VISIBLE },1000)
        }

        override fun onReading(state: StateNfc) {
            super.onReading(state)

        }

        override fun onReadState(state: StateNfc, data: Any?) {
            super.onReadState(state, data)
            mHandler.post {
                if(state == StateNfc.DG1){
                    disPlayData(data)
                }else if(state == StateNfc.DG13){
                    displayDG13(data)
                }
            }
        }
    }

    fun handleNfcTag(tag: Tag) {
        val subscribe =  CardService.readChipNfc(
            requireContext(),
            tag,
            mrzInfo!!,
            basicInformation,
            CardAccessType.MRZ,
            eCallback)
        disposable.add(subscribe)

    }


    private fun handleExceptionNfc(msg:String?){
        Log.e(TAG, "$msg")

    }



    @SuppressLint("SetTextI18n")
    private fun disPlayData(data:Any?){
        try{
            val dG1File = data as DG1File
            val parser = dG1File.toPersonDetails()
            binding!!.llCard.tvDocumentNumber.text = parser.documentNumber
            binding!!.llCard.tvGender.text = parser.gender?.name
            binding!!.llCard.tvBirthday.text = parser.dateOfBirth
            binding!!.llCard.tvDateOfExpiration.text = parser.dateOfExpiry
            binding!!.llCard.tvFullName.text = "${parser.primaryIdentifier} ${parser.secondaryIdentifier}"
        }catch (e:Exception){
            Log.e(TAG, "${e.message}")
        }
    }

    private fun displayDG13(data: Any?){
        try {
            val dg13 = data as DG13File
            binding!!.llCard.tvPlaceOfResidence.text = dg13.placeOfResidence
            binding!!.llCard.tvGender.text = dg13.gender
            binding!!.llCard.tvDocumentNumber.text = dg13.eidNumber
            binding!!.llCard.tvGender.text = dg13.gender
            binding!!.llCard.tvDateOfExpiration.text = dg13.dateOfExpiry
            binding!!.llCard.tvBirthday.text = dg13.dateOfBirth
            binding!!.llCard.tvFullName.text = dg13.fullName
        }catch (e:Exception){

        }
    }

    //=============================================
    //          region Life cycle
    //=============================================

    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.clear()
        handleNfc = null
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity = activity
        if (activity is NfcFragmentListener) {
            nfcFragmentListener = activity
        }
    }

    override fun onDetach() {
        nfcFragmentListener = null
        super.onDetach()
    }

    override fun onPause() {
        super.onPause()
        nfcFragmentListener?.onDisableNfc()
    }

    override fun onResume() {
        super.onResume()
        nfcFragmentListener?.onEnableNfc()
        binding?.btnConfirm?.visibility = View.GONE
    }




    //===============================================
    //  endregion Life cycle
    //===============================================



    interface NfcFragmentListener {
        fun onEnableNfc()
        fun onDisableNfc()
        fun onEidRead(ePassport: EPassport?)
    }

    companion object {
        private val TAG = ScanNfcFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(mrzInfo: MRZInfo?,value: BasicInformation?) =
            ScanNfcFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(IntentData.KEY_QRCODE_INFO, value)
                    putSerializable(IntentData.KEY_MRZ_INFO, mrzInfo)
                }
            }
    }
}