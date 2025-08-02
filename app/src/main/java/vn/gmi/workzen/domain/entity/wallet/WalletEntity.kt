package vn.gmi.workzen.domain.entity.wallet

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import vn.gmi.workzen.domain.entity.enums.WalletType

class WalletEntity : RealmObject{
    @PrimaryKey
    var id:String = ""
    var name:String?=null
    var shortName:String?=null
    var logo:String?=null
    var metaData:String?=null
    var isEnable: Boolean?=null
    var type: String?=null //WalletType
}