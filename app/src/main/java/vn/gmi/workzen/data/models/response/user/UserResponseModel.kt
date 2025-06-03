package vn.gmi.workzen.data.models.response.user

data class UserResponseModel (
    val id: String,
    val phone: String,
    val email: String?,
    val avatar: String?,
    val fullName: String,
)