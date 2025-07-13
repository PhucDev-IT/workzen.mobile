package vn.gmi.workzen.data.datasource.local.conversation

import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.copyFromRealm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import vn.gmi.workzen.data.database.RealmProvider
import vn.gmi.workzen.domain.entity.conversation.ConversationEntity
import vn.gmi.workzen.domain.entity.conversation.MessageEntity

class ConversationLocalDataSourceImpl : ConversationLocalDataSource {
    override suspend fun getConversations(userId:String): Flow<List<ConversationEntity>> {
        return RealmProvider.realm.query<ConversationEntity>("userId == $0",userId)
            .sort("lastMessageCreatedAt", Sort.ASCENDING).asFlow().map { it.list.copyFromRealm() }
    }

    override suspend fun saveConversation(conversations: List<ConversationEntity>) {
        RealmProvider.realm.write {
            conversations.forEach {
                copyToRealm(it, UpdatePolicy.ALL)
            }
        }
    }

    override suspend fun getMessages(conversationId: String): Flow<List<MessageEntity>> {
        return RealmProvider.realm.query<MessageEntity>("conversationId == $0",conversationId).sort("createdAt" ,
            Sort.ASCENDING).limit(50).asFlow().map { it.list.copyFromRealm() }
    }

    override suspend fun saveMessages(messages: List<MessageEntity>) {
        RealmProvider.realm.write {
            messages.forEach {
                copyToRealm(it, UpdatePolicy.ALL)
            }

        }
    }
}