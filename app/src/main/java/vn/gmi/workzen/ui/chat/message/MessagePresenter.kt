package vn.gmi.workzen.ui.chat.message

import android.annotation.SuppressLint
import androidx.core.net.toUri
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import vn.gmi.workzen.MyApplication
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.data.models.request.conversation.ChatMessage
import vn.gmi.workzen.domain.usecase.FindConversationUseCase
import vn.gmi.workzen.domain.usecase.GetConversationLocalUseCase
import vn.gmi.workzen.domain.usecase.GetMessagesLocalUseCase
import vn.gmi.workzen.domain.usecase.GetMessagesRemoteUseCase
import vn.gmi.workzen.domain.usecase.SendMessageUseCase
import vn.gmi.workzen.domain.usecase.StoreMessagesUseCase
import vn.gmi.workzen.utils.Utils
import java.io.File
import javax.inject.Inject

class MessagePresenter @Inject constructor(
    private val getMessagesLocalUseCase: GetMessagesLocalUseCase,
    private val getMessagesRemoteUseCase: GetMessagesRemoteUseCase,
    private val storeMessagesUseCase: StoreMessagesUseCase,
    private val findConversationUseCase: FindConversationUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : BasePresenter<MessageContract.View>(), MessageContract.Presenter {


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

    override fun requestLoadMessages(conversationId: String) {
        scope.launch {
            try {
                val mapLocal = mapOf(
                    "conversationId" to conversationId,
                    "limit" to 1000
                )
                scope.launch {
                    getMessagesLocalUseCase.invoke(mapLocal).collectLatest { messages ->
                        getView()?.onLoadFirstData(messages)
                    }
                }

                val mapRemote = mapOf(
                    "conversationId" to conversationId,
                    "page" to 0,
                    "size" to 1000
                )
                val result = getMessagesRemoteUseCase.invoke(mapRemote)
                storeMessagesUseCase.invoke(result)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun sendMessage(msgs: List<ChatMessage>) {
        scope.launch {
            try {
                msgs.forEach { msg ->
                    val files = msg.file?.mapNotNull { data ->
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