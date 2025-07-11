package vn.gmi.workzen.domain.entity.contract

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import vn.gmi.workzen.domain.entity.company.CompanyEntity
import vn.gmi.workzen.domain.entity.company.DepartmentEntity
import vn.gmi.workzen.domain.entity.shift.ShiftEntity

class ContractEntity : RealmObject {
    @PrimaryKey
    var id:String = ""
    var startDate: String? = null  // ISO 8601: "2024-06-01"
    var expiryDate: String? = null
    var documentUrl:String?=null
    var position:String?=null
    var jobName: String?=null
    var company: CompanyEntity?=null
    var isActive: Boolean = true;
    var baseSalary: Double?=null
    var shift: ShiftEntity?=null
    var note:String?=null
    var department: DepartmentEntity?=null
}

fun ContractEntity.toDetached(): ContractEntity {
    val new = ContractEntity()
    new.isActive = this.isActive
    new.baseSalary = this.baseSalary
    new.company = this.company
    new.department = this.department
    new.documentUrl = this.documentUrl
    new.expiryDate = this.expiryDate
    new.id = this.id
    new.jobName = this.jobName
    new.note = this.note
    new.position = this.position
    new.shift = this.shift
    new.startDate = this.startDate

    return new
}
