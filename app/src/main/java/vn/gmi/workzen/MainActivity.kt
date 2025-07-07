package vn.gmi.workzen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import vn.gmi.workzen.data.database.RealmProvider
import vn.gmi.workzen.databinding.ActivityMainBinding
import vn.gmi.workzen.domain.entity.notification.Notification
import vn.gmi.workzen.domain.entity.notification.NotifyType
import vn.gmi.workzen.manager.schedule.ReminderScheduler
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.ui.home.HomeFragment
import vn.gmi.workzen.ui.payroll.PayRollFragment
import vn.gmi.workzen.ui.profile.ProfileFragment
import vn.gmi.workzen.ui.worksheet.WorkSheetFragment
import vn.gmi.workzen.utils.IntentData

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            v.updatePadding(bottom = 0)
            insets
        }

        setListener()
        if(savedInstanceState == null){
            replaceFragment(HomeFragment())
        }

        if(intent.hasExtra(IntentData.KEY_DATA_FROM_FCM)){
            val dataJson = intent.getStringExtra(IntentData.KEY_DATA_FROM_FCM)
            val notification = Gson().fromJson(dataJson, Notification::class.java)
        }

    }

    private fun setListener(){
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.menu_payroll -> {
                    replaceFragment(PayRollFragment())
                    true
                }
                R.id.menu_worksheet -> {
                    replaceFragment(WorkSheetFragment())
                    true
                }
                R.id.menu_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(obj:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, obj)
            .addToBackStack(null)
            .commit()
        if(obj is WorkSheetFragment || obj is PayRollFragment || obj is ProfileFragment){
            updateStatusBar(R.color.primary, false)
        }else{
            updateStatusBar(R.color.background, true)
        }
    }

    private fun updateStatusBar(colorRes: Int, isLight: Boolean) {
        window.statusBarColor = ContextCompat.getColor(this, colorRes)
        WindowCompat.getInsetsController(window, window.decorView)
            ?.isAppearanceLightStatusBars = isLight
    }

    private fun handleNavigatorView(notification: Notification){

    }

    override fun onDestroy() {
        RealmProvider.close()
        super.onDestroy()

    }
}