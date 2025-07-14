package vn.gmi.workzen.domain.entity.conversation

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.data.models.response.notification.NotificationStatus
import vn.gmi.workzen.domain.entity.enums.ConversationType
import vn.gmi.workzen.domain.entity.notification.NotifyType
import java.io.Serializable
import java.time.Instant
import java.util.UUID

class ConversationEntity:  RealmObject, DataMapper<ConversationSerializable>(){
    @PrimaryKey
    var conversationId:String = ""
    var conversationName:String?=null
    var avatarUrl:String?=null
    var role:String?=null
    var lastReadAt: RealmInstant?=null
    var lastMessageContent:String?=null
    var lastMessageSubContent:String?=null
    var lastMessageCreatedAt:RealmInstant?=null
    var lastMessageSenderId:String?=null
    var unreadCount:Int?=null
    var type: String = ConversationType.PRIVATE.name
    var userId:String?=null

    override fun mapToEntity(): ConversationSerializable {
        return ConversationSerializable().apply {
            conversationId = this@ConversationEntity.conversationId
            conversationName = this@ConversationEntity.conversationName
            avatarUrl = this@ConversationEntity.avatarUrl
            role = this@ConversationEntity.role
            lastReadAt = this@ConversationEntity.lastReadAt
            lastMessageContent = this@ConversationEntity.lastMessageContent
            lastMessageSubContent = this@ConversationEntity.lastMessageSubContent
            lastMessageCreatedAt = this@ConversationEntity.lastMessageCreatedAt
            lastMessageSenderId = this@ConversationEntity.lastMessageSenderId
            unreadCount = this@ConversationEntity.unreadCount
            type = this@ConversationEntity.type
            userId = this@ConversationEntity.userId
        }
    }
}


open class ConversationSerializable : Serializable{
    var conversationId:String = ""
    var conversationName:String?=null
    var avatarUrl:String?=null
    var role:String?=null
    var lastReadAt: RealmInstant?=null
    var lastMessageContent:String?=null
    var lastMessageSubContent:String?=null
    var lastMessageCreatedAt:RealmInstant?=null
    var lastMessageSenderId:String?=null
    var unreadCount:Int?=null
    var type: String = ConversationType.PRIVATE.name
    var userId:String?=null
}