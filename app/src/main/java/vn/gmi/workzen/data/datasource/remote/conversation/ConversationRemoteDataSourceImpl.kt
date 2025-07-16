package vn.gmi.workzen.data.datasource.remote.conversation

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import vn.gmi.workzen.data.mapper.PagedResponse
import vn.gmi.workzen.data.models.request.conversation.ChatMessage
import vn.gmi.workzen.data.models.response.conversation.ConversationWithLastMessage
import vn.gmi.workzen.data.models.response.conversation.MessageResponseModel
import vn.gmi.workzen.domain.entity.conversation.MessageEntity
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.networks.api.AuthenticationService
import vn.gmi.workzen.networks.api.ConversationService
import vn.gmi.workzen.networks.models.ApiResponse
import java.io.File

class ConversationRemoteDataSourceImpl(private val apiService: ConversationService): ConversationRemoteDataSource  {
    override suspend fun getConversations(
        userId: String,
        page: Int,
        size: Int
    ):Response<ApiResponse<PagedResponse<ConversationWithLastMessage>>> {
        return apiService.getConversations(page,size,userId)
    }

    override suspend fun getMessages(
        conversationId: String,
        page: Int,
        size: Int
    ): Response<ApiResponse<List<MessageResponseModel>>> {
        return apiService.getMessages(conversationId,page,size)
    }

    override suspend fun sendMessage(msg: ChatMessage, files: List<File>?): Response<ApiResponse<MessageResponseModel>> {
        val jsonMessage = ApiService.instance.GSON.toJson(msg)
        val messageBody = jsonMessage.toRequestBody("application/json; charset=utf-8".toMediaType())

        val filesBody = files?.map { file ->
            val requestFile = file.asRequestBody("multipart/form-data".toMediaType())
            MultipartBody.Part.createFormData("files", file.name, requestFile)
        }

        return apiService.sendMessage(filesBody,messageBody)
    }
}