package vn.gmi.workzen.ui.main

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
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
import vn.gmi.workzen.R
import vn.gmi.workzen.core.base.BaseActivity
import vn.gmi.workzen.data.database.RealmProvider
import vn.gmi.workzen.databinding.ActivityMainBinding
import vn.gmi.workzen.domain.entity.notification.Notification
import vn.gmi.workzen.services.AppBroadcastReceiver
import vn.gmi.workzen.manager.ChatWebsocketManager
import vn.gmi.workzen.ui.home.HomeFragment
import vn.gmi.workzen.ui.payroll.PayRollFragment
import vn.gmi.workzen.ui.profile.ProfileFragment
import vn.gmi.workzen.ui.worksheet.WorkSheetFragment
import vn.gmi.workzen.utils.IntentData
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {
    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var mainPresenter: MainContract.Presenter
    private val myBroadcast = AppBroadcastReceiver()

    override val layoutView: View
        get(){
            binding = ActivityMainBinding.inflate(layoutInflater)
            return binding.root
        }

    override fun initPresenter(): MainContract.Presenter {
        return mainPresenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null){
            replaceFragment(HomeFragment())
        }
    }
    override fun initViews() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            v.updatePadding(bottom = 0)
            insets
        }
        init()
        setListener()
    }

    override fun onSingleClick(v: View?) {

    }


    override fun setListener() {
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

    private fun init(){
        presenter.attachView(this)
        presenter.observeProfile()
    }


    private fun replaceFragment(obj: Fragment){
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


    override fun onDestroy() {
        presenter.detachView()
        RealmProvider.close()
        ChatWebsocketManager.disconnect()
        super.onDestroy()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError(message: String) {
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver( myBroadcast,filter)

    }

    override fun onResume() {
        super.onResume()
        ChatWebsocketManager.connect()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(myBroadcast)
    }

}