package vn.gmi.workzen.domain.entity.user

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import vn.gmi.workzen.domain.entity.contract.ContractEntity
import vn.gmi.workzen.domain.entity.user.IdentificationEntity

class ProfileEntity : RealmObject {
    @PrimaryKey
    var id: String = ""
    var phone: String?=null
    var email: String?=null
    var fullName: String?=null
    var note:String?=null
    var avatarUrl:String?=null
    var isActive: Boolean = true
    var details: IdentificationEntity?=null
    var contracts: RealmList<ContractEntity> = realmListOf()
    override fun toString(): String {
        return "ProfileEntity(id='$id', phone=$phone, email=$email, fullName=$fullName, details=$details, contracts=$contracts)"
    }


}