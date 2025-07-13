package vn.gmi.workzen.ui.chat.message

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.domain.usecase.GetMessagesLocalUseCase
import vn.gmi.workzen.domain.usecase.GetMessagesRemoteUseCase
import vn.gmi.workzen.domain.usecase.StoreMessagesUseCase
import javax.inject.Inject

class MessagePresenter @Inject constructor(
    private val getMessagesLocalUseCase: GetMessagesLocalUseCase,
    private val getMessagesRemoteUseCase: GetMessagesRemoteUseCase,
    private val storeMessagesUseCase: StoreMessagesUseCase
): BasePresenter<MessageContract.View>(), MessageContract.Presenter {



    override fun requestLoadMessages(conversationId: String) {
        scope.launch {
            try {
                val mapLocal = mapOf(
                    "conversationId" to conversationId,
                    "limit" to 50
                )
                scope.launch {
                    getMessagesLocalUseCase.invoke(mapLocal).collectLatest { messages ->
                        getView()?.onLoadFirstData(messages)
                    }
                }

                val mapRemote = mapOf(
                    "conversationId" to conversationId,
                    "page" to 0,
                    "size" to 50
                )
                val result = getMessagesRemoteUseCase.invoke(mapRemote)
                storeMessagesUseCase.invoke(result)

            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}