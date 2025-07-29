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


class ChatMessage : DataMapper<MessageEntity>() {
    private val id: String = "${System.currentTimeMillis()}_${UUID.randomUUID().toString()}"
    var conversationId: String? = null
    var senderId: String? = null
    var messageType: MessageType? = null // TEXT, IMAGE, FILE, EMOJI
    var content: String? = null
    var subContent: String? = null //Thông báo nhỏ, ví dụ add member, remove,...
    var files: List<FileMsgInfo>? = null


    class FileMsgInfo : DataMapper<MessageEntity.FileMsgInfoEntity>(){
        var id: String? = null
        var filePath: String? = null
        var fileName: String? = null
        var fileSize: Long? = null
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


        override fun mapToEntity(): MessageEntity.FileMsgInfoEntity {
            return MessageEntity.FileMsgInfoEntity().apply {
                this.id = this@FileMsgInfo.id
                this.filePath = this@FileMsgInfo.filePath
                this.fileName = this@FileMsgInfo.fileName
                this.fileSize = this@FileMsgInfo.fileSize
                this.fileType = this@FileMsgInfo.fileType
            }
        }

        override fun toString(): String {
            return "FileMsgInfo(id=$id, filePath=$filePath, fileName=$fileName, fileSize=$fileSize, fileType=$fileType)"
        }
    }

    var createdAt: String? = null
    var isEdited = false
    var replyMessageId: String? = null
    var receiverId: String? = null // For 1-1 messages or FCM target
    var isSent: Boolean? = null

    override fun mapToEntity(): MessageEntity {
        val realmFiles = realmListOf<MessageEntity.FileMsgInfoEntity>().apply {
            this@ChatMessage.files?.forEach { file ->
                add(file.mapToEntity())
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
            files = realmFiles
            createdAt = Instant.parse(this@ChatMessage.createdAt)?.toRealmInstant()
            isEdited = this@ChatMessage.isEdited
            replyToMessageId = this@ChatMessage.replyMessageId
            senderId = profile?.id
            senderName = profile?.fullName
            senderAvatar = profile?.avatarUrl
            isSent = this@ChatMessage.isSent ?: false

        }
    }

    override fun toString(): String {
        return "ChatMessage(id='$id', conversationId=$conversationId, senderId=$senderId, messageType=$messageType, content=$content, subContent=$subContent, files=$files, createdAt=$createdAt, isEdited=$isEdited, replyMessageId=$replyMessageId, receiverId=$receiverId, isSent=$isSent)"
    }


}