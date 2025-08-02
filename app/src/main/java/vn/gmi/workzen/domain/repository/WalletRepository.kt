package vn.gmi.workzen.domain.repository

import vn.gmi.workzen.data.models.request.wallet.CreateLinkPaymentReq
import vn.gmi.workzen.domain.entity.wallet.LinkedWalletEntity
import vn.gmi.workzen.domain.entity.wallet.WalletEntity

interface WalletRepository {

    suspend fun getWallets(): List<WalletEntity>

    suspend fun getLinkedWallets(userId: String): List<LinkedWalletEntity>

    suspend fun createLinkPayment(request: CreateLinkPaymentReq): LinkedWalletEntity
}