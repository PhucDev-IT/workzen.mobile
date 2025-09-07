package vn.gmi.workzen.data.models.request.wallet

import vn.gmi.workzen.domain.entity.enums.TransactionType
import java.time.LocalDateTime

class CreateTransactionReq {
    var senderWalletLinkedId:String?=null
    var receiverWalletLinkedId:String?=null
    var amount:String?=null
    var description:String?=null
    var rawData:String?=null
    var recipientType:String?=null
    var recipientId:String?=null
    var transactionTime: String?=null
    var type: TransactionType?=null
}