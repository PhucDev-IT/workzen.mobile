package vn.gmi.workzen.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.gmi.workzen.core.base.BaseAdapter
import vn.gmi.workzen.databinding.ItemChatBinding
import vn.gmi.workzen.domain.entity.conversation.ConversationEntity

class RvConversationAdapter(private val onClick: (ConversationEntity) -> Unit) : BaseAdapter<ConversationEntity>(){


    @SuppressLint("NotifyDataSetChanged")
    fun resetAndAddAll(list: List<ConversationEntity>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }


    class ConversationViewHolder(itemView: View) :ItemViewHolder(itemView){
        val binding = ItemChatBinding.bind(itemView)
    }

    override fun bindView(
        holder: ItemViewHolder,
        item: ConversationEntity
    ) {
       val view = holder as ConversationViewHolder
        with(view.binding){
            tvName.text = item.conversationName
            Glide.with(holder.itemView.context).load(item.avatarUrl).into(imgAvatar)
            tvLastChat.text = item.lastMessageContent?: item.lastMessageSubContent

            if((item.unreadCount ?: 0) > 0){
                tvCount.text = item.unreadCount.toString()
                llUnreadMessage.visibility = View.VISIBLE
            }else{
                llUnreadMessage.visibility = View.GONE
            }

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

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
      return bindView(holder, list[position])
    }

    override fun getItemCount(): Int  = list.size
}