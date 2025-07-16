package vn.gmi.workzen.domain.entity.conversation

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class MessageEntity : RealmObject{
    @PrimaryKey
    var id: String = ""
    var conversationId:String?=null
    var content:String?=null
    var subContent:String?=null
    var fileUrl: RealmList<String> = realmListOf()
    var replyToMessageId:String?=null
    var messageType: String?=null
    var isEdited: Boolean?=null
    var createdAt: RealmInstant?=null
    var senderId:String?=null
    var senderName:String?=null
    var senderAvatar:String?=null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MessageEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "MessageEntity(id='$id', conversationId=$conversationId, content=$content, subContent=$subContent, fileUrl=$fileUrl, senderId=$senderId)"
    }


}