package vn.gmi.workzen.data.models.response.conversation

import io.realm.kotlin.ext.realmListOf
import vn.gmi.workzen.core.extensions.toRealmInstant
import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.data.models.request.conversation.ChatMessage
import vn.gmi.workzen.domain.entity.contract.ContractEntity
import vn.gmi.workzen.domain.entity.conversation.MessageEntity
import vn.gmi.workzen.domain.entity.enums.MessageType
import java.time.Instant
import java.util.UUID

class MessageResponseModel : DataMapper<MessageEntity>(){
    var id: String? = null
    var conversationId:String?=null
    var content:String?=null
    var subContent:String?=null
    var files: List<ChatMessage.FileMsgInfo>?=null
    var replyToMessageId:String?=null
    var messageType: MessageType?=null
    var isEdited: Boolean?=null
    var createdAt: Instant?=null
    var updatedAt: Instant?=null
    var sender: SenderRp?=null


    class SenderRp{
        var id:String?=null
        var name:String?=null
        var avatar:String?=null
    }

    override fun mapToEntity(): MessageEntity {
        val files = realmListOf<MessageEntity.FileMsgInfoEntity>().apply {
            this@MessageResponseModel.files?.forEach {
                add(it.mapToEntity())
            }
        }
        return MessageEntity().apply {
            id = this@MessageResponseModel.id ?: UUID.randomUUID().toString()
            conversationId = this@MessageResponseModel.conversationId
            content = this@MessageResponseModel.content
            subContent = this@MessageResponseModel.subContent
            this.files= files
            replyToMessageId = this@MessageResponseModel.replyToMessageId
            messageType = this@MessageResponseModel.messageType?.name
            isEdited = this@MessageResponseModel.isEdited
            createdAt = this@MessageResponseModel.createdAt?.toRealmInstant()
            updatedAt = this@MessageResponseModel.updatedAt?.toRealmInstant()
            senderId = this@MessageResponseModel.sender?.id
            senderName = this@MessageResponseModel.sender?.name
            senderAvatar = this@MessageResponseModel.sender?.avatar
            isSent = true

        }
    }

}

