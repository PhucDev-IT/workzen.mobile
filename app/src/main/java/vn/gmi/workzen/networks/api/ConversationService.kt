package vn.gmi.workzen.networks.api

import com.airbnb.lottie.model.MutablePair
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import vn.gmi.workzen.data.mapper.PagedResponse
import vn.gmi.workzen.data.models.request.conversation.ChatMessage
import vn.gmi.workzen.data.models.response.conversation.ConversationWithLastMessage
import vn.gmi.workzen.data.models.response.conversation.MessageResponseModel
import vn.gmi.workzen.networks.models.ApiResponse
import java.time.Instant

interface ConversationService {
    @GET(EndPoints.GET_CONVERSATION)
    suspend fun getConversations(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("userId") userId: String
    ): Response<ApiResponse<PagedResponse<ConversationWithLastMessage>>>


    @GET(EndPoints.GET_CONVERSATION_GROUP)
    suspend fun getConversationsGroup(): Response<ApiResponse<List<ConversationWithLastMessage>>>

    @GET(EndPoints.GET_CONVERSATION_UN_READ)
    suspend fun getConversationsUnread(): Response<ApiResponse<List<ConversationWithLastMessage>>>

    @GET(EndPoints.GET_MESSAGE)
    suspend fun getMessages(
        @Path("conversationId") conversationId: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ApiResponse<PagedResponse<MessageResponseModel>>>

    @Multipart
    @POST(EndPoints.SEND_MESSAGE)
    suspend fun sendMessage(
        @Part files: List<MultipartBody.Part>?,
        @Part("message") message: RequestBody): Response<ApiResponse<MessageResponseModel>>

    @GET(EndPoints.GET_MESSAGE_SINCE)
    suspend fun getMessagesSince(
        @Query("conversationId") conversationId: String,
        @Query("lastSyncedAt") lastTime: Instant
    ): Response<ApiResponse<List<MessageResponseModel>>>
}