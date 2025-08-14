package vn.gmi.workzen.domain.entity.wallet

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import vn.gmi.workzen.data.models.response.wallet.WalletResp

class LinkedWalletEntity : RealmObject{
    @PrimaryKey
    var idLinkedWallet:String = ""
    var balance:String?=null
    var contact:String?=null
    var idCard:String?=null
    var eidNumber:String?=null
    var ownerName:String?=null

    var walletInfo: WalletEntity?=null
}