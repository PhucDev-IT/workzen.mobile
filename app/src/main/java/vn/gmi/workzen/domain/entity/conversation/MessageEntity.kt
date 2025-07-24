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
    var updatedAt: RealmInstant?=null
    var senderId:String?=null
    var senderName:String?=null
    var senderAvatar:String?=null
    var isSent:Boolean?=null


    override fun toString(): String {
        return "MessageEntity(id='$id', conversationId=$conversationId, content=$content, subContent=$subContent, fileUrl=$fileUrl, senderId=$senderId), isSent=$isSent"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MessageEntity

        if (isSent != other.isSent) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isSent?.hashCode() ?: 0
        result = 31 * result + id.hashCode()
        return result
    }


}