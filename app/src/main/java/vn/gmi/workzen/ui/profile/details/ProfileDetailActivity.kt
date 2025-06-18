package vn.gmi.workzen.ui.profile.details

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayoutMediator
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.ProfileTabPagerAdapter
import vn.gmi.workzen.databinding.ActivityProfileDetailBinding

class ProfileDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.viewPager.adapter = ProfileTabPagerAdapter(this)
        TabLayoutMediator( binding.tabLayout,  binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Thông tin chung"
                1 -> "Sơ yếu lý lịch"
                2 -> "Công việc"
                else -> ""
            }
        }.attach()
    }
}