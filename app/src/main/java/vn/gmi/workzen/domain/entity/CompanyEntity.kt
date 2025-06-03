package vn.gmi.workzen.domain.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class CompanyEntity : CompanyBase(), RealmObject {
    @PrimaryKey
    var id: String =""

}