package vn.gmi.workzen.networks.models.response.auth

data class AuthenticationResponse (
    val user: UserResponseModel?,
    val bearToken:String
)