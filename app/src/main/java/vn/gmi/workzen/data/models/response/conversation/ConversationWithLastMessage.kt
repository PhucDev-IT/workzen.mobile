package vn.gmi.workzen.data.models.response.conversation

import vn.gmi.workzen.core.extensions.toRealmInstant
import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.domain.entity.conversation.ConversationEntity
import vn.gmi.workzen.domain.entity.enums.MessageType
import vn.gmi.workzen.manager.SessionManager
import java.time.Instant
import java.util.UUID

class ConversationWithLastMessage : DataMapper<ConversationEntity>(){
    var conversationId:String?=null
    var conversationName:String?=null
    var avatarUrl:String?=null
    var role:String?=null
    var lastReadAt: Instant?=null
    var lastMessageContent:String?=null
    var lastMessageSubContent:String?=null
    var lastMessageCreatedAt:Instant?=null
    var lastMessageSenderId:String?=null
    var unreadCount:Int?=null
    var conversationType:String?=null
    var lastMessageSenderName:String?=null
    var lastMessageType: MessageType?=null


    override fun mapToEntity(): ConversationEntity {
        val userId = SessionManager.profileState.value?.id
        return ConversationEntity().apply {
            this.conversationId = this@ConversationWithLastMessage.conversationId ?: UUID.randomUUID().toString()
            this.conversationName = this@ConversationWithLastMessage.conversationName
            this.avatarUrl = this@ConversationWithLastMessage.avatarUrl
            this.role = this@ConversationWithLastMessage.role
            this.lastReadAt = this@ConversationWithLastMessage.lastReadAt?.toRealmInstant()
            this.lastMessageContent = this@ConversationWithLastMessage.lastMessageContent
            this.lastMessageSubContent = this@ConversationWithLastMessage.lastMessageSubContent
            this.lastMessageCreatedAt = this@ConversationWithLastMessage.lastMessageCreatedAt?.toRealmInstant()
            this.lastMessageSenderId = this@ConversationWithLastMessage.lastMessageSenderId
            this.unreadCount = this@ConversationWithLastMessage.unreadCount
            this.type = this@ConversationWithLastMessage.conversationType.toString()
            this.userId =userId
            this.lastMessageSenderName = this@ConversationWithLastMessage.lastMessageSenderName
            this.lastMessageType = this@ConversationWithLastMessage.lastMessageType?.name
        }
    }
}