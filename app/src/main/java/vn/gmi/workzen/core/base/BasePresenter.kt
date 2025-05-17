package vn.gmi.workzen.core.base

abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {
    private var attachedView: V? = null

    override fun attachView(view: V) {
        this.attachedView = view
    }

    override fun detachView() {
        this.attachedView = null
    }

    override fun getView(): V? {
        return attachedView
    }
}
