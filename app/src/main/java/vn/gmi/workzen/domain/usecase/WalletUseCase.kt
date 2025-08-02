package vn.gmi.workzen.domain.usecase

import vn.gmi.workzen.core.usecases.BaseUseCase
import vn.gmi.workzen.data.models.request.wallet.CreateLinkPaymentReq
import vn.gmi.workzen.domain.entity.wallet.LinkedWalletEntity
import vn.gmi.workzen.domain.entity.wallet.WalletEntity
import vn.gmi.workzen.domain.repository.WalletRepository

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
        return repository.createLinkPayment(params)
    }
}

class GetLinkedWalletsUseCase(private val repository: WalletRepository) : BaseUseCase<String, List<LinkedWalletEntity>>(){
    override suspend fun invoke(params: String): List<LinkedWalletEntity> {
        return repository.getLinkedWallets(params)
    }
}