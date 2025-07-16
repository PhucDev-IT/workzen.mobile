package vn.gmi.workzen.ui.chat.message

import vn.gmi.workzen.core.base.BaseContract
import vn.gmi.workzen.data.models.request.conversation.ChatMessage
import vn.gmi.workzen.domain.entity.conversation.ConversationEntity
import vn.gmi.workzen.domain.entity.conversation.MessageEntity

interface MessageContract {
    interface View : BaseContract.View {
        fun onLoadFirstData(items: List<MessageEntity>)
        fun onLoadConversationSuccess(conversation: ConversationEntity?)
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun requestLoadMessages(conversationId:String)
        fun getInfoConversation(conversationId: String)
        fun sendMessage(messages: List<ChatMessage> )
    }
}