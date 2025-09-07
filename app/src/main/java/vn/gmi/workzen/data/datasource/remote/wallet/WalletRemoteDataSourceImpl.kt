package vn.gmi.workzen.data.datasource.remote.wallet

import retrofit2.Response
import vn.gmi.workzen.data.mapper.PagedResponse
import vn.gmi.workzen.data.models.request.wallet.CreateLinkPaymentReq
import vn.gmi.workzen.data.models.request.wallet.CreateTransactionReq
import vn.gmi.workzen.data.models.response.wallet.LinkedWalletResponse
import vn.gmi.workzen.data.models.response.wallet.TransactionResp
import vn.gmi.workzen.data.models.response.wallet.WalletResp
import vn.gmi.workzen.networks.api.WalletService
import vn.gmi.workzen.networks.models.ApiResponse

class WalletRemoteDataSourceImpl (private val apiService: WalletService):  WalletRemoteDataSource {
    override suspend fun getAllWallets(): Response<ApiResponse<List<WalletResp>>> {
       return apiService.getAllWallets()
    }

    override suspend fun requestLinkWallet(userId: String,request: CreateLinkPaymentReq): Response<ApiResponse<LinkedWalletResponse>> {
        return apiService.requestLinkWallet(userId,request)
    }

    override suspend fun getAllLinkedWallet(userId: String): Response<ApiResponse<List<LinkedWalletResponse>>> {
        return  apiService.getAllLinkedWallet(userId)
    }

    override suspend fun getTransactionHistories(
        userId: String,
        page: Int,
        size: Int
    ): Response<ApiResponse<PagedResponse<TransactionResp>>> {
        return apiService.getTransactionHistories(userId,page,size)
    }

    override suspend fun createTransaction(
        userId: String,
        request: CreateTransactionReq
    ): Response<ApiResponse<TransactionResp>> {
        return apiService.createTransaction(userId,request)
    }

    override suspend fun getWalletIdByPhone(phone: String): Response<ApiResponse<String>> {
        return apiService.getWalletIdByPhone(phone)
    }
}