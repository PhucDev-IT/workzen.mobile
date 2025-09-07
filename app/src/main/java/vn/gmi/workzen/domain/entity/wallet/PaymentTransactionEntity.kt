package vn.gmi.workzen.domain.entity.wallet

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import vn.gmi.workzen.domain.entity.enums.TransactionType
import java.math.BigDecimal

class PaymentTransactionEntity : RealmObject{
    @PrimaryKey
    var id:String = ""
    var sender:UserInfoTransaction?=null
    var receiver:UserInfoTransaction?=null
    var transactionId:String?=null
    var description:String?=null
    var amount: String?=null
    var type: String?=null //TransactionType
    var transactionTime:String?=null
    var rawData:String?=null
    var recipientType:String?=null
    var recipientId:String?=null
    var status:String?=null

}