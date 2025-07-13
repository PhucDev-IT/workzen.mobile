package vn.gmi.workzen.domain.usecase

import kotlinx.coroutines.flow.Flow
import vn.gmi.workzen.core.usecases.BaseUseCase
import vn.gmi.workzen.domain.entity.conversation.ConversationEntity
import vn.gmi.workzen.domain.entity.conversation.MessageEntity
import vn.gmi.workzen.domain.repository.ConversationRepository


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
        return conversationRepository.getMessageLocal(conversationId, limit)
    }
}

class GetMessagesRemoteUseCase(private val conversationRepository: ConversationRepository): BaseUseCase<Map<String,Any>, List<MessageEntity>>(){
    override suspend fun invoke(params: Map<String, Any>): List<MessageEntity> {
        return conversationRepository.getMessageRemote((params["conversationId"]?:"").toString(), (params["page"]?:0) as Int, (params["size"]?:0) as Int)
    }
}

class StoreMessagesUseCase(private val conversationRepository: ConversationRepository): BaseUseCase<List<MessageEntity>, Unit>(){
    override suspend fun invoke(params: List<MessageEntity>) {
        return conversationRepository.storeMessage(params)
    }
}