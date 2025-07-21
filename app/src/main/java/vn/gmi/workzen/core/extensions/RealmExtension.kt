package vn.gmi.workzen.core.extensions

import io.realm.kotlin.types.RealmList

fun <T> RealmList<T>.asList(): List<T> = this.toMutableList()
