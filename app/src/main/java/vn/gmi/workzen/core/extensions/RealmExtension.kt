package vn.gmi.workzen.core.extensions

import io.realm.kotlin.types.RealmList

fun <T> RealmList<T>.asToList(): List<T>{
    val list = mutableListOf<T>()
     this.forEach {it-> list.add(it) }
    return list
}
