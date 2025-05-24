package vn.gmi.workzen.data.models.auth

import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.domain.entities.UserEntity

data class UserResponseModel(
    val id: String,
    val phone: String,
    val email: String?,
    val avatar: String?,
    val fullName: String,
    val isActive: Boolean,
    val roles: List<String>?
): DataMapper<vn.gmi.workzen.domain.entities.UserEntity>() {
    override fun mapToEntity(): UserEntity {
        return UserEntity(
            id = id,
            phone = phone,
            email = email,
            avatar = avatar,
            fullName = fullName,
            isActive = isActive,
            roles = roles
        )
    }
}
