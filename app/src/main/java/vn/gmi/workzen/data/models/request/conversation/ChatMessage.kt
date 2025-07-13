package vn.gmi.workzen.data.models.request.conversation

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

    var file: String? = null // base64-encoded content
    var fileName: String? = null // Optional: name of the file
    var fileType: String? = null // Optional: image/png, application/pdf, etc.
    var fileSize: Long? = null // Optional


    var createdAt: String? = null
    var isEdited = false
    var replyMessageId: String? = null
    var receiverId: String? = null // For 1-1 messages or FCM target

    override fun mapToEntity(): MessageEntity {
        val profile = SessionManager.profileState.value
        return MessageEntity().apply {
            id = this@ChatMessage.id
            conversationId = this@ChatMessage.conversationId
            senderId = this@ChatMessage.senderId
            messageType = this@ChatMessage.messageType?.name
            content = this@ChatMessage.content
            subContent = this@ChatMessage.subContent
            fileUrl = this@ChatMessage.file
            createdAt = Instant.parse(this@ChatMessage.createdAt)?.toRealmInstant()
            isEdited = this@ChatMessage.isEdited
            replyToMessageId = this@ChatMessage.replyMessageId
            senderId = profile?.id
            senderName = profile?.fullName
            senderAvatar = profile?.avatarUrl


        }
    }
}