package vn.gmi.workzen.core.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {
    private val presenterJob = SupervisorJob()
    protected val scope = CoroutineScope(Dispatchers.Main + presenterJob)

    private var attachedView: V? = null

    override fun attachView(view: V) {
        this.attachedView = view
    }

    override fun detachView() {
        presenterJob.cancel()
        this.attachedView = null
    }

    override fun getView(): V? {
        return attachedView
    }
}
