package vn.gmi.workzen.data.datasource.local.conversation

import kotlinx.coroutines.flow.Flow
import vn.gmi.workzen.domain.entity.conversation.ConversationEntity
import vn.gmi.workzen.domain.entity.conversation.MessageEntity

interface ConversationLocalDataSource {
    suspend fun getConversations(userId:String): Flow<List<ConversationEntity>>
    suspend fun saveConversation(conversations: List<ConversationEntity>)
    suspend fun findConversation(conversationId:String): ConversationEntity?

    suspend fun getMessages(conversationId:String, limit:Int, page:Int): Flow<List<MessageEntity>>
    suspend fun saveMessages(messages: List<MessageEntity>)
    suspend fun clearMessage(conversationId: String)


}