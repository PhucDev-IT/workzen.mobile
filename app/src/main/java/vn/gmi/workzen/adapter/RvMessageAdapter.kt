package vn.gmi.workzen.adapter

import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stfalcon.imageviewer.StfalconImageViewer
import vn.gmi.workzen.core.base.BaseAdapter
import vn.gmi.workzen.core.extensions.asList
import vn.gmi.workzen.core.extensions.toHourMinute
import vn.gmi.workzen.core.extensions.toInstant
import vn.gmi.workzen.databinding.*
import vn.gmi.workzen.domain.entity.conversation.MessageEntity
import vn.gmi.workzen.domain.entity.enums.MessageType
import vn.gmi.workzen.manager.SessionManager
import vn.gmi.workzen.ui.chat.message.VideoPlayerActivity
import java.time.Duration
import vn.gmi.workzen.R
class RvMessageAdapter : BaseAdapter<MessageEntity>() {

    private val currentUserId = SessionManager.profileState.value?.id
    private var currentSender: MessageEntity? = null
    private var nextSender: MessageEntity? = null

    class TextViewHolder(val binding: ItemMessageBaseBinding) : ItemViewHolder(binding.root)
    class ImageViewHolder(val binding: ItemMsgImageBinding) : ItemViewHolder(binding.root)
    class EmojiViewHolder(val binding: ItemMgsEmojiBinding) : ItemViewHolder(binding.root)
    class SystemMessageViewHolder(val binding: ItemSystemMessageBinding) : ItemViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            MessageTypeView.TEXT.code -> TextViewHolder(ItemMessageBaseBinding.inflate(inflater, parent, false))
            MessageTypeView.IMAGE.code, MessageTypeView.VIDEO.code -> ImageViewHolder(ItemMsgImageBinding.inflate(inflater, parent, false))
            MessageTypeView.EMOJI.code -> EmojiViewHolder(ItemMgsEmojiBinding.inflate(inflater, parent, false))
            MessageTypeView.SYSTEM.code -> SystemMessageViewHolder(ItemSystemMessageBinding.inflate(inflater, parent, false))
            else -> throw IllegalArgumentException("Invalid viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        currentSender = list[position]
        nextSender = list.getOrNull(position + 1)
        bindView(holder, list[position])
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        val message = list[position]
        return when {
            message.subContent != null -> MessageTypeView.SYSTEM.code
            message.messageType == MessageType.TEXT.name -> MessageTypeView.TEXT.code
            message.messageType == MessageType.IMAGE.name -> MessageTypeView.IMAGE.code
            message.messageType == MessageType.VIDEO.name -> MessageTypeView.VIDEO.code
            message.messageType == MessageType.FILE.name -> MessageTypeView.FILE.code
            message.messageType == MessageType.EMOJI.name -> MessageTypeView.EMOJI.code
            else -> MessageTypeView.TEXT.code
        }
    }

    override fun bindView(holder: ItemViewHolder, item: MessageEntity) {
        when (holder) {
            is TextViewHolder -> handleTextMessage(holder, item)
            is ImageViewHolder -> handleImageMessage(holder, item)
            is EmojiViewHolder -> handleEmojiMessage(holder, item)
            is SystemMessageViewHolder -> holder.binding.tvContent.text = item.subContent
        }
    }

    private fun handleImageMessage(holder: ImageViewHolder, message: MessageEntity) {
        with(holder.binding) {
            message.fileUrl.asList().forEachIndexed { index, url ->
                when (index) {
                    0 -> Glide.with(holder.itemView.context).load(url).thumbnail(0.1f).into(image)
                    1 -> Glide.with(holder.itemView.context).load(url).thumbnail(0.1f).into(image2)
                    2 -> Glide.with(holder.itemView.context).load(url).thumbnail(0.1f).into(image3)
                }
            }

            tvTime.text = if (message.isSent == true) message.createdAt?.toHourMinute() else "Đang gửi"
            container.gravity = if (message.senderId == currentUserId) Gravity.END else Gravity.START
            imgAvatar.visibility = if (message.senderId == currentUserId) View.GONE else View.VISIBLE

            if (message.senderId != nextSender?.senderId && message.senderId != currentUserId) {
                Glide.with(holder.itemView.context).load(message.senderAvatar).into(imgAvatar)
            }

            if (message.messageType == MessageType.IMAGE.name) {
                image.setOnClickListener {
                    StfalconImageViewer.Builder(holder.itemView.context, message.fileUrl) { view, imageUrl ->
                        Glide.with(holder.itemView.context).load(imageUrl).into(view)
                    }.show()
                }
            } else if (message.messageType == MessageType.VIDEO.name) {
                image.setOnClickListener {
                    message.fileUrl.firstOrNull()?.let {
                        val intent = Intent(holder.itemView.context, VideoPlayerActivity::class.java)
                        intent.putExtra("video_url", it)
                        holder.itemView.context.startActivity(intent)
                    }
                }
            }
        }
    }

    private fun handleTextMessage(holder: TextViewHolder, message: MessageEntity) {
        with(holder.binding) {
            tvContent.text = message.content
            tvTime.visibility = if (shouldShowTime(message)) View.VISIBLE else View.GONE
            tvTime.text = if (message.isSent == true) message.createdAt?.toHourMinute() else "Đang gửi"

            container.gravity = if (message.senderId == currentUserId) Gravity.END else Gravity.START
            imgAvatar.visibility = if (message.senderId == currentUserId) View.GONE else View.VISIBLE

            if (message.senderId != nextSender?.senderId && message.senderId != currentUserId) {
                Glide.with(holder.itemView.context).load(message.senderAvatar).into(imgAvatar)
            }
        }
    }

    private fun handleEmojiMessage(holder: EmojiViewHolder, message: MessageEntity) {
        with(holder.binding) {
            tvContent.text = message.content
            tvTime.visibility = if (shouldShowTime(message)) View.VISIBLE else View.GONE
            tvTime.text = message.createdAt?.toHourMinute()

            container.gravity = if (message.senderId == currentUserId) Gravity.END else Gravity.START
            imgAvatar.visibility = if (message.senderId == currentUserId) View.GONE else View.VISIBLE

            if (message.senderId != nextSender?.senderId && message.senderId != currentUserId) {
                Glide.with(holder.itemView.context).load(message.senderAvatar).into(imgAvatar)
            }
        }
    }

    private fun shouldShowTime(message: MessageEntity): Boolean {
        val msgTime = message.createdAt?.toInstant()
        val nextMsgTime = nextSender?.createdAt?.toInstant()
        return when {
            message.senderId != nextSender?.senderId -> true
            msgTime != null && nextMsgTime != null -> Duration.between(msgTime, nextMsgTime).toMinutes() > 15
            else -> false
        }
    }
}

enum class MessageTypeView(val code: Int) {
    TEXT(0), IMAGE(1), FILE(2), EMOJI(3), SYSTEM(4), VIDEO(5), UNKNOWN(-1)
}
