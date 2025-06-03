package vn.gmi.workzen.domain.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.time.LocalDate



class ContractEntity :  RealmObject {
    @PrimaryKey
    var id:String = ""
    var startDate: String? = null  // ISO 8601: "2024-06-01"
    var expiryDate: String? = null
    var documentUrl:String?=null
    var position:String?=null
    var baseSalary: Double?=null
    var workingTime: String?=null
    var jobTitle: String?=null
    var company: CompanyEntity?=null
    var branch: BranchCompany?=null
}