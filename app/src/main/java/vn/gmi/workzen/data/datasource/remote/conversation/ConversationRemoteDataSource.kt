package vn.gmi.workzen.data.datasource.remote.conversation

import retrofit2.Response
import vn.gmi.workzen.data.mapper.PagedResponse
import vn.gmi.workzen.data.models.request.conversation.ChatMessage
import vn.gmi.workzen.data.models.response.conversation.ConversationWithLastMessage
import vn.gmi.workzen.data.models.response.conversation.MessageResponseModel
import vn.gmi.workzen.domain.entity.conversation.MessageEntity
import vn.gmi.workzen.networks.models.ApiResponse
import java.io.File
import java.time.Instant

interface ConversationRemoteDataSource {
    suspend fun getConversations(userId:String,page:Int,size:Int):Response<ApiResponse<PagedResponse<ConversationWithLastMessage>>>

    suspend fun getMessages(conversationId:String,page:Int,size:Int):  Response<ApiResponse<PagedResponse<MessageResponseModel>>>

    suspend fun sendMessage(msg: ChatMessage, file: List<File>?): Response<ApiResponse<MessageResponseModel>>

    suspend fun getMessageSince(conversationId:String, lastTime: Instant): Response<ApiResponse<List<MessageResponseModel>>>

    suspend fun getConversationsGroups(): Response<ApiResponse<List<ConversationWithLastMessage>>>
    suspend fun getConversationsUnRead(): Response<ApiResponse<List<ConversationWithLastMessage>>>

}