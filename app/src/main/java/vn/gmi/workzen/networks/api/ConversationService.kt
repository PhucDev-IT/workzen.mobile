package vn.gmi.workzen.networks.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import vn.gmi.workzen.data.mapper.PagedResponse
import vn.gmi.workzen.data.models.request.conversation.ChatMessage
import vn.gmi.workzen.data.models.response.conversation.ConversationWithLastMessage
import vn.gmi.workzen.data.models.response.conversation.MessageResponseModel
import vn.gmi.workzen.networks.models.ApiResponse

interface ConversationService {
    @GET(EndPoints.GET_CONVERSATION)
    suspend fun getConversations(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("userId") userId: String
    ): Response<ApiResponse<PagedResponse<ConversationWithLastMessage>>>

    @GET(EndPoints.GET_MESSAGE)
    suspend fun getMessages(
        @Path("conversationId") conversationId: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ApiResponse<List<MessageResponseModel>>>

    @POST(EndPoints.SEND_MESSAGE)
    suspend fun sendMessage(@Body req: ChatMessage): Response<ApiResponse<MessageResponseModel>>
}