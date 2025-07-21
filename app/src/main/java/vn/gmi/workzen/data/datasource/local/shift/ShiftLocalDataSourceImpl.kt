package vn.gmi.workzen.data.datasource.local.shift

import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.copyFromRealm
import io.realm.kotlin.ext.query
import vn.gmi.workzen.data.database.RealmProvider
import vn.gmi.workzen.domain.entity.shift.ShiftEntity

class ShiftLocalDataSourceImpl : ShiftLocalDataSource {
    override suspend fun getShift(): ShiftEntity? {
        return RealmProvider.realm.query<ShiftEntity>().first().find()?.copyFromRealm()
    }

    override  suspend fun saveShift(shift: ShiftEntity) {
        RealmProvider.realm.write {
            copyToRealm(shift,updatePolicy = UpdatePolicy.ALL)
        }
    }
}