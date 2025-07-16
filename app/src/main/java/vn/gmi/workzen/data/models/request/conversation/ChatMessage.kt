package vn.gmi.workzen.data.models.request.conversation

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.internal.interop.Enumerated
import vn.gmi.workzen.core.extensions.toRealmInstant
import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.domain.entity.conversation.MessageEntity
import vn.gmi.workzen.domain.entity.enums.MessageType
import vn.gmi.workzen.manager.SessionManager
import java.time.Instant
import java.util.UUID


class ChatMessage : DataMapper<MessageEntity> (){
     private val id: String = "${System.currentTimeMillis()}_${UUID.randomUUID().toString()}"
    var conversationId: String? = null
    var senderId: String? = null

    var messageType: MessageType? = null // TEXT, IMAGE, FILE, EMOJI

     var content: String? = null

    var subContent: String? = null //Thông báo nhỏ, ví dụ add member, remove,...

    var file: List<FileMsgInfo>? = null // base64-encoded content


    class FileMsgInfo{
        var id: String? = null
        var file: String? = null
        var fileName: String? = null
        var fileType: String? = null
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as FileMsgInfo

            return id == other.id
        }

        override fun hashCode(): Int {
            return id?.hashCode() ?: 0
        }


    }

    var createdAt: String? = null
    var isEdited = false
    var replyMessageId: String? = null
    var receiverId: String? = null // For 1-1 messages or FCM target


    override fun mapToEntity(): MessageEntity {
        val realmFiles = realmListOf<String>().apply {
            this@ChatMessage.file?.forEach { file->
                file.file?.let { add(it) }

            }
        }
        val profile = SessionManager.profileState.value
        return MessageEntity().apply {
            id = this@ChatMessage.id
            conversationId = this@ChatMessage.conversationId
            senderId = this@ChatMessage.senderId
            messageType = this@ChatMessage.messageType?.name
            content = this@ChatMessage.content
            subContent = this@ChatMessage.subContent
            fileUrl = realmFiles
            createdAt = Instant.parse(this@ChatMessage.createdAt)?.toRealmInstant()
            isEdited = this@ChatMessage.isEdited
            replyToMessageId = this@ChatMessage.replyMessageId
            senderId = profile?.id
            senderName = profile?.fullName
            senderAvatar = profile?.avatarUrl


        }
    }
}