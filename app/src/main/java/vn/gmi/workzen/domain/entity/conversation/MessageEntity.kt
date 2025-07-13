package vn.gmi.workzen.domain.entity.conversation

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class MessageEntity : RealmObject{
    @PrimaryKey
    var id: String = ""
    var conversationId:String?=null
    var content:String?=null
    var subContent:String?=null
    var fileUrl:String?=null
    var replyToMessageId:String?=null
    var messageType: String?=null
    var isEdited: Boolean?=null
    var createdAt: RealmInstant?=null
    var senderId:String?=null
    var senderName:String?=null
    var senderAvatar:String?=null

}