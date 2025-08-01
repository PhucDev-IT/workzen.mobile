package vn.gmi.workzen.ui.account

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBallShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorFrameShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorPixelShape
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import vn.gmi.workzen.R
import vn.gmi.workzen.databinding.ViewPaymentAccountBinding
import vn.gmi.workzen.ui.account.model.QrStyleOption
import vn.gmi.workzen.ui.account.topup.TopupFragment
import vn.gmi.workzen.ui.account.transaction.TransactionFragment
import vn.gmi.workzen.ui.account.withdraw.WithDrawFragment

class BottomSheetAccountPaymentFragment : BottomSheetDialogFragment() {
    private lateinit var binding: ViewPaymentAccountBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ViewPaymentAccountBinding.inflate(inflater, container, false)

        binding.llMyQr.setOnClickListener {
            val intent = Intent(requireContext(), MyQrCodeActivity::class.java)
            startActivity(intent)

        }
        setListener()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null){
            showTab(0)
        }
    }


    private fun setListener(){
        binding.tabTopup.setOnClickListener {
            selectTab(binding.tabTopup)
            showTab(0)
        }

        binding.tabWithdraw.setOnClickListener {
            selectTab(binding.tabWithdraw)
            showTab(1)
        }

        binding.tabTransaction.setOnClickListener {
            selectTab(binding.tabTransaction)
            showTab(2)
        }

        binding.icClose.setOnClickListener {
            dismiss()
        }


    }

    private fun selectTab(selected: TextView) {
        val tabs = listOf(binding.tabTopup, binding.tabWithdraw, binding.tabTransaction)

        for (tab in tabs) {
            if (tab == selected) {
                tab.setBackgroundResource(R.drawable.bg_corner_border)
                tab.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
            } else {
                tab.setBackgroundColor(Color.TRANSPARENT)
                tab.setTextColor(ContextCompat.getColor(requireContext(), R.color.textSecondary))
            }
        }
    }


    private fun showTab(tab: Int) {
        val fragment = when (tab) {
            0 -> TopupFragment()
            1 -> WithDrawFragment()
            2 -> TransactionFragment()
            else -> TopupFragment()
        }

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_content, fragment)
            .commit()
    }


}