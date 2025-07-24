package vn.gmi.workzen.domain.repository

import kotlinx.coroutines.flow.Flow
import vn.gmi.workzen.data.mapper.PagedResponse
import vn.gmi.workzen.data.models.request.conversation.ChatMessage
import vn.gmi.workzen.domain.entity.conversation.ConversationEntity
import vn.gmi.workzen.domain.entity.conversation.MessageEntity
import java.io.File
import java.time.Instant

interface ConversationRepository {
    suspend fun getConversationLocal(userId:String): Flow<List<ConversationEntity>>
    suspend fun getConversationRemote(userId:String,page:Int,size:Int): List<ConversationEntity>
    suspend fun storeConversation(conversations: List<ConversationEntity>)
    suspend fun findConversation(conversationId:String): ConversationEntity?


    suspend fun getMessageLocal(conversationId:String, limit:Int, page:Int): Flow<List<MessageEntity>>
    suspend fun getMessageRemote(conversationId:String,page:Int,size:Int):  PagedResponse<MessageEntity>
    suspend fun storeMessage(messages: List<MessageEntity>)
    suspend fun sendMessage(msg: ChatMessage, files: List<File>?):MessageEntity
    suspend fun clearMessageConversationId(conversationId:String)
    suspend fun getMessageSinceRemote(conversationId:String, lastTime: Instant): List<MessageEntity>

    suspend fun getConversationsTypeGroupRemote(): List<ConversationEntity>
    suspend fun getConversationsUnReadRemote(): List<ConversationEntity>
}