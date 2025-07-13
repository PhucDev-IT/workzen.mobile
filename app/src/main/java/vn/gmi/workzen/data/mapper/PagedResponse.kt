package vn.gmi.workzen.data.mapper

class PagedResponse<T> {
    val data: List<T>?=null
    val page:Int?=null
    val size:Int?=null
    val totalElements:Int?=null
}