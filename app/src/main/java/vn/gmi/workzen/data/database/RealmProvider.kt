package vn.gmi.workzen.data.database


import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import vn.gmi.workzen.domain.entity.BranchCompany
import vn.gmi.workzen.domain.entity.CompanyEntity
import vn.gmi.workzen.domain.entity.ContractEntity
import vn.gmi.workzen.domain.entity.IdentificationEntity
import vn.gmi.workzen.domain.entity.ProfileEntity
import vn.gmi.workzen.domain.entity.ShiftEntity

object RealmProvider {

    private val schemaModels = setOf(
        IdentificationEntity::class,
        ProfileEntity::class,
        ContractEntity::class,
        CompanyEntity::class,
        BranchCompany::class,
        ShiftEntity::class

    )

    val config: RealmConfiguration by lazy {
        RealmConfiguration.Builder(
            schema = schemaModels
        )
            .schemaVersion(1)
            .build()
    }


    val realm: Realm by lazy {
        Realm.open(config)
    }
}