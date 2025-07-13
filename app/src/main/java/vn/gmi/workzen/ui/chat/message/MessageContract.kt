package vn.gmi.workzen.ui.chat.message

import vn.gmi.workzen.core.base.BaseContract
import vn.gmi.workzen.domain.entity.conversation.MessageEntity

interface MessageContract {
    interface View : BaseContract.View {
        fun onLoadFirstData(items: List<MessageEntity>)
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun requestLoadMessages(conversationId:String)
    }
}