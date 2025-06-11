package vn.gmi.workzen.data.models.response.user

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.domain.entity.contract.ContractEntity
import vn.gmi.workzen.domain.entity.user.IdentificationEntity
import vn.gmi.workzen.domain.entity.user.ProfileEntity

class ProfileResponseModel:DataMapper<ProfileEntity>()  {
    var user:UserResponseModel?=null
    var details:IdentificationEntity?=null
    var contracts: List<ContractResponseModel> ?=null

    override fun mapToEntity(): ProfileEntity {
        val contractList = realmListOf<ContractEntity>().apply {
            this@ProfileResponseModel.contracts?.forEach {
                add(it.mapToEntity()) // unmanaged, Realm sẽ không dùng trực tiếp
            }
        }

        return ProfileEntity().apply {
            id = this@ProfileResponseModel.user!!.id
            fullName = this@ProfileResponseModel.user!!.fullName
            phone = this@ProfileResponseModel.user!!.phone
            email = this@ProfileResponseModel.user!!.email
            details = this@ProfileResponseModel.details
            contracts = contractList
        }
    }


}