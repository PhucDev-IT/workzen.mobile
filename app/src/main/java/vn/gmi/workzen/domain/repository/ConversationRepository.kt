package vn.gmi.workzen.domain.repository

import kotlinx.coroutines.flow.Flow
import vn.gmi.workzen.domain.entity.conversation.ConversationEntity
import vn.gmi.workzen.domain.entity.conversation.MessageEntity

interface ConversationRepository {
    suspend fun getConversationLocal(userId:String): Flow<List<ConversationEntity>>
    suspend fun getConversationRemote(userId:String,page:Int,size:Int): List<ConversationEntity>
    suspend fun storeConversation(conversations: List<ConversationEntity>)
    suspend fun findConversation(conversationId:String): ConversationEntity?


    suspend fun getMessageLocal(conversationId:String, limit:Int): Flow<List<MessageEntity>>
    suspend fun getMessageRemote(conversationId:String,page:Int,size:Int): List<MessageEntity>
    suspend fun storeMessage(messages: List<MessageEntity>)

}