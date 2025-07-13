package vn.gmi.workzen.ui.chat.conversation

import vn.gmi.workzen.core.base.BaseContract
import vn.gmi.workzen.domain.entity.conversation.ConversationEntity

interface ConversationContract {
    interface View: BaseContract.View{
        fun onShowConversations(conversations:List<ConversationEntity>)
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun requestLoadConversations()
    }
}