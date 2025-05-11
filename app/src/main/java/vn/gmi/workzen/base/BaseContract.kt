package vn.gmi.workzen.base

interface BaseContract {
    interface View{
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter<V:View>{
        fun attachView(view : V)
        fun detachView()
        fun getView():V?
    }
}