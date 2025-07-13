package vn.gmi.workzen.data.datasource.local.notification

import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.copyFromRealm
import vn.gmi.workzen.data.database.RealmProvider
import vn.gmi.workzen.domain.entity.notification.Notification
import vn.gmi.workzen.manager.SessionManager
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotificationLocalDataSourceImpl : NotificationLocalDataSource {

    override suspend fun getAll(accountId: String): List<Notification> {
        val realm = RealmProvider.realm
        val results = realm.query<Notification>("userId == $0", accountId).find().copyFromRealm()
        return results  // Kết quả là List<Notification>
    }

    override suspend fun addAll(notifications: List<Notification>) {
        RealmProvider.realm.write {
            notifications.forEach {
                copyToRealm(it, UpdatePolicy.ALL)
            }
        }
    }

    override suspend fun update(notification: Notification) {
        RealmProvider.realm.write {
            copyToRealm(notification, UpdatePolicy.ALL)
        }
    }

    override suspend fun delete(notification: Notification) {
        RealmProvider.realm.write {
            findLatest(notification)?.let {
                delete(it)
            }
        }
    }
}
