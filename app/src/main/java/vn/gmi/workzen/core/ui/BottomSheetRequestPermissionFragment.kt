package vn.gmi.workzen.core.ui

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import vn.gmi.workzen.databinding.BottomSheetRequestPermissionBinding

class BottomSheetRequestPermissionFragment : BottomSheetDialogFragment() {
    private  var binding: BottomSheetRequestPermissionBinding?=null
    private var listener: PermissionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetRequestPermissionBinding.inflate(inflater,container,false)
        binding?.tvConfirm?.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        binding?.tvCancel?.setOnClickListener { this.dismiss() }

        return binding?.root
    }

//    override fun onStart() {
//        super.onStart()
//        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
//        bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
//        val behavior = BottomSheetBehavior.from(bottomSheet as View)
//        behavior.state = BottomSheetBehavior.STATE_EXPANDED
//    }


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // ✅ Quyền được cấp
                Toast.makeText(requireContext(), "Đã cấp quyền", Toast.LENGTH_SHORT).show()
                dismiss()
                listener?.onPermissionGranted()
            } else {
                // ❌ Từ chối quyền
                Toast.makeText(requireContext(), "Chưa cấp quyền", Toast.LENGTH_SHORT).show()
                listener?.onPermissionDenied()
            }
        }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    interface PermissionListener{
        fun onPermissionGranted()
        fun onPermissionDenied()
    }
}