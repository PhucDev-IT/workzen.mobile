package vn.gmi.workzen.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import vn.gmi.workzen.data.datasource.local.conversation.ConversationLocalDataSource
import vn.gmi.workzen.data.datasource.remote.conversation.ConversationRemoteDataSource
import vn.gmi.workzen.data.di.IoDispatcher
import vn.gmi.workzen.data.models.request.conversation.ChatMessage
import vn.gmi.workzen.domain.entity.conversation.ConversationEntity
import vn.gmi.workzen.domain.entity.conversation.MessageEntity
import vn.gmi.workzen.domain.repository.ConversationRepository
import vn.gmi.workzen.networks.rest.ApiResult
import vn.gmi.workzen.networks.rest.toApiResult
import java.io.File
import javax.inject.Inject

class ConversationRepositoryImpl @Inject constructor(
    private val remoteDataSource: ConversationRemoteDataSource,
    private val localDataSource: ConversationLocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ConversationRepository {
    override suspend fun getConversationLocal(userId: String): Flow<List<ConversationEntity>> {
        return withContext(ioDispatcher) {
            localDataSource.getConversations(userId)
        }
    }

    override suspend fun getConversationRemote(
        userId: String,
        page: Int,
        size: Int
    ): List<ConversationEntity> {
        return withContext(ioDispatcher) {
            when (val result =
                remoteDataSource.getConversations(userId, page, size).toApiResult()) {
                is ApiResult.Success -> {
                    val data = result.data.data
                    val conversations = data?.map { it.mapToEntity() } ?: emptyList()
                    conversations
                }

                is ApiResult.Error -> throw Exception(result.message)
            }
        }
    }

    override suspend fun storeConversation(conversations: List<ConversationEntity>) {
        return withContext(ioDispatcher) {
            localDataSource.saveConversation(conversations)
        }
    }

    override suspend fun getMessageLocal(
        conversationId: String,
        limit: Int
    ):  Flow<List<MessageEntity>> {
        return withContext(ioDispatcher) {
            localDataSource.getMessages(conversationId, limit)
        }
    }

    override suspend fun getMessageRemote(
        conversationId: String,
        page: Int,
        size: Int
    ): List<MessageEntity> {
        return withContext(ioDispatcher) {
            when (val result =
                remoteDataSource.getMessages(conversationId, page, size).toApiResult()) {
                is ApiResult.Success -> {
                    val data = result.data
                    val messages = data.map { it.mapToEntity() }
                    messages
                }
                is ApiResult.Error -> throw Exception(result.message)
            }
        }
    }

    override suspend fun storeMessage(messages: List<MessageEntity>) {
        return withContext(ioDispatcher) {
            localDataSource.saveMessages(messages)
        }
    }

    override suspend fun findConversation(conversationId: String): ConversationEntity? {
        return withContext(ioDispatcher) {
            localDataSource.findConversation(conversationId)
        }
    }

    override suspend fun sendMessage(msg: ChatMessage, files: List<File>?): MessageEntity {
        return withContext(ioDispatcher) {
            when (val result = remoteDataSource.sendMessage(msg, files).toApiResult()){
                is ApiResult.Success -> {
                    val data = result.data
                    val entity = data.mapToEntity()
                    localDataSource.saveMessages(listOf(entity))
                    entity
                }
                is ApiResult.Error -> throw Exception(result.message)
            }
        }
    }
}