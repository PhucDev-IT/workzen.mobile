package vn.gmi.workzen.data.models.response.auth


data class LoginResponseModel(
    val id: String,
    val phone: String,
    val email: String?,
    val fullName: String,
    val roles: List<String>?,
    val bearTokens: BearTokens

)
data class BearTokens(val accessToken:String, val refreshToken:String)