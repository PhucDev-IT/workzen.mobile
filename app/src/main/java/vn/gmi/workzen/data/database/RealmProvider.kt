package vn.gmi.workzen.data.database


import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import vn.gmi.workzen.domain.entity.IdentificationEntity

object RealmProvider {

    private val schemaModels = setOf(
        IdentificationEntity::class
    )

    val config: RealmConfiguration by lazy {
        RealmConfiguration.Builder(
            schema = setOf(IdentificationEntity::class)
        )
            .schemaVersion(1)
            .build()
    }


    val realm: Realm by lazy {
        Realm.open(config)
    }
}