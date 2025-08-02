package vn.gmi.workzen.data.models.response.wallet

import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.domain.entity.wallet.LinkedWalletEntity

class LinkedWalletResponse : DataMapper<LinkedWalletEntity>() {
    var idLinkedWallet:String?=null
    var walletInfo: WalletResp?=null


    override fun mapToEntity(): LinkedWalletEntity {
        return LinkedWalletEntity().apply {
            this.idLinkedWallet = this@LinkedWalletResponse.idLinkedWallet?:""
            this.walletInfo = this@LinkedWalletResponse.walletInfo?.mapToEntity()
        }
    }
}