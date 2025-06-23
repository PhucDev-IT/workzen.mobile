package vn.gmi.workzen.ui.notification

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import vn.gmi.workzen.R
import vn.gmi.workzen.core.base.BaseActivity
import vn.gmi.workzen.databinding.ActivityNotificationBinding
import javax.inject.Inject

class NotificationActivity : BaseActivity<NotificationContract.View, NotificationContract.Presenter>(), NotificationContract.View {

    private lateinit var binding: ActivityNotificationBinding
    @Inject lateinit var notificationPresenter: NotificationContract.Presenter


    override val layoutView: View
        get() {
            binding = ActivityNotificationBinding.inflate(layoutInflater)
            return binding.root
        }

    override fun initPresenter(): NotificationContract.Presenter {
        return notificationPresenter
    }

    override fun initViews() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun setListener() {

    }

    override fun onSingleClick(v: View?) {

    }

    override fun onError(message: String) {

    }
}