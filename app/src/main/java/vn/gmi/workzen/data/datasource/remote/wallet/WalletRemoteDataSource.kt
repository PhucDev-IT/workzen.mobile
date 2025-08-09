package vn.gmi.workzen.data.datasource.remote.wallet

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Query
import vn.gmi.workzen.data.mapper.PagedResponse
import vn.gmi.workzen.data.models.request.wallet.CreateLinkPaymentReq
import vn.gmi.workzen.data.models.request.wallet.CreateTransactionReq
import vn.gmi.workzen.data.models.response.wallet.LinkedWalletResponse
import vn.gmi.workzen.data.models.response.wallet.TransactionResp
import vn.gmi.workzen.data.models.response.wallet.WalletResp
import vn.gmi.workzen.networks.models.ApiResponse

interface WalletRemoteDataSource {
    suspend fun getAllWallets(): Response<ApiResponse<List<WalletResp>>>

    suspend fun requestLinkWallet(userId: String, request: CreateLinkPaymentReq): Response<ApiResponse<LinkedWalletResponse>>
    suspend fun getAllLinkedWallet( userId: String): Response<ApiResponse<List<LinkedWalletResponse>>>

    suspend fun getTransactionHistories(userId: String,page: Int,size: Int): Response<ApiResponse<PagedResponse<TransactionResp>>>

    suspend fun createTransaction(userId: String,request: CreateTransactionReq): Response<ApiResponse<TransactionResp>>
    suspend fun getWalletIdByPhone(phone: String): Response<ApiResponse<String>>
}