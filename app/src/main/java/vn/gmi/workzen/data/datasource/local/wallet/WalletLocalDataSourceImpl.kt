package vn.gmi.workzen.data.datasource.local.wallet

import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.copyFromRealm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import vn.gmi.workzen.data.database.RealmProvider
import vn.gmi.workzen.domain.entity.notification.Notification
import vn.gmi.workzen.domain.entity.wallet.LinkedWalletEntity
import vn.gmi.workzen.domain.entity.wallet.PaymentTransactionEntity
import vn.gmi.workzen.domain.entity.wallet.WalletEntity

class WalletLocalDataSourceImpl  : WalletLocalDataSource{
    override suspend fun getAllWallet(): List<WalletEntity> {
        val realm = RealmProvider.realm
        val results = realm.query<WalletEntity>().find().copyFromRealm()
        return results  // K
    }

    override suspend fun storeWallets(wallets: List<WalletEntity>) {
        RealmProvider.realm.write {
            wallets.forEach {
                copyToRealm(it, updatePolicy = UpdatePolicy.ALL)
            }
        }
    }

    override suspend fun getLinkedWallets(): List<LinkedWalletEntity> {
        return RealmProvider.realm.query<LinkedWalletEntity>().find().copyFromRealm()
    }

    override suspend fun storeLinkedWallets(wallets: List<LinkedWalletEntity>) {
       return RealmProvider.realm.write {
           wallets.forEach {
               copyToRealm(it, updatePolicy = UpdatePolicy.ALL)
           }
       }
    }

    override suspend fun saveTransactionHistory(transactions: List<PaymentTransactionEntity>) {
        return RealmProvider.realm.write {
            transactions.forEach {
                copyToRealm(it, updatePolicy = UpdatePolicy.ALL)

            }
        }
    }

    override suspend fun getTransactionHistories(
        userId: String,
        page: Int,
        size: Int
    ): List<PaymentTransactionEntity> {
        return RealmProvider.realm.query<PaymentTransactionEntity>("userId == $0",userId).sort("createdAt" ,
            Sort.ASCENDING).find().copyFromRealm()
    }
}