package vn.gmi.workzen.ui.chat.conversation

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import vn.gmi.workzen.core.base.BaseContract
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.domain.usecase.GetConversationLocalUseCase
import vn.gmi.workzen.domain.usecase.GetConversationRemoteUseCase
import vn.gmi.workzen.domain.usecase.StoreConversationUseCase
import vn.gmi.workzen.manager.SessionManager
import javax.inject.Inject

class ConversationPresenter @Inject constructor(
    private val getConversationLocalUseCase: GetConversationLocalUseCase,
    private val getConversationRemoteUseCase: GetConversationRemoteUseCase,
    private val storeConversationUseCase: StoreConversationUseCase
) : BasePresenter<ConversationContract.View>(), ConversationContract.Presenter {

    private var page = 0
    private var size = 20
    private var userId: String? = SessionManager.profileState.value?.id


    override fun requestLoadConversations() {
        scope.launch {
            try {
                scope.launch {
                    getConversationLocalUseCase.invoke(userId.toString())
                        .collectLatest { messages ->
                            getView()?.onShowConversations(messages)
                        }
                }

                val maps = mapOf<String, Any>(
                    "page" to page,
                    "size" to size,
                    "userId" to userId.toString()
                )
                val result = getConversationRemoteUseCase.invoke(maps)
                storeConversationUseCase.invoke(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun reset() {
        page = 0
        size = 20
    }
}