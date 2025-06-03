package vn.gmi.workzen.data.models.response.user

import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.data.models.response.shift.ShiftResponseModel
import vn.gmi.workzen.domain.entity.BranchCompany
import vn.gmi.workzen.domain.entity.CompanyEntity
import vn.gmi.workzen.domain.entity.ContractEntity
import vn.gmi.workzen.domain.entity.ShiftEntity
import vn.gmi.workzen.utils.FormatUtils
import java.time.LocalDate

class ContractResponseModel : DataMapper<ContractEntity>(){
    var id:String = ""
    var startDate: String? = null  // ISO 8601: "2024-06-01"
    var expiryDate: String? = null
    var documentUrl:String?=null
    var position:String?=null
    var baseSalary: Double?=null
    var workingTime: ShiftResponseModel?=null
    var jobTitle: String?=null
    var company: CompanyEntity?=null
    var branch: BranchCompany?=null

    override fun mapToEntity(): ContractEntity {
        return ContractEntity().apply {
            id = this@ContractResponseModel.id
            startDate = this@ContractResponseModel.startDate
            expiryDate =this@ContractResponseModel.expiryDate
            documentUrl = this@ContractResponseModel.documentUrl
            position = this@ContractResponseModel.position
            baseSalary = this@ContractResponseModel.baseSalary
            workingTime = this@ContractResponseModel.workingTime?.mapToEntity()
            jobTitle = this@ContractResponseModel.jobTitle
            company = this@ContractResponseModel.company
            branch = this@ContractResponseModel.branch

        }
    }
}