package vn.gmi.workzen.data.mapper

abstract class DataMapper<T> {
    abstract fun mapToEntity():T
}