package vn.gmi.workzen.data.models.response.user

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.domain.entity.ContractEntity
import vn.gmi.workzen.domain.entity.IdentificationEntity
import vn.gmi.workzen.domain.entity.ProfileEntity

class ProfileResponseModel:DataMapper<ProfileEntity>()  {
    var id:String?=null
    var fullName:String?=null
    var phone:String?=null
    var email:String?=null
    var details:IdentificationEntity?=null
    var contracts: List<ContractResponseModel> ?=null

    override fun mapToEntity(): ProfileEntity {
        val contractList = realmListOf<ContractEntity>().apply {
            this@ProfileResponseModel.contracts?.forEach {
                add(it.mapToEntity()) // unmanaged, Realm sẽ không dùng trực tiếp
            }
        }

        return ProfileEntity().apply {
            id = this@ProfileResponseModel.id
            fullName = this@ProfileResponseModel.fullName
            phone = this@ProfileResponseModel.phone
            email = this@ProfileResponseModel.email
            details = this@ProfileResponseModel.details
            contracts = contractList
        }
    }


}