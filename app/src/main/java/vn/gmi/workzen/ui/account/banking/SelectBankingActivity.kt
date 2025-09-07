package vn.gmi.workzen.ui.account.banking

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Consumer
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.RvSelectorBankingAdapter
import vn.gmi.workzen.databinding.ActivitySelectBankingBinding
import vn.gmi.workzen.domain.entity.enums.WalletType
import vn.gmi.workzen.domain.entity.wallet.WalletEntity
import vn.gmi.workzen.domain.usecase.GetWalletsUseCase
import vn.gmi.workzen.ui.account.model.BankingItem
import vn.gmi.workzen.utils.IntentData
import javax.inject.Inject

@AndroidEntryPoint
class SelectBankingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectBankingBinding
    private lateinit var adapter: RvSelectorBankingAdapter
    private lateinit var walletType: WalletType

    @Inject lateinit var getWalletsUseCase: GetWalletsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySelectBankingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if(!intent.hasExtra(IntentData.KEY_VIEW)) finish()
        walletType = intent.getSerializableExtra(IntentData.KEY_VIEW) as WalletType

        initUI()
        setListener()
        getWallets()

    }

    private fun initUI(){
        binding.llHeader.title.text = if(walletType == WalletType.BANKING) "Chọn ngân hàng" else if(walletType == WalletType.CARD_PAYMENT) "Chọn thẻ" else "Chọn ví điện tử"
        adapter = RvSelectorBankingAdapter(object : Consumer<WalletEntity>{
            override fun accept(value: WalletEntity) {
                val intent = Intent(this@SelectBankingActivity, if(walletType == WalletType.BANKING) InputInformationBankingActivity::class.java else  InputInfoCardPaymentActivity::class.java)
                intent.putExtra("id", value.id)
                intent.putExtra("shortName", value.shortName)
                intent.putExtra("logo", value.logo)
                intent.putExtra("name", value.name)
                startActivity(intent)
            }
        })

        binding.rvBanking.adapter = adapter
        binding.rvBanking.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        binding.rvBanking.addItemDecoration(dividerItemDecoration)
    }


    private fun getWallets(){
        lifecycleScope.launch {
           try{
               val list = getWalletsUseCase.invoke(Unit)
               val banks= if(walletType == WalletType.BANKING) list.filter { it.type == WalletType.BANKING.name } else  if(walletType == WalletType.CARD_PAYMENT)  list.filter { it.type == WalletType.CARD_PAYMENT.name } else null
               banks?.let { adapter.addAll(banks) }
           }catch (e: Exception){
               e.printStackTrace()
           }
        }
    }

    private fun initBanking() : List<BankingItem>{
        val list = mutableListOf<BankingItem>()

        list.add(BankingItem("1",R.drawable.logo_vcb,"Ngân hàng Thương mại cổ phần Ngoại thương Việt Nam","Vietcombank"))
        list.add(BankingItem("2",R.drawable.logo_techcombank,"Ngân hàng TMCP Kỹ thương Việt Nam","Techcombank"))
        list.add(BankingItem("3",R.drawable.logo_mb_bank,"MB Ngân hàng quân đội","MB"))
        list.add(BankingItem("4",R.drawable.logo_msb,"Ngân hàng TMCP Hàng Hải Việt Nam","MSB"))
        list.add(BankingItem("5",R.drawable.logo_bidv,"Ngân hàng TMCP Đầu tư và Phát triển Việt Nam","BIDV"))
        list.add(BankingItem("6",R.drawable.logo_vpbank,"Ngân hàng TMCP Việt Nam Thịnh Vượng","VPBank"))
        list.add(BankingItem("7",R.drawable.logo_viettinbank,"Ngân hàng Thương mại Cổ phần Công Thương Việt Nam","Vietinbank"))
        list.add(BankingItem("8",R.drawable.logo_timo,"Ngân hàng số by BVBank","Timo"))
        list.add(BankingItem("9",R.drawable.logo_acb,"Ngân hàng Thương Mại Cổ Phần Á Châu","ACB"))
        list.add(BankingItem("10",R.drawable.logo_vib,"Ngân hàng quốc tế VIB","VIB"))
        list.add(BankingItem("11",R.drawable.logo_napas,"National Payment Corporation of Vietnam","NAPAS"))
        list.add(BankingItem("11",R.drawable.logo_pvcombank,"Ngân hàng TMCP Đại Chúng Việt Nam","PVcomBank"))
        list.add(BankingItem("11",R.drawable.logo_cb_ngan_hang_xay_dung,"Ngân hàng xây dựng","CBBank"))

        return list
    }

    private fun setListener(){
        binding.llHeader.root.setNavigationOnClickListener { finish() }
    }
}