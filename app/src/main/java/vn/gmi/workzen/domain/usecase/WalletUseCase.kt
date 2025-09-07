package vn.gmi.workzen.domain.usecase

import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.core.usecases.BaseUseCase
import vn.gmi.workzen.data.models.request.wallet.CreateLinkPaymentReq
import vn.gmi.workzen.data.models.request.wallet.CreateTransactionReq
import vn.gmi.workzen.data.models.response.wallet.TransactionResp
import vn.gmi.workzen.domain.entity.wallet.LinkedWalletEntity
import vn.gmi.workzen.domain.entity.wallet.PaymentTransactionEntity
import vn.gmi.workzen.domain.entity.wallet.WalletEntity
import vn.gmi.workzen.domain.repository.WalletRepository
import vn.gmi.workzen.utils.MySharedPreferences

//class GetWalletsLocalUseCase(private val repository: WalletRepository) : BaseUseCase<Unit, List<WalletEntity>>(){
//    override suspend fun invoke(params: Unit): List<WalletEntity> {
//        return repository.getWalletsLocal()
//    }
//}

class GetWalletsUseCase(private val repository: WalletRepository) : BaseUseCase<Unit, List<WalletEntity>>(){
    override suspend fun invoke(params: Unit): List<WalletEntity> {
        return repository.getWallets()
    }
}

//class StoreWalletsToLocalUseCase(private val repository: WalletRepository) : BaseUseCase<List<WalletEntity>, Unit>(){
//    override suspend fun invoke(params: List<WalletEntity>) {
//        return repository.storeWalletsToLocal(params)
//    }
//}

class RequestLinkWalletUseCase(private val repository: WalletRepository) : BaseUseCase<CreateLinkPaymentReq, LinkedWalletEntity>(){
    override suspend fun invoke(params: CreateLinkPaymentReq): LinkedWalletEntity {
        val userId = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_USER_ID) ?: ""
        return repository.createLinkPayment(userId,params)
    }
}

class GetLinkedWalletLocalUseCase(private val repository: WalletRepository) : BaseUseCase<String, List<LinkedWalletEntity>>(){
    override suspend fun invoke(params: String): List<LinkedWalletEntity> {
        return repository.getLinkedWalletLocal(params)
    }
}

class GetLinkedWalletsUseCase(private val repository: WalletRepository) : BaseUseCase<String, List<LinkedWalletEntity>>(){
    override suspend fun invoke(params: String): List<LinkedWalletEntity> {
        return repository.getLinkedWallets(params)
    }
}

class GetTransactionHistoriesUseCase(private val repository: WalletRepository) : BaseUseCase<Map<String, Any>, List<PaymentTransactionEntity>>(){
    override suspend fun invoke(params: Map<String, Any>): List<PaymentTransactionEntity> {
        return repository.getTransactionHistories(params["userId"] as String,params["page"] as Int,params["size"] as Int)
    }
}

class CreateTransactionUseCase(private val repository: WalletRepository) : BaseUseCase<Map<String, Any>, PaymentTransactionEntity>(){
    override suspend fun invoke(params: Map<String, Any>): PaymentTransactionEntity {
        return repository.createTransaction(params["userId"] as String,params["request"] as CreateTransactionReq)
    }
}

class GetWalletIdByPhoneUseCase(private val repository: WalletRepository) : BaseUseCase<String, String>(){
    override suspend fun invoke(params: String): String {
        return repository.getWalletIdByPhone(params)
    }
}