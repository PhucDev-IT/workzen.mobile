package vn.gmi.workzen.data.models.response.user

import vn.gmi.workzen.data.mapper.DataMapper

data class UserResponseModel (
    val id: String,
    val phone: String,
    val email: String?,
    val fullName: String,
)