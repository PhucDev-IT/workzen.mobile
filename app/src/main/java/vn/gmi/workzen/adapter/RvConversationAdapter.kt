package vn.gmi.workzen.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.gmi.workzen.core.base.BaseAdapter
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.core.extensions.toHourMinute
import vn.gmi.workzen.databinding.ItemChatBinding
import vn.gmi.workzen.domain.entity.conversation.ConversationEntity
import vn.gmi.workzen.domain.entity.enums.ConversationType
import vn.gmi.workzen.domain.entity.enums.MessageType
import vn.gmi.workzen.utils.MySharedPreferences

class RvConversationAdapter(private val onClick: (ConversationEntity) -> Unit) :
    BaseAdapter<ConversationEntity>() {

    private val currentUserId = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_USER_ID)

    @SuppressLint("NotifyDataSetChanged")
    fun resetAndAddAll(list: List<ConversationEntity>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }


    class ConversationViewHolder(itemView: View) : ItemViewHolder(itemView) {
        val binding = ItemChatBinding.bind(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun bindView(
        holder: ItemViewHolder,
        item: ConversationEntity
    ) {
        val view = holder as ConversationViewHolder
        with(view.binding) {
            tvName.text = item.conversationName
            Glide.with(holder.itemView.context).load(item.avatarUrl).into(imgAvatar)

            tvTime.text = item.lastMessageCreatedAt?.toHourMinute()
            if ((item.unreadCount ?: 0) > 0) {
                tvCount.text = item.unreadCount.toString()
                llUnreadMessage.visibility = View.VISIBLE
            } else {
                llUnreadMessage.visibility = View.GONE
            }

            if (item.type == ConversationType.GROUP.name) {
                val colorNameGroup =
                    ContextCompat.getColor(holder.itemView.context, vn.gmi.workzen.R.color.orange)
                tvName.setTextColor(colorNameGroup)
            }

            val firstText =
                if (item.lastMessageSenderId != currentUserId) "${item.lastMessageSenderName}: " else ""

            val msgContent = when(item.lastMessageType){
                MessageType.VIDEO.name -> "Đã gửi một video"
                MessageType.IMAGE.name -> "Đã gửi một ảnh"
                MessageType.FILE.name -> "Đã gửi một file"
                else -> (item.lastMessageContent ?: item.lastMessageSubContent)

            }
            tvLastChat.text =
                (firstText) +msgContent


            container.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val view = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConversationViewHolder(view.root)
    }


    override fun getItemCount(): Int = list.size
}