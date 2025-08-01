package vn.gmi.workzen.ui.account.banking

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import vn.gmi.workzen.R
import vn.gmi.workzen.ui.account.model.BankingItem

class SelectBankingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_select_banking)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initBanking(){
        val list = mutableListOf<BankingItem>()

        list.add(BankingItem("1",R.drawable.logo_vcb,"Ngân hàng Thương mại cổ phần Ngoại thương Việt Nam Vietcombank","Vietcombank"))
        list.add(BankingItem("2",R.drawable.logo_techcombank,"Ngân hàng TMCP Kỹ thương Việt Nam","Techcombank"))
        list.add(BankingItem("3",R.drawable.logo_mb_bank,"MB Ngân hàng quân đội","MB"))
        list.add(BankingItem("4",R.drawable.logo_msb,"Ngân hàng TMCP Hàng Hải Việt Nam","MSB"))
        list.add(BankingItem("5",R.drawable.logo_bidv,"Ngân hàng TMCP Đầu tư và Phát triển Việt Nam (BIDV)","BIDV"))
        list.add(BankingItem("6",R.drawable.logo_vpbank,"Ngân hàng TMCP Việt Nam Thịnh Vượng","VPBank"))
        list.add(BankingItem("7",R.drawable.logo_viettinbank,"Ngân hàng Thương mại Cổ phần Công Thương Việt Nam","Vietinbank"))
        list.add(BankingItem("8",R.drawable.logo_timo,"Ngân hàng số by BVBank","Timo"))
        list.add(BankingItem("9",R.drawable.logo_acb,"Ngân hàng Thương Mại Cổ Phần Á Châu","ACB"))
        list.add(BankingItem("10",R.drawable.logo_vib,"Ngân hàng quốc tế VIB","VIB"))
        list.add(BankingItem("11",R.drawable.logo_napas,"National Payment Corporation of Vietnam","NAPAS"))
        list.add(BankingItem("11",R.drawable.logo_pvcombank,"Ngân hàng TMCP Đại Chúng Việt Nam","PVcomBank"))
        list.add(BankingItem("11",R.drawable.logo_cb_ngan_hang_xay_dung,"Ngân hàng xây dựng","CBBank"))
    }
}