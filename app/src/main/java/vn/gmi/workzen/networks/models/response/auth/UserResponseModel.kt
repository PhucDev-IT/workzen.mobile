package vn.gmi.workzen.networks.models.response.auth

import vn.gmi.workzen.data.mapper.DataMapper


data class UserResponseModel(
    val id: String,
    val phone: String,
    val email: String?,
    val avatar: String?,
    val fullName: String,
    val isActive: Boolean,
    val roles: List<String>?
)