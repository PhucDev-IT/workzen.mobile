package vn.gmi.workzen.domain.entity.company

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class CompanyEntity : RealmObject {
    @PrimaryKey
    var id: String =""
    var name: String? = null
    var shortName: String? = null
    var taxCode: String? = null
    var address: String? = null
    var latitude: Double? = null
    var longitude: Double? = null
    var phone: String? = null
    var email: String? = null
    var logo: String? = null
    var representativeName: String? = null
    var isActive: Boolean = true
    var minWorkingHours: Float?=null
    var note:String?=null
    var parentId:String?=null
}