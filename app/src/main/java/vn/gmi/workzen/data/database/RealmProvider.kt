package vn.gmi.workzen.data.database


import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import vn.gmi.workzen.BuildConfig
import vn.gmi.workzen.domain.entity.company.CompanyEntity
import vn.gmi.workzen.domain.entity.contract.ContractEntity
import vn.gmi.workzen.domain.entity.IdentificationEntity
import vn.gmi.workzen.domain.entity.company.DepartmentEntity
import vn.gmi.workzen.domain.entity.user.ProfileEntity
import vn.gmi.workzen.domain.entity.shift.ShiftEntity

object RealmProvider {

    private val schemaModels = setOf(
        IdentificationEntity::class,
        ProfileEntity::class,
        ContractEntity::class,
        CompanyEntity::class,
        DepartmentEntity::class,
        ShiftEntity::class
    )

    val config: RealmConfiguration by lazy {
        RealmConfiguration.Builder(
            schema = schemaModels
        ).apply {
            if (BuildConfig.DEBUG) {
                deleteRealmIfMigrationNeeded()
            }
            schemaVersion(1)
        }.build()
    }



    val realm: Realm by lazy {
        Realm.open(config)
    }
}