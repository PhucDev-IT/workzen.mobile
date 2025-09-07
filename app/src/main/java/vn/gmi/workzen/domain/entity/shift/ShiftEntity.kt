package vn.gmi.workzen.domain.entity.shift

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class ShiftEntity : RealmObject {
    @PrimaryKey
    var id:String = ""
    var name: String?=null
    var startTime: String?=null
    var endTime: String?=null
    var isOvertime: Boolean? = false
}