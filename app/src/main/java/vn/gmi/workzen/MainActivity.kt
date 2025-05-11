package vn.gmi.workzen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import vn.gmi.workzen.databinding.ActivityMainBinding
import vn.gmi.workzen.ui.home.HomeFragment
import vn.gmi.workzen.ui.payroll.PayRollFragment
import vn.gmi.workzen.ui.profile.ProfileFragment
import vn.gmi.workzen.ui.worksheet.WorkSheetFragment

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
    }

    private fun setListener(){
        binding.bottomNavigationView.setOnNavigationItemReselectedListener { item->
            when(item.itemId){
                R.id.menu_home->{
                    replaceFragment(HomeFragment())
                    return@setOnNavigationItemReselectedListener
                }
                R.id.menu_payroll ->{
                    replaceFragment(PayRollFragment())
                    return@setOnNavigationItemReselectedListener
                }
                R.id.menu_worksheet ->{
                    replaceFragment(WorkSheetFragment())
                    return@setOnNavigationItemReselectedListener
                }
                R.id.menu_profile ->{
                    replaceFragment(ProfileFragment())
                    return@setOnNavigationItemReselectedListener
                }
            }
        }
    }

    private fun replaceFragment(obj:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, obj)
            .commit()
    }
}