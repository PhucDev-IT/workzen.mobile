package vn.gmi.workzen.domain.entity.wallet

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class UserInfoTransaction : RealmObject {
    @PrimaryKey
    var id:String = ""
    var idWalletLinked:String?=null
    var fullName:String?=null
    var phone:String?=null
    var avatar:String?=null
}