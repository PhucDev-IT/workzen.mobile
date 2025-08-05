package vn.gmi.workzen.data.models.response.wallet

import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.domain.entity.enums.TransactionType
import vn.gmi.workzen.domain.entity.wallet.PaymentTransactionEntity
import vn.gmi.workzen.domain.entity.wallet.UserInfoTransaction
import java.math.BigDecimal

class TransactionResp : DataMapper<PaymentTransactionEntity>() {
    var id: String? = null
    var sender: UserInfoTransaction? = null
    var receiver: UserInfoTransaction? = null
    var transactionId: String? = null
    var description: String? = null
    var amount: BigDecimal? = null
    var type: TransactionType? = null
    var transactionTime: String? = null
    var rawData: String? = null
    var recipientType: String? = null
    var recipientId: String? = null
    var status: String? = null


    override fun mapToEntity(): PaymentTransactionEntity {
        return PaymentTransactionEntity().apply {
            this.id = this@TransactionResp.id ?: ""
            this.sender = this@TransactionResp.sender
            this.receiver = this@TransactionResp.receiver
            this.transactionId = this@TransactionResp.transactionId
            this.description = this@TransactionResp.description
            this.amount = this@TransactionResp.amount.toString()
            this.type = this@TransactionResp.type?.name
            this.transactionTime = this@TransactionResp.transactionTime
            this.rawData = this@TransactionResp.rawData
            this.recipientType = this@TransactionResp.recipientType
            this.recipientId = this@TransactionResp.recipientId
            this.status = this@TransactionResp.status


        }
    }
}