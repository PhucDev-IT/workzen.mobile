package vn.gmi.workzen.networks.models

class ApiResponse<T> {
    val success:Boolean = false
    val error:ErrorResponse?=null
    val data: T?=null
}