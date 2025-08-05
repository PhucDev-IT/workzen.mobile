package vn.gmi.workzen.domain.repository

import vn.gmi.workzen.data.models.request.wallet.CreateLinkPaymentReq
import vn.gmi.workzen.data.models.request.wallet.CreateTransactionReq
import vn.gmi.workzen.domain.entity.wallet.LinkedWalletEntity
import vn.gmi.workzen.domain.entity.wallet.PaymentTransactionEntity
import vn.gmi.workzen.domain.entity.wallet.WalletEntity

interface WalletRepository {

    suspend fun getWallets(): List<WalletEntity>

    suspend fun getLinkedWallets(userId: String): List<LinkedWalletEntity>

    suspend fun createLinkPayment(userId: String,request: CreateLinkPaymentReq): LinkedWalletEntity

    suspend fun getTransactionHistories(userId: String,page: Int,size: Int): List<PaymentTransactionEntity>

    suspend fun createTransaction(userId: String,request: CreateTransactionReq): PaymentTransactionEntity

}