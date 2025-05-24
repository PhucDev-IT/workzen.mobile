package vn.gmi.workzen.domain.entities

data class UserEntity (
    val id: String,
    val phone: String,
    val email: String?,
    val avatar: String?,
    val fullName: String,
    val isActive: Boolean,
    val roles: List<String>?
    )