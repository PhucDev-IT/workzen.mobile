package vn.gmi.workzen.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import vn.gmi.workzen.data.datasource.local.wallet.WalletLocalDataSource
import vn.gmi.workzen.data.datasource.remote.wallet.WalletRemoteDataSource
import vn.gmi.workzen.data.di.IoDispatcher
import vn.gmi.workzen.data.models.request.wallet.CreateLinkPaymentReq
import vn.gmi.workzen.domain.entity.wallet.LinkedWalletEntity
import vn.gmi.workzen.domain.entity.wallet.WalletEntity
import vn.gmi.workzen.domain.repository.WalletRepository
import vn.gmi.workzen.networks.rest.ApiResult
import vn.gmi.workzen.networks.rest.toApiResult

class WalletRepositoryImpl(
    private val localDataSource: WalletLocalDataSource,
    private val remoteDataSource: WalletRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): WalletRepository {


    override suspend fun getWallets(): List<WalletEntity> {
        return withContext (dispatcher){
            val local = localDataSource.getAllWallet()
            if( local.isNotEmpty() == true)
                local
            else
                when(val result = remoteDataSource.getAllWallets().toApiResult()){
                    is ApiResult.Success -> {
                        val mapped = result.data.map { it.mapToEntity() }
                        localDataSource.storeWallets(mapped)
                        mapped
                    }
                    is ApiResult.Error -> throw Exception(result.message)
                }
        }
    }

    override suspend fun getLinkedWallets(userId: String): List<LinkedWalletEntity> {
        return withContext (dispatcher){
            when(val result = remoteDataSource.getAllLinkedWallet(userId).toApiResult()){
                is ApiResult.Success -> {
                   val entities = result.data.map { it.mapToEntity() }
                    localDataSource.storeLinkedWallets(entities)
                    entities
                }
                is ApiResult.Error -> throw Exception(result.message)
            }
        }
    }

    override suspend fun createLinkPayment(request: CreateLinkPaymentReq): LinkedWalletEntity {
       return withContext (dispatcher){
           when(val result = remoteDataSource.requestLinkWallet(request).toApiResult()){
               is ApiResult.Success -> result.data.mapToEntity()
               is ApiResult.Error -> throw Exception(result.message)
           }
       }
    }
}