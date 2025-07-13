package vn.gmi.workzen.domain.entity.conversation

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import vn.gmi.workzen.data.models.response.notification.NotificationStatus
import vn.gmi.workzen.domain.entity.enums.ConversationType
import vn.gmi.workzen.domain.entity.notification.NotifyType
import java.time.Instant
import java.util.UUID

class ConversationEntity: RealmObject{
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
}


