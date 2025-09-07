package vn.gmi.workzen.services

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class AppBroadcastReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            "android.net.conn.CONNECTIVITY_CHANGE"->{
                if(checkInternetConnection(context)){

                }else{

                }
            }
        }
    }


    @SuppressLint("ServiceCast")
    private fun checkInternetConnection(context: Context?): Boolean{
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        if(connectivityManager == null) return false

        val network = connectivityManager.activeNetwork
        if(network == null) return false
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }
}