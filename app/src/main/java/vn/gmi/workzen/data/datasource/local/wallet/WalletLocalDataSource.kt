package vn.gmi.workzen.data.datasource.local.wallet

import vn.gmi.workzen.domain.entity.wallet.LinkedWalletEntity
import vn.gmi.workzen.domain.entity.wallet.PaymentTransactionEntity
import vn.gmi.workzen.domain.entity.wallet.WalletEntity

interface WalletLocalDataSource {
    suspend fun getAllWallet() : List<WalletEntity>
    suspend fun storeWallets(wallets: List<WalletEntity>)

    suspend fun getLinkedWallets(): List<LinkedWalletEntity>
    suspend fun storeLinkedWallets(wallets: List<LinkedWalletEntity>)

    suspend fun saveTransactionHistory(transactions: List<PaymentTransactionEntity>)
    suspend fun getTransactionHistories(userId: String,page: Int,size: Int): List<PaymentTransactionEntity>

}