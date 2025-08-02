package vn.gmi.workzen.data.datasource.remote.wallet

import retrofit2.Response
import vn.gmi.workzen.data.models.request.wallet.CreateLinkPaymentReq
import vn.gmi.workzen.data.models.response.wallet.LinkedWalletResponse
import vn.gmi.workzen.data.models.response.wallet.WalletResp
import vn.gmi.workzen.networks.api.WalletService
import vn.gmi.workzen.networks.models.ApiResponse

class WalletRemoteDataSourceImpl (private val apiService: WalletService):  WalletRemoteDataSource {
    override suspend fun getAllWallets(): Response<ApiResponse<List<WalletResp>>> {
       return apiService.getAllWallets()
    }

    override suspend fun requestLinkWallet(request: CreateLinkPaymentReq): Response<ApiResponse<LinkedWalletResponse>> {
        return apiService.requestLinkWallet(request)
    }

    override suspend fun getAllLinkedWallet(userId: String): Response<ApiResponse<List<LinkedWalletResponse>>> {
        return  apiService.getAllLinkedWallet(userId)
    }
}