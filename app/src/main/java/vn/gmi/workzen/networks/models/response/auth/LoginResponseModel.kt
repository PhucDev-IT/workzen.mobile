package vn.gmi.workzen.networks.models.response.auth


data class LoginResponseModel(
    val id: String,
    val phone: String,
    val email: String?,
    val avatar: String?,
    val fullName: String,
    val roles: List<String>?,
    val bearToken:String
)