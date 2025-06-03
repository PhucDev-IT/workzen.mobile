package vn.gmi.workzen.data.datasource.local.shift

import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import vn.gmi.workzen.data.database.RealmProvider
import vn.gmi.workzen.domain.entity.ShiftEntity

class ShiftLocalDataSourceImpl : ShiftLocalDataSource {
    override fun getShift(): ShiftEntity? {
        return RealmProvider.realm.query<ShiftEntity>().first().find()
    }

    override fun saveShift(shift: ShiftEntity) {
        RealmProvider.realm.writeBlocking {
            copyToRealm(shift,updatePolicy = UpdatePolicy.ALL)
        }
    }
}