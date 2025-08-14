package vn.gmi.workzen.data.models.response.wallet

import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.domain.entity.wallet.LinkedWalletEntity

class LinkedWalletResponse : DataMapper<LinkedWalletEntity>() {
    var idLinkedWallet:String?=null
    var walletInfo: WalletResp?=null
    var balance:String?=null
    var contact:String?=null
    var idCard:String?=null
    var eidNumber:String?=null
    var ownerName:String?=null

    override fun mapToEntity(): LinkedWalletEntity {
        return LinkedWalletEntity().apply {
            this.idLinkedWallet = this@LinkedWalletResponse.idLinkedWallet?:""
            this.walletInfo = this@LinkedWalletResponse.walletInfo?.mapToEntity()
            this.balance = this@LinkedWalletResponse.balance
            this.contact = this@LinkedWalletResponse.contact
            this.idCard = this@LinkedWalletResponse.idCard
            this.eidNumber = this@LinkedWalletResponse.eidNumber
            this.ownerName = this@LinkedWalletResponse.ownerName

        }
    }
}