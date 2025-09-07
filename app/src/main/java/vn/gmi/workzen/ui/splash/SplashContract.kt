package vn.gmi.workzen.ui.splash

import vn.gmi.workzen.core.base.BaseContract

interface SplashContract {
    interface View:BaseContract.View{
        fun goToView(clz:Class<*>)
    }

    interface Presenter : BaseContract.Presenter<View>{
        fun checkApp()
    }
}