package vn.gmi.workzen.data.models.response.wallet

import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.domain.entity.enums.WalletType
import vn.gmi.workzen.domain.entity.wallet.WalletEntity

class WalletResp : DataMapper<WalletEntity>(){
    val id:String?=null
    val name:String?=null
    val shortName:String?=null
    val logo:String?=null
    val metaData:String?=null
    val isEnable:Boolean?=null
    val type: WalletType?=null


    override fun mapToEntity(): WalletEntity {
        return WalletEntity().apply {
            this.id = this@WalletResp.id?:""
            this.name = this@WalletResp.name?:""
            this.shortName = this@WalletResp.shortName?:""
            this.logo = this@WalletResp.logo?:""
            this.metaData = this@WalletResp.metaData?:""
            this.isEnable = this@WalletResp.isEnable
            this.type = this@WalletResp.type?.name
        }
    }
}