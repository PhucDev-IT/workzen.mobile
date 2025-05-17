package vn.gmi.workzen

import android.app.Application
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.utils.MySharedPreferences

class MyApplication : Application (){

    override fun onCreate() {
        super.onCreate()
        MySharedPreferences.init(this)
    }
}