package vn.gmi.workzen.data.datasource.local.user

import android.util.Log
import io.realm.kotlin.UpdatePolicy
import vn.gmi.workzen.data.database.RealmProvider
import vn.gmi.workzen.domain.entity.user.IdentificationEntity
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.data.models.response.user.UserResponseModel
import vn.gmi.workzen.domain.entity.contract.ContractEntity
import vn.gmi.workzen.domain.entity.user.ProfileEntity
import vn.gmi.workzen.utils.MySharedPreferences

class UserLocalDataSourceImpl: UserLocalDataSource {
    override suspend fun saveIdentification(model: IdentificationEntity) {
        RealmProvider.realm.write {
            copyToRealm(model, updatePolicy = UpdatePolicy.ALL)
        }
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_FULL_NAME, model.fullName!!)
    }

    override suspend fun storeProfile(model: ProfileEntity) {
        RealmProvider.realm.write {
            // Convert contracts to managed objects
            val managedContracts = realmListOf<ContractEntity>().apply {
                model.contracts.forEach { contract ->
                    add(copyToRealm(contract, updatePolicy = UpdatePolicy.ALL))
                }
            }

            // Copy details
            val managedDetails = model.details?.let {
                copyToRealm(it, updatePolicy = UpdatePolicy.ALL)
            }

            // Rebuild profile entity with managed fields
            val managedProfile = ProfileEntity().apply {
                id = model.id
                fullName = model.fullName
                phone = model.phone
                email = model.email
                details = managedDetails
                contracts = managedContracts
            }

            copyToRealm(managedProfile, updatePolicy = UpdatePolicy.ALL)
        }
        MySharedPreferences.setBooleanValue(SharedPreferenceKey.KEY_IS_ONBOARD,
            model.details?.isVerified == true
        )
    }


    override suspend fun getIdentification(): IdentificationEntity? {
        val results = RealmProvider.realm.query<IdentificationEntity>().find()
        return results.firstOrNull()
    }

    override suspend fun getProfile(): ProfileEntity? {
        val results = RealmProvider.realm.query<ProfileEntity>().find()
        return results.firstOrNull()
    }
}