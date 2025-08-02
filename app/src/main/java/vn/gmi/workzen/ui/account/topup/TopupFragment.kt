package vn.gmi.workzen.ui.account.topup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import vn.gmi.workzen.R
import vn.gmi.workzen.databinding.FragmentTopupBinding
import vn.gmi.workzen.domain.entity.enums.WalletType
import vn.gmi.workzen.ui.account.banking.SelectBankingActivity
import vn.gmi.workzen.utils.IntentData


class TopupFragment : Fragment() {
    private lateinit var binding: FragmentTopupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopupBinding.inflate(inflater, container, false)
        initUI()
        setListener()
        return binding.root

    }

    private fun initUI(){
        Glide.with(requireContext()).load("https://cdn-icons-png.flaticon.com/512/2132/2132804.png").into(binding.imgLogoBank)
        Glide.with(requireContext()).load("https://png.pngtree.com/png-clipart/20190904/original/pngtree-orange-wallet-icon-png-image_4462385.jpg").into(binding.imgLogoWallet)
        Glide.with(requireContext()).load("https://cdn-icons-png.freepik.com/512/349/349252.png").into(binding.imgLogoVisa)
    }

    private fun setListener(){
        binding.llAddBanking.setOnClickListener {
            val intent = Intent(requireContext(), SelectBankingActivity::class.java)
            intent.putExtra(IntentData.KEY_VIEW, WalletType.BANKING)
            startActivity(intent)
        }

        binding.llAddEwallet.setOnClickListener {
            Toast.makeText(requireContext(), "Chức năng đang phát triển", Toast.LENGTH_SHORT).show()
        }

        binding.llAddVisa.setOnClickListener {
            val intent = Intent(requireContext(), SelectBankingActivity::class.java)
            intent.putExtra(IntentData.KEY_VIEW, WalletType.CARD_PAYMENT)
            startActivity(intent)
        }

    }

}