package vn.gmi.workzen.core.base

interface BaseContract {
    interface View{
        fun showLoading()
        fun hideLoading()
        fun onError(message:String)
    }

    interface Presenter<V: View>{
        fun attachView(view : V)
        fun detachView()
        fun getView():V?
    }
}