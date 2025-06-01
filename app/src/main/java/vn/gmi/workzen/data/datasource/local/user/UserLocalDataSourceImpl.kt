package vn.gmi.workzen.data.datasource.local.user

import android.util.Log
import io.realm.kotlin.UpdatePolicy
import vn.gmi.workzen.data.database.RealmProvider
import vn.gmi.workzen.domain.entity.IdentificationEntity
import io.realm.kotlin.ext.query

class UserLocalDataSourceImpl: UserLocalDataSource {
    override suspend fun saveIdentification(model: IdentificationEntity) {
        RealmProvider.realm.write {
            copyToRealm(model, updatePolicy = UpdatePolicy.ALL)
        }

    }

    override suspend fun getIdentification(userId: String): IdentificationEntity? {
        val results = RealmProvider.realm.query<IdentificationEntity>().find()
        Log.d("Phuc", "getIdentification: ${results.size}")
        return results.firstOrNull()
    }
}