package vn.gmi.workzen.ui.account.banking

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import vn.gmi.workzen.R
import vn.gmi.workzen.data.models.request.wallet.CreateLinkPaymentReq
import vn.gmi.workzen.databinding.ActivityInputInformationBankingBinding
import vn.gmi.workzen.domain.usecase.RequestLinkWalletUseCase
import vn.gmi.workzen.ui.main.MainActivity
import javax.inject.Inject


@AndroidEntryPoint
class InputInformationBankingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputInformationBankingBinding
    private lateinit var walletId: String
    private lateinit var shortName: String
    private lateinit var logo: String
    private lateinit var name: String

    @Inject lateinit var requestLinkWalletUseCase: RequestLinkWalletUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInputInformationBankingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getStringExtra("id")
        val shortName = intent.getStringExtra("shortName")
        val logo = intent.getStringExtra("logo")
        val name = intent.getStringExtra("name")

        if (id == null) finish()

        this.walletId = id!!
        this.shortName = shortName ?: ""
        this.logo = logo ?: ""
        this.name = name ?: ""


        initUI()
        setListener()
    }

    private fun initUI() {
        binding.llHeader.title.text = "Ngân hàng $shortName"
        Glide.with(this).load(logo).into(binding.logoBank)
        binding.tvHeader.text = name
    }

    private fun setListener() {
        binding.llHeader.root.setNavigationOnClickListener { finish() }

        binding.btnContinue.setOnClickListener {
            requestLinkedWallet()
        }
    }

    private fun requestLinkedWallet(){
        val eidNumber = binding.edtEidNumber.text.toString().trim()
        val idCard = binding.edtIdCard.text.toString().trim()
        val ownerName = binding.edtOwnerName.text.toString().trim()
        val dateCard = binding.edtDateCard.text.toString().trim()


        if(eidNumber.isEmpty() || idCard.isEmpty() || ownerName.isEmpty() || dateCard.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        val req = CreateLinkPaymentReq()
        req.eidNumber = eidNumber
        req.idCard = idCard
        req.ownerName = ownerName
        req.date = dateCard
        req.walletId = walletId

        lifecycleScope.launch {
            try{
                val result = requestLinkWalletUseCase.invoke(req)
                Toast.makeText(this@InputInformationBankingActivity, "Liên kết thành công", Toast.LENGTH_SHORT).show()

                val intent: Intent = Intent(this@InputInformationBankingActivity, MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
                finish()

            }catch (e: Exception){
                e.printStackTrace()
            }
        }

    }
}