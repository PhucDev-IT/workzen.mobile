package vn.gmi.workzen.data.mapper

class PagedResponse<T> {
    var data: List<T>?=null
    var page:Int?=null
    var size:Int?=null
    var totalElements:Int?=null
}