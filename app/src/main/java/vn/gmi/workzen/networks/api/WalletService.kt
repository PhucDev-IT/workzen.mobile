package vn.gmi.workzen.networks.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import vn.gmi.workzen.data.mapper.PagedResponse
import vn.gmi.workzen.data.models.request.wallet.CreateLinkPaymentReq
import vn.gmi.workzen.data.models.request.wallet.CreateTransactionReq
import vn.gmi.workzen.data.models.response.wallet.LinkedWalletResponse
import vn.gmi.workzen.data.models.response.wallet.TransactionResp
import vn.gmi.workzen.data.models.response.wallet.WalletResp
import vn.gmi.workzen.networks.models.ApiResponse

interface WalletService {
    @GET(EndPoints.GET_ALL_WALLET)
    suspend fun getAllWallets(): Response<ApiResponse<List<WalletResp>>>

    @POST(EndPoints.REQUEST_LINK_WALLET)
    suspend fun requestLinkWallet(@Path("userId") userId: String,@Body request: CreateLinkPaymentReq): Response<ApiResponse<LinkedWalletResponse>>

    @GET(EndPoints.GET_ALl_LINKED_WALLET)
    suspend fun getAllLinkedWallet(@Path("userId") userId: String): Response<ApiResponse<List<LinkedWalletResponse>>>

    @POST(EndPoints.CREATE_TRANSACTION)
    suspend fun createTransaction(@Path("userId") userId: String,@Body request: CreateTransactionReq): Response<ApiResponse<TransactionResp>>

    @GET(EndPoints.GET_TRANSACTION_HISTORIES)
    suspend fun getTransactionHistories(@Path("userId") userId: String, @Query("page") page: Int, @Query("size") size: Int): Response<ApiResponse<PagedResponse<TransactionResp>>>

    @GET(EndPoints.GET_WALLET_ID_BY_PHONE)
    suspend fun getWalletIdByPhone(@Query("phone") phone: String): Response<ApiResponse<String>>


}