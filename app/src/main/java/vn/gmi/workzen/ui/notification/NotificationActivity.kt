package vn.gmi.workzen.ui.notification

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.RvNotificationAdapter
import vn.gmi.workzen.core.base.BaseActivity
import vn.gmi.workzen.databinding.ActivityNotificationBinding
import vn.gmi.workzen.domain.entity.notification.Notification
import vn.gmi.workzen.domain.entity.notification.NotifyType
import javax.inject.Inject

@AndroidEntryPoint
class NotificationActivity : BaseActivity<NotificationContract.View, NotificationContract.Presenter>(), NotificationContract.View {

    private lateinit var binding: ActivityNotificationBinding
    @Inject lateinit var notificationPresenter: NotificationContract.Presenter
    private lateinit var notificationCommon: RvNotificationAdapter
    private lateinit var notificationRecent: RvNotificationAdapter


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

        notificationCommon = RvNotificationAdapter()
        notificationRecent = RvNotificationAdapter()
        binding.rvCommon.adapter = notificationCommon
        binding.rvRecentActivity.adapter = notificationRecent


        presenter.requestGetNotification()
    }


    override fun setListener() {
        binding.toolbar.setNavigationOnClickListener{
            finish()
        }
    }

    override fun onSingleClick(v: View?) {
        when(v){

        }
    }

    override fun showLoading() {
        binding.progressCommon.visibility = View.VISIBLE
        binding.progressRecent.visibility = View.VISIBLE
       // super.showLoading()
    }

    override fun hideLoading() {
        binding.progressCommon.visibility = View.GONE
        binding.progressRecent.visibility = View.GONE
      //  super.hideLoading()
    }

    override fun onError(message: String) {
        Log.e("NotificationActivity", "onError: $message")
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetNotificationSuccess(notifications: List<Notification>) {
        val (notificationList, otherList) = notifications.partition { it.type == NotifyType.NOTIFICATION.name }
        notificationCommon.addAll(notificationList)
        notificationRecent.addAll(otherList)

        if(notificationList.isEmpty() && otherList.isEmpty()){
            binding.llNotFound.visibility = View.VISIBLE
            binding.scrollView.visibility = View.GONE
        }else{
            binding.llNotFound.visibility = View.GONE
            binding.scrollView.visibility = View.VISIBLE

        }
    }
}