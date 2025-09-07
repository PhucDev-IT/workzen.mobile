package vn.gmi.workzen.domain.usecase

import kotlinx.coroutines.flow.Flow
import vn.gmi.workzen.core.usecases.BaseUseCase
import vn.gmi.workzen.data.mapper.PagedResponse
import vn.gmi.workzen.data.models.request.conversation.ChatMessage
import vn.gmi.workzen.domain.entity.conversation.ConversationEntity
import vn.gmi.workzen.domain.entity.conversation.MessageEntity
import vn.gmi.workzen.domain.repository.ConversationRepository
import java.io.File
import java.time.Instant

class GetConversationLocalUseCase(private val conversationRepository: ConversationRepository): BaseUseCase<String, Flow<List<ConversationEntity>>>() {
    override suspend fun invoke(params: String): Flow<List<ConversationEntity>> {
        return conversationRepository.getConversationLocal(params)
    }
}

class FindConversationUseCase(private val conversationRepository: ConversationRepository): BaseUseCase<String, ConversationEntity?>() {
    override suspend fun invoke(params: String): ConversationEntity? {
        return conversationRepository.findConversation(params)
    }
}

class SendMessageUseCase(
    private val conversationRepository: ConversationRepository
) : BaseUseCase<Pair<ChatMessage, List<File>?>, MessageEntity>() {

    override suspend fun invoke(params: Pair<ChatMessage, List<File>?>): MessageEntity {
        val (msg, files) = params
        return conversationRepository.sendMessage(msg, files)
    }
}


class GetConversationRemoteUseCase(private val conversationRepository: ConversationRepository): BaseUseCase<Map<String,Any>, List<ConversationEntity>>(){
    override suspend fun invoke(params: Map<String, Any>): List<ConversationEntity> {
        return conversationRepository.getConversationRemote((params["userId"]?:"").toString(),
            (params["page"]?:0) as Int, (params["size"]?:0) as Int
        )
    }
}

class StoreConversationUseCase(private val conversationRepository: ConversationRepository): BaseUseCase<List<ConversationEntity>, Unit>(){
    override suspend fun invoke(params: List<ConversationEntity>) {
        return conversationRepository.storeConversation(params)
    }
}

class GetMessagesLocalUseCase(
    private val conversationRepository: ConversationRepository
) : BaseUseCase<Map<String, Any>, Flow<List<MessageEntity>>>() {

    override suspend fun invoke(params: Map<String, Any>): Flow<List<MessageEntity>> {
        val conversationId = params["conversationId"] as? String ?: ""
        val limit = params["limit"] as? Int ?: 20
        val page = params["page"] as? Int ?: 0
        return conversationRepository.getMessageLocal(conversationId, limit, page)
    }
}

class GetMessagesRemoteUseCase(private val conversationRepository: ConversationRepository): BaseUseCase<Map<String,Any>, PagedResponse<MessageEntity>>(){
    override suspend fun invoke(params: Map<String, Any>): PagedResponse<MessageEntity> {
        return conversationRepository.getMessageRemote((params["conversationId"]?:"").toString(), (params["page"]?:0) as Int, (params["size"]?:0) as Int)
    }
}

class StoreMessagesUseCase(private val conversationRepository: ConversationRepository): BaseUseCase<List<MessageEntity>, Unit>(){
    override suspend fun invoke(params: List<MessageEntity>) {
        return conversationRepository.storeMessage(params)
    }
}

class ClearMessageConversationIdUseCase(private val conversationRepository: ConversationRepository) : BaseUseCase<String, Unit>() {
    override suspend fun invoke(params: String) {
        return conversationRepository.clearMessageConversationId(params)
    }
}

class GetMessagesSinceRemoteUseCase(private val conversationRepository: ConversationRepository): BaseUseCase<Map<String,Any>, List<MessageEntity>>(){
    override suspend fun invoke(params: Map<String, Any>): List<MessageEntity> {
        return conversationRepository.getMessageSinceRemote((params["conversationId"]?:"").toString(), (params["lastTime"]?:0) as Instant)
    }
}

class GetConversationTypeGroupRemoteUseCase(private val conversationRepository: ConversationRepository): BaseUseCase<Unit, List<ConversationEntity>>(){
    override suspend fun invoke(params: Unit): List<ConversationEntity> {
        return conversationRepository.getConversationsTypeGroupRemote()
    }
}

class GetConversationUnReadRemoteUseCase(private val conversationRepository: ConversationRepository): BaseUseCase<Unit, List<ConversationEntity>>(){
    override suspend fun invoke(params: Unit): List<ConversationEntity> {
        return conversationRepository.getConversationsUnReadRemote()
    }
}