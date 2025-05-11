package vn.gmi.workzen.base

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import vn.gmi.workzen.R
import vn.mobile.wallet.utils.Constants

abstract class BaseActivity<V : BaseContract.View, P : BaseContract.Presenter<V>> :
    BaseContract.View,
    View.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener,
    AppCompatActivity() {

    protected lateinit var presenter: P
    abstract val layoutView: View
    private var mLastClickTime: Long = 0

    abstract fun initPresenter(): P
    abstract fun initViews()
    abstract fun setListener()
    abstract fun onSingleClick(v:View?)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutView)

        presenter = initPresenter()
        presenter.attachView(this as V)

        initViews()
        setListener()
        onAnimation()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - this.mLastClickTime
        this.mLastClickTime = currentClickTime
        if (elapsedTime <= Constants.MIN_CLICK_INTERVAL) {
            return
        }
        this.onSingleClick(v)
    }
    override fun onRefresh() {}

    protected fun replaceFragment(@IdRes containerViewId: Int, fragment: Fragment, fragmentTag: String, backStackStateName: String?) {
        try {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun hideNavigationBar() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    private fun onAnimation() {
        overridePendingTransition(R.anim.fade_in, R.anim.no_animation)
    }
}
