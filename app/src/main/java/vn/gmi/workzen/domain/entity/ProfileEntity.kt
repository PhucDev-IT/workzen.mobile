package vn.gmi.workzen.domain.entity

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class ProfileEntity: RealmObject {
    @PrimaryKey
    var id:String?=null
    var fullName:String?=null
    var phone:String?=null
    var email:String?=null
    var details:IdentificationEntity?=null
    var contracts: RealmList<ContractEntity> = realmListOf()


}