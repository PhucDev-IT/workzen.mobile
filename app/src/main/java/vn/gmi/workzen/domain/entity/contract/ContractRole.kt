package vn.gmi.workzen.domain.entity.contract

import android.util.Log

enum class ContractRole(val value: String) {
    STAFF("Nhân viên")
    ;

    companion object{
        fun fromValue(value: String): ContractRole? {
            return ContractRole.entries.find { it.value == value }
        }

        fun fromKey(key: String?): String {
           try{
               return ContractRole.valueOf(key!!).value.toString()
           }catch (e: Exception){
               Log.e("ContractRole",e.message?:"")
               return ""
           }
        }
    }
}