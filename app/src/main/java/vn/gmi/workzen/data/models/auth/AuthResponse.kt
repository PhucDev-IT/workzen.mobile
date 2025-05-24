package vn.gmi.workzen.data.models.auth

data class AuthResponse (
    val user: UserResponseModel?,
    val bearToken:String
)