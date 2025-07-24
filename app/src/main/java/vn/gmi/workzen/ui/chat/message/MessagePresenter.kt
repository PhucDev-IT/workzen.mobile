package vn.gmi.workzen.ui.chat.message

import android.annotation.SuppressLint
import androidx.core.net.toUri
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import vn.gmi.workzen.MyApplication
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.extensions.toInstant
import vn.gmi.workzen.data.models.request.conversation.ChatMessage
import vn.gmi.workzen.domain.usecase.ClearMessageConversationIdUseCase
import vn.gmi.workzen.domain.usecase.FindConversationUseCase
import vn.gmi.workzen.domain.usecase.GetConversationLocalUseCase
import vn.gmi.workzen.domain.usecase.GetMessagesLocalUseCase
import vn.gmi.workzen.domain.usecase.GetMessagesRemoteUseCase
import vn.gmi.workzen.domain.usecase.GetMessagesSinceRemoteUseCase
import vn.gmi.workzen.domain.usecase.SendMessageUseCase
import vn.gmi.workzen.domain.usecase.StoreMessagesUseCase
import vn.gmi.workzen.utils.Utils
import java.io.File
import java.time.Instant
import javax.inject.Inject

class MessagePresenter @Inject constructor(
    private val getMessagesLocalUseCase: GetMessagesLocalUseCase,
    private val getMessagesRemoteUseCase: GetMessagesRemoteUseCase,
    private val storeMessagesUseCase: StoreMessagesUseCase,
    private val findConversationUseCase: FindConversationUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val clearMessageConversationIdUseCase: ClearMessageConversationIdUseCase,
    private val getMessagesSinceRemoteUseCase: GetMessagesSinceRemoteUseCase
) : BasePresenter<MessageContract.View>(), MessageContract.Presenter {

    private var page = 0;
    private var size = 1000;
    private var lastTime: Instant? = null

    @SuppressLint("SuspiciousIndentation")
    override fun getInfoConversation(conversationId: String) {
        scope.launch {
            try {
                val conversation = findConversationUseCase.invoke(conversationId)
                getView()?.onLoadConversationSuccess(conversation)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Chỉ lấy những tin nhắn mơ nhất tính từ lúc chưa đọc
    override fun requestLoadMessages(conversationId: String) {
        scope.launch {
            try {
                val mapLocal = mapOf(
                    "conversationId" to conversationId,
                    "limit" to size,
                    "page" to page
                )
                scope.launch {
                    getMessagesLocalUseCase.invoke(mapLocal).collectLatest { messages ->
                        lastTime = messages.lastOrNull()?.updatedAt?.toInstant()
                        getView()?.onLoadFirstData(messages)
                    }
                }

                if (lastTime == null) {
                    val mapRemote = mapOf(
                        "conversationId" to conversationId,
                        "page" to page,
                        "size" to size
                    )
                    val result = getMessagesRemoteUseCase.invoke(mapRemote)
                    result.data?.let {
                        storeMessagesUseCase.invoke(it)
                        if ((result.totalElements ?: 0) > size) {
                            page++
                        }
                    }

                } else {
                    val mapRemote = mapOf(
                        "conversationId" to conversationId,
                        "lastTime" to lastTime!!,
                    )
                    val result = getMessagesSinceRemoteUseCase.invoke(mapRemote)
                    storeMessagesUseCase.invoke(result)
                }
                //  clearMessageConversationIdUseCase.invoke(conversationId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun sendMessage(msgs: List<ChatMessage>) {
        scope.launch {
            try {
                scope.launch {
                    storeMessagesUseCase.invoke(msgs.map { it.mapToEntity() })
                }
                msgs.forEach { msg ->
                    val files = msg.files?.mapNotNull { data ->
                        Utils.uriToFile(MyApplication.instance, data.file?.toUri()!!)
                    }
                    val pair = Pair(msg, files)
                    sendMessageUseCase.invoke(pair)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}