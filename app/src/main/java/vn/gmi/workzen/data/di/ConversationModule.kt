package vn.gmi.workzen.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import vn.gmi.workzen.data.datasource.local.conversation.ConversationLocalDataSource
import vn.gmi.workzen.data.datasource.local.conversation.ConversationLocalDataSourceImpl
import vn.gmi.workzen.data.datasource.remote.conversation.ConversationRemoteDataSource
import vn.gmi.workzen.data.datasource.remote.conversation.ConversationRemoteDataSourceImpl
import vn.gmi.workzen.data.repository.ConversationRepositoryImpl
import vn.gmi.workzen.domain.repository.ConversationRepository
import vn.gmi.workzen.domain.usecase.ClearMessageConversationIdUseCase
import vn.gmi.workzen.domain.usecase.FindConversationUseCase
import vn.gmi.workzen.domain.usecase.GetConversationLocalUseCase
import vn.gmi.workzen.domain.usecase.GetConversationRemoteUseCase
import vn.gmi.workzen.domain.usecase.GetMessagesLocalUseCase
import vn.gmi.workzen.domain.usecase.GetMessagesRemoteUseCase
import vn.gmi.workzen.domain.usecase.SendMessageUseCase
import vn.gmi.workzen.domain.usecase.StoreConversationUseCase
import vn.gmi.workzen.domain.usecase.StoreMessagesUseCase
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.networks.api.ConversationService
import vn.gmi.workzen.ui.chat.conversation.ConversationContract
import vn.gmi.workzen.ui.chat.conversation.ConversationPresenter
import vn.gmi.workzen.ui.chat.message.MessageContract
import vn.gmi.workzen.ui.chat.message.MessagePresenter

@Module
@InstallIn(SingletonComponent::class)
class ConversationModule {
    @Provides
    fun provideConversationApi(): ConversationService {
        return ApiService.instance.conversationService
    }

    //===========================  USE CASE ===========================

    @Provides
    fun provideGetConversationLocalUseCase(repo: ConversationRepository): GetConversationLocalUseCase {
        return GetConversationLocalUseCase(repo)
    }

    @Provides
    fun provideGetConversationRemoteUseCase(repo: ConversationRepository): GetConversationRemoteUseCase {
        return GetConversationRemoteUseCase(repo)
    }


    @Provides
    fun provideStoreConversationUseCase(repo: ConversationRepository): StoreConversationUseCase {
        return StoreConversationUseCase(repo)
    }

    @Provides
    fun providerGetMessagesLocalUseCase(repo: ConversationRepository): GetMessagesLocalUseCase {
        return GetMessagesLocalUseCase(repo)
    }

    @Provides
    fun providerGetMessagesRemoteUseCase(repo: ConversationRepository): GetMessagesRemoteUseCase {
        return GetMessagesRemoteUseCase(repo)
    }

    @Provides
    fun providerStoreMessagesUseCase(repo: ConversationRepository): StoreMessagesUseCase {
        return StoreMessagesUseCase(repo)
    }

    @Provides
    fun providerFindConversationUseCase(repo: ConversationRepository): FindConversationUseCase {
        return FindConversationUseCase(repo)
    }

    @Provides
    fun provideSendMessageUseCase(repo: ConversationRepository): SendMessageUseCase {
        return SendMessageUseCase(repo)
    }

    @Provides
    fun provideClearMessageConversationIdUseCase(repo: ConversationRepository): ClearMessageConversationIdUseCase {
        return ClearMessageConversationIdUseCase(repo)
    }

    //============================== DATA SOURCE ===============================
    @Provides
    fun provideConversationLocalDataSource(): ConversationLocalDataSource {
        return ConversationLocalDataSourceImpl()
    }

    @Provides
    fun providerConversationRemoteDataSource(service: ConversationService): ConversationRemoteDataSource {
        return ConversationRemoteDataSourceImpl(service)
    }


    @Provides
    fun providerConversationRepository(
        remote: ConversationRemoteDataSource,
        local: ConversationLocalDataSource,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): ConversationRepository {
        return ConversationRepositoryImpl(remote, local, dispatcher)
    }

    // ============================ BIND VIEW ==========================
    @Provides
    fun provideConversationPresenter(
        getConversationRemoteUseCase: GetConversationRemoteUseCase,
        getConversationLocalUseCase: GetConversationLocalUseCase,
        storeConversationUseCase: StoreConversationUseCase
    ): ConversationContract.Presenter{
        return ConversationPresenter(getConversationLocalUseCase,getConversationRemoteUseCase,storeConversationUseCase)
    }

    @Provides
    fun provideMessagePresenter(
        getMessagesLocalUseCase: GetMessagesLocalUseCase,
        getMessagesRemoteUseCase: GetMessagesRemoteUseCase,
        storeMessagesUseCase: StoreMessagesUseCase,
        findConversationUseCase: FindConversationUseCase,
        sendMessageUseCase: SendMessageUseCase,
        clearMessageConversationIdUseCase: ClearMessageConversationIdUseCase
    ): MessageContract.Presenter{
        return MessagePresenter(
            getMessagesLocalUseCase,
            getMessagesRemoteUseCase,
            storeMessagesUseCase,
            findConversationUseCase,
            sendMessageUseCase,
            clearMessageConversationIdUseCase
        )

    }

}