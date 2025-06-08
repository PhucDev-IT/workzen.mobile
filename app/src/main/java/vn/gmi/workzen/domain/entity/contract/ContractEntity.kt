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
    var jobTitle: String?=null
    var company: CompanyEntity?=null
    var isActive: Boolean = true;
    var baseSalary: Double?=null
    var shift: ShiftEntity?=null
    var note:String?=null
    var department: DepartmentEntity?=null
}