package vn.gmi.workzen

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.utils.MySharedPreferences

@HiltAndroidApp
class MyApplication : Application (){

    override fun onCreate() {
        super.onCreate()
        MySharedPreferences.init(this)
    }
}