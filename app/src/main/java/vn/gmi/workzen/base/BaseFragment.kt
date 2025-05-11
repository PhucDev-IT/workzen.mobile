package vn.gmi.workzen.base

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import vn.mobile.wallet.utils.Constants

abstract class BaseFragment<B : ViewBinding> : Fragment(), BaseContract.View,
    View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    protected var mBinding: B? = null
    protected val binding get() = mBinding!!
    private var mLastClickTime = 0L

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): B
    abstract fun initBindingData()
    abstract fun onSingleClick(v: View?)
    abstract fun initView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = getViewBinding(inflater, container)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initBindingData()
    }

    override fun onClick(v: View) {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - mLastClickTime
        mLastClickTime = currentClickTime
        if (elapsedTime <= Constants.MIN_CLICK_INTERVAL) return
        onSingleClick(v)
    }

    override fun onRefresh() {}

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null // tránh leak
    }
}
