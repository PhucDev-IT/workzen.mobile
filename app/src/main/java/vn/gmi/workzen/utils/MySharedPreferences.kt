package vn.gmi.workzen.utils

import android.content.Context
import android.content.SharedPreferences


object MySharedPreferences {
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

        private  val MY_SHARED_PREFERENCES: String = "MY_SHARED_PREFERENCES"


        fun getStringValues(key: String): String? {
            val sharedPreferences =
                appContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            return sharedPreferences.getString(key, null)
        }

        fun getIntValues(key: String): Int {
            val sharedPreferences =
                appContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            return sharedPreferences.getInt(key, -1)
        }
        fun setIntValue(key: String, value:Int){
            val sharedPreferences: SharedPreferences = appContext.getSharedPreferences(
                MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt(key,value)
            editor.apply()
        }
        fun setStringValue( key: String, value:String){
            val sharedPreferences: SharedPreferences = appContext.getSharedPreferences(
                MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(key,value)
            editor.apply()
        }

        fun getBooleanValue( key: String):Boolean{
            val sharedPreferences =
                appContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean(key, false)
        }

        fun setBooleanValue( key: String, value:Boolean){
            val sharedPreferences: SharedPreferences = appContext.getSharedPreferences(
                MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean(key,value)
            editor.apply()
        }

    fun removeKey(key: String) {
        val sharedPreferences: SharedPreferences = appContext.getSharedPreferences(
            MY_SHARED_PREFERENCES, Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val exists = sharedPreferences.contains(key)
        if(exists){
            editor.remove(key)
            editor.apply()
        }
    }

    fun clearAll(){
        val sharedPreferences: SharedPreferences = appContext.getSharedPreferences(
            MY_SHARED_PREFERENCES, Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear().apply()

    }

}