package vn.gmi.workzen.manager

import io.realm.kotlin.ext.copyFromRealm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import vn.gmi.workzen.data.database.RealmProvider
import vn.gmi.workzen.domain.entity.user.ProfileEntity

object SessionManager {
    private val _profileState = MutableStateFlow<ProfileEntity?>(null)
    val profileState: StateFlow<ProfileEntity?> = _profileState

    fun observeProfile() {
        CoroutineScope(Dispatchers.Default).launch {
            RealmProvider.realm
                .query<ProfileEntity>()
                .first()
                .asFlow()
                .map { it.obj?.copyFromRealm() }
                .collect {
                    _profileState.value = it
                }
        }
    }
}
