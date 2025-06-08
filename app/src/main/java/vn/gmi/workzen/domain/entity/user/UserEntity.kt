package vn.gmi.workzen.domain.entity.user

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

data class UserEntity  (
    var id: String,
    var phone: String,
    var email: String?,
    var fullName: String,
)