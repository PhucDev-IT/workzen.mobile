package vn.gmi.workzen.domain.entity.company

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class DepartmentEntity : RealmObject{
    @PrimaryKey
    var id:String = ""
    var code:String?=null
    var name:String?=null
    var isActive: Boolean = true;
}