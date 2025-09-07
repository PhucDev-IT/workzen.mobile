package vn.gmi.workzen.ui.account

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBallShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorFrameShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorPixelShape
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import vn.gmi.workzen.R
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.databinding.ViewPaymentAccountBinding
import vn.gmi.workzen.domain.entity.enums.WalletType
import vn.gmi.workzen.domain.entity.wallet.LinkedWalletEntity
import vn.gmi.workzen.domain.usecase.GetLinkedWalletsUseCase
import vn.gmi.workzen.ui.account.model.QrStyleOption
import vn.gmi.workzen.ui.account.topup.TopupFragment
import vn.gmi.workzen.ui.account.transaction.TransactionFragment
import vn.gmi.workzen.ui.account.withdraw.WithDrawFragment
import vn.gmi.workzen.utils.FormatUtils
import vn.gmi.workzen.utils.MySharedPreferences
import vn.gmi.workzen.utils.Utils
import java.math.BigDecimal
import javax.inject.Inject

@AndroidEntryPoint
class BottomSheetAccountPaymentFragment : BottomSheetDialogFragment(), WalletListener {
    private lateinit var binding: ViewPaymentAccountBinding



    @Inject lateinit var getLinkedWalletsUseCase: GetLinkedWalletsUseCase

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


     fun displayData(wallets:List<LinkedWalletEntity>?){
        if(wallets.isNullOrEmpty()) return

        val systemWallet = wallets!!.find { it.walletInfo?.type == WalletType.SYSTEM_WALLET.name }
        systemWallet?.let {
            binding.tvBalance.text = FormatUtils.numberFormat.format(BigDecimal(systemWallet.balance))
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


    override fun onReloadWalletSystem(wallets: List<LinkedWalletEntity>) {
        displayData(wallets)
    }

}

interface WalletListener{
    fun onReloadWalletSystem(wallets: List<LinkedWalletEntity>)
}