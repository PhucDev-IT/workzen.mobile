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
import vn.gemini.passport.R
import vn.gemini.passport.common.IntentData
import vn.gemini.passport.databinding.FragmentScanNfcBinding
import vn.gemini.passport.viewmodel.NfcViewModel

class ScanNfcFragment : Fragment() {

    private var _binding: FragmentScanNfcBinding? = null
    private val binding get() = _binding
    private var mrzInfo: MRZInfo? = null
    private var basicInformation: BasicInformation? = null
    private var canKey:String?=null
    private var modeScan: CardAccessType?=null
    private var nfcFragmentListener: NfcFragmentListener? = null
    private var handleNfc: NfcDocumentTag? = null
    private var mHandler = Handler(Looper.getMainLooper())
    private lateinit var viewModel: NfcViewModel
    private var typeCard :String  = TypeMrz.TD1.name
    private var disposable = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScanNfcBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[NfcViewModel::class.java]
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = arguments
        modeScan = arguments!!.getSerializable(IntentData.KEY_MODE_SCAN_NFC) as CardAccessType
        canKey = arguments!!.getSerializable(IntentData.KEY_CAN_KEY) as String?
        basicInformation =
            arguments.getSerializable(IntentData.KEY_QRCODE_INFO) as BasicInformation?
        if (arguments!!.containsKey(IntentData.KEY_MRZ_INFO)) {
            mrzInfo = arguments.getSerializable(IntentData.KEY_MRZ_INFO) as MRZInfo?
        }
        typeCard = arguments.getString(IntentData.KEY_TYPE_MRZ_INFO)?: TypeMrz.TD1.name

        if(typeCard == TypeMrz.PASSPORT.name){
            binding?.llContainsCardEid?.visibility = View.GONE
            binding?.llContainsCardPassport?.visibility = View.VISIBLE
        }else{
            binding?.llContainsCardEid?.visibility = View.VISIBLE
            binding?.llContainsCardPassport?.visibility = View.GONE
        }

        viewModel.isFinish.observe(viewLifecycleOwner){b->
            if(b){
                binding!!.lavAnimScan.pauseAnimation()
            }else{
                binding!!.lavAnimScan.playAnimation()
            }
        }

        handleNfc = NfcDocumentTag(modeScan!!)
    }


    private var eCallback = object : ECallback {
        override fun onReadStart() {
            viewModel.setGuide(getString(R.string.nfc_guide))
            viewModel.setIsFinish(false)
        }

        override fun onReadFinish() {
            viewModel.setIsFinish(true)
        }

        override fun onError(message: String?, code: ECardNfcError) {
            handleExceptionNfc(message)
        }

        override fun onSuccess(ePassport: EPassport?) {
            viewModel.setIsFinish(true)
            nfcFragmentListener?.onEidRead(ePassport)
        }

        override fun onReading(state: StateNfc) {
            super.onReading(state)
            viewModel.setGuide(state.name)
        }

        override fun onReadState(state: StateNfc, data: Any?) {
            super.onReadState(state, data)
            mHandler.post {
                if(state == StateNfc.DG1){
                    disPlayData(data)
                }else if(state == StateNfc.DG2){
                    displayDG2(data)
                }else if(state == StateNfc.DG13){
                    displayDG13(data)
                }
            }
        }
    }

    fun handleNfcTag(tag: Tag) {
        val subscribe = if(modeScan == CardAccessType.BAC){
            CardService.readChipNfc(
                requireContext(),
                tag,
                mrzInfo!!,
                basicInformation,
                CardAccessType.MRZ,
                eCallback)
        }else{
            CardService.readChipNfc(
                requireContext(),
                tag,
                canKey?:"",eCallback)
        }
        disposable.add(subscribe)

    }


    private fun handleExceptionNfc(msg:String?){
        Log.e(TAG, "$msg")
        viewModel.setGuide(getString(R.string.nfc_guide))
        viewModel.setIsFinish(false)
    }



    @SuppressLint("SetTextI18n")
    private fun disPlayData(data:Any?){
        try{
            val dG1File = data as DG1File
            val parser = dG1File.toPersonDetails()

            if(typeCard != TypeMrz.PASSPORT.name){
                binding!!.llCard.tvDocumentNumber.text = parser.documentNumber
                binding!!.llCard.tvGender.text = parser.gender?.name
                binding!!.llCard.tvBirthday.text = parser.dateOfBirth
                binding!!.llCard.tvDateOfExpiration.text = parser.dateOfExpiry
                binding!!.llCard.tvFullName.text = "${parser.primaryIdentifier} ${parser.secondaryIdentifier}"
            }else{
                binding!!.llCardPassport.tvGender.text = parser.gender?.name
                binding!!.llCardPassport.tvBirthday.text = parser.dateOfBirth
                binding!!.llCardPassport.tvNationality.text = parser.nationality
                binding!!.llCardPassport.tvFullName.text = "${parser.primaryIdentifier} ${parser.secondaryIdentifier}"

            }
        }catch (e:Exception){
            Log.e(TAG, "${e.message}")
        }
    }

    private fun displayDG13(data: Any?){
        try {
            val dg13 = data as DG13File
            binding!!.llCard.tvPlaceOfResidence.text = dg13.placeOfResidence
        }catch (e:Exception){

        }
    }
    private fun displayDG2(data: Any?){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                if(typeCard == TypeMrz.PASSPORT.name){
                    val dg2 = data as DG2File
                    val image = NfcUtils.retrieveFaceImage(dg2)
                    withContext(Dispatchers.Main){
                        binding!!.llCardPassport.imgDg2.setImageBitmap(image)
                    }
                }
            }
        }catch (e:Exception){
            Log.e(TAG, "${e.message}")
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
        fun newInstance(mrzInfo: MRZInfo?, type:String,value: BasicInformation?, canKey:String?,mode:CardAccessType) =
            ScanNfcFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(IntentData.KEY_QRCODE_INFO, value)
                    putSerializable(IntentData.KEY_MRZ_INFO, mrzInfo)
                    putString(IntentData.KEY_CAN_KEY, canKey)
                    putSerializable(IntentData.KEY_MODE_SCAN_NFC, mode)
                    putString(IntentData.KEY_TYPE_MRZ_INFO,type)
                }
            }
    }
}