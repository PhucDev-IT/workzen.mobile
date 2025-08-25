package vn.gmi.workzen.ui.account.topup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.RvCheckBoxWalletLinkedAdapter
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.core.extensions.addCurrencyFormatter
import vn.gmi.workzen.databinding.FragmentTopupBinding
import vn.gmi.workzen.domain.entity.enums.WalletType
import vn.gmi.workzen.domain.usecase.GetLinkedWalletLocalUseCase
import vn.gmi.workzen.domain.usecase.GetLinkedWalletsUseCase
import vn.gmi.workzen.ui.account.BottomSheetAccountPaymentFragment
import vn.gmi.workzen.ui.account.WalletListener

import vn.gmi.workzen.ui.account.banking.SelectBankingActivity
import vn.gmi.workzen.utils.IntentData
import vn.gmi.workzen.utils.MySharedPreferences
import javax.inject.Inject


@AndroidEntryPoint
class TopupFragment (): Fragment() {
    private lateinit var binding: FragmentTopupBinding
    private lateinit var adapter: RvCheckBoxWalletLinkedAdapter
    private var callback: WalletListener? = null

    @Inject lateinit var getLinkedWalletsUseCase: GetLinkedWalletsUseCase
    @Inject lateinit var getLinkedWalletLocalUseCase: GetLinkedWalletLocalUseCase


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


        adapter = RvCheckBoxWalletLinkedAdapter()
        binding.rvLinkedWallet.adapter = adapter
        binding.rvLinkedWallet.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        getLinkedWallets()
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

        binding.edtAmount.addCurrencyFormatter()
    }

    private fun getLinkedWallets(){
        lifecycleScope.launch {
            try {
                val userId = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_USER_ID)

                val local = getLinkedWalletLocalUseCase.invoke(userId ?: "")
                if(local.isNotEmpty()){
                    adapter.addAll(local)
                }
                val result = getLinkedWalletsUseCase.invoke(userId ?: "")
                adapter.addAll(result)

                callback?.onReloadWalletSystem(result)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = parentFragment as? WalletListener
    }
    override fun onDetach() {
        super.onDetach()
        callback = null
    }

}