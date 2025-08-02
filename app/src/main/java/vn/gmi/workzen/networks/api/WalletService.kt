package vn.gmi.workzen.networks.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import vn.gmi.workzen.data.models.request.wallet.CreateLinkPaymentReq
import vn.gmi.workzen.data.models.response.wallet.LinkedWalletResponse
import vn.gmi.workzen.data.models.response.wallet.WalletResp
import vn.gmi.workzen.networks.models.ApiResponse

interface WalletService {
    @GET(EndPoints.GET_ALL_WALLET)
    suspend fun getAllWallets(): Response<ApiResponse<List<WalletResp>>>

    @POST(EndPoints.REQUEST_LINK_WALLET)
    suspend fun requestLinkWallet(@Body request: CreateLinkPaymentReq): Response<ApiResponse<LinkedWalletResponse>>
    suspend fun getAllLinkedWallet(@Query("userId") userId: String): Response<ApiResponse<List<LinkedWalletResponse>>>
}