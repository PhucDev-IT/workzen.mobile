package vn.gmi.workzen.data.datasource.remote.wallet

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Query
import vn.gmi.workzen.data.models.request.wallet.CreateLinkPaymentReq
import vn.gmi.workzen.data.models.response.wallet.LinkedWalletResponse
import vn.gmi.workzen.data.models.response.wallet.WalletResp
import vn.gmi.workzen.networks.models.ApiResponse

interface WalletRemoteDataSource {
    suspend fun getAllWallets(): Response<ApiResponse<List<WalletResp>>>

    suspend fun requestLinkWallet(@Body request: CreateLinkPaymentReq): Response<ApiResponse<LinkedWalletResponse>>
    suspend fun getAllLinkedWallet(@Query("userId") userId: String): Response<ApiResponse<List<LinkedWalletResponse>>>
}