package vn.gmi.workzen.data.models.request.user

data class OnboardUserReqModel(
    var eidNumber: String? = null,
    var fullName: String? = null,
    var gender: String? = null,
    var dateOfBirth: String? = null,
    var dateOfIssue: String? = null,
    var dateOfExpiry: String? = null,
    var nationality: String? = null,
    var ethnicity: String? = null,
    var religion: String? = null,
    var placeOfOrigin: String? = null,
    var placeOfResidence: String? = null,
    var personalIdentification: String? = null,
    var fatherName: String? = null,
    var motherName: String? = null,
    var spouseName: String? = null,
    var oldEidNumber: String? = null,
    var dg2: String? = null
)
