package vn.gmi.workzen.networks.models

class ApiResponse<T> {
    val success:Boolean = false
    val message:String?=null
    val error:ErrorResponse?=null
    val data: T?=null
}