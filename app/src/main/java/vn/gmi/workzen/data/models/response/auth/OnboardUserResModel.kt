package vn.gmi.workzen.data.models.response.auth

import android.util.Log
import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.domain.entity.user.IdentificationEntity

class OnboardUserResModel : DataMapper<IdentificationEntity>(){
    var id:String = ""
    var eidNumber: String? = null
    var fullName: String? = null
    var gender: String? = null
    var dateOfBirth: String? = null
    var dateOfIssue: String? = null
    var dateOfExpiry: String? = null
    var nationality: String? = null
    var ethnicity: String? = null
    var religion: String? = null
    var placeOfOrigin: String? = null
    var placeOfResidence: String? = null
    var personalIdentification: String? = null
    var fatherName: String? = null
    var motherName: String? = null
    var spouseName: String? = null
    var oldEidNumber: String? = null
    var dg2: String? = null
    var isVerified: Boolean?=null

    override fun mapToEntity(): IdentificationEntity {
        val entity = IdentificationEntity()
        entity.id = this.id
        entity.eidNumber = this.eidNumber
        entity.fullName = this.fullName
        entity.gender = this.gender
        entity.dateOfBirth = this.dateOfBirth
        entity.dateOfIssue = this.dateOfIssue
        entity.dateOfExpiry = this.dateOfExpiry
        entity.nationality = this.nationality
        entity.ethnicity = this.ethnicity
        entity.religion = this.religion
        entity.placeOfOrigin = this.placeOfOrigin
        entity.placeOfResidence = this.placeOfResidence
        entity.personalIdentification = this.personalIdentification
        entity.fatherName = this.fatherName
        entity.motherName = this.motherName
        entity.spouseName = this.spouseName
        entity.oldEidNumber = this.oldEidNumber
        entity.dg2 = this.dg2
        entity.isVerified = this.isVerified
        return entity
    }
}