package vn.gmi.workzen.adapter

import android.media.Image
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stfalcon.imageviewer.StfalconImageViewer
import io.realm.kotlin.types.RealmInstant
import vn.gmi.workzen.core.base.BaseAdapter
import vn.gmi.workzen.core.extensions.toHourMinute
import vn.gmi.workzen.core.extensions.toInstant
import vn.gmi.workzen.databinding.ItemMessageBaseBinding
import vn.gmi.workzen.databinding.ItemMgsEmojiBinding
import vn.gmi.workzen.databinding.ItemMsgImageBinding
import vn.gmi.workzen.databinding.ItemSystemMessageBinding
import vn.gmi.workzen.domain.entity.conversation.MessageEntity
import vn.gmi.workzen.manager.SessionManager
import vn.gmi.workzen.domain.entity.enums.MessageType
import java.time.Duration
import java.time.Instant

class RvMessageAdapter : BaseAdapter<MessageEntity>() {

    private val currentUserId = SessionManager.profileState.value?.id
    private var currentSender: MessageEntity? = null
    private var nextSender: MessageEntity? = null

    class TextViewHolder(val binding: ItemMessageBaseBinding) : ItemViewHolder(binding.root)
    class ImageViewHolder(val binding: ItemMsgImageBinding) : ItemViewHolder(binding.root)
//    class FileViewHolder(val binding: ItemMessageFileSentBinding) : ItemViewHolder(binding.root)
    class EmojiViewHolder(val binding: ItemMgsEmojiBinding) : ItemViewHolder(binding.root)
    class SystemMessageViewHolder(val binding: ItemSystemMessageBinding) : ItemViewHolder(binding.root)



    override fun bindView(
        holder: ItemViewHolder,
        item: MessageEntity
    ) {
        when (holder) {
            is TextViewHolder -> {handleTextMessage(holder, item) }

            is ImageViewHolder -> {
                handleImageMessage(holder,item)
            }

            is  EmojiViewHolder ->{handleEmojiMessage(holder,item)}
//            is ImageReceivedViewHolder -> {
//                // Load image from item.fileUrl
//            }
//            is FileSentViewHolder -> {
//                holder.binding.fileName.text = item.fileUrl?.substringAfterLast("/")
//            }
//            is FileReceivedViewHolder -> {
//                holder.binding.fileName.text = item.fileUrl?.substringAfterLast("/")
//            }
//            is EmojiSentViewHolder -> {
//                holder.binding.emojiText.text = item.content
//            }
//            is EmojiReceivedViewHolder -> {
//                holder.binding.emojiText.text = item.content
//            }
            is SystemMessageViewHolder -> {
                holder.binding.tvContent.text = item.subContent
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            MessageTypeView.TEXT.code -> TextViewHolder(
                ItemMessageBaseBinding.inflate(inflater, parent, false)
            )

            MessageTypeView.IMAGE.code -> ImageViewHolder(
                ItemMsgImageBinding.inflate(inflater, parent, false)
            )
//            MessageTypeView.IMAGE_RECEIVED.code -> ImageReceivedViewHolder(
//                ItemMessageImageReceivedBinding.inflate(inflater, parent, false)
//            )
//            MessageTypeView.FILE_SENT.code -> FileSentViewHolder(
//                ItemMessageFileSentBinding.inflate(inflater, parent, false)
//            )
//            MessageTypeView.FILE_RECEIVED.code -> FileReceivedViewHolder(
//                ItemMessageFileReceivedBinding.inflate(inflater, parent, false)
//            )
//            MessageTypeView.EMOJI_SENT.code -> EmojiSentViewHolder(
//                ItemMessageEmojiSentBinding.inflate(inflater, parent, false)
//            )
            MessageTypeView.EMOJI.code -> EmojiViewHolder(
                ItemMgsEmojiBinding.inflate(inflater, parent, false)
            )
            MessageTypeView.SYSTEM.code -> SystemMessageViewHolder(
                ItemSystemMessageBinding.inflate(inflater, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid viewType: $viewType")
        }
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        currentSender = list[position]
        nextSender = if(position < list.size - 1){
            list[position + 1]
        }else{
            null
        }
        bindView(holder,list[position])
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        val message = list[position]

        return when {
            message.subContent != null -> MessageTypeView.SYSTEM.code
            message.messageType == MessageType.TEXT.name -> MessageTypeView.TEXT.code
            message.messageType == MessageType.IMAGE.name -> MessageTypeView.IMAGE.code
            message.messageType == MessageType.FILE.name -> MessageTypeView.FILE.code
            message.messageType == MessageType.EMOJI.name -> MessageTypeView.EMOJI.code
            else -> MessageTypeView.TEXT.code
        }
    }


    private fun handleImageMessage(holder: ItemViewHolder, message: MessageEntity){
        val view = holder as ImageViewHolder
        with(view.binding){
            Glide.with(holder.itemView.context).load(message.fileUrl).into(image)

            tvTime.text = message.createdAt?.toHourMinute()

            if(message.senderId == currentUserId){
                container.gravity = Gravity.END
                imgAvatar.visibility = View.GONE
            }else{
                container.gravity = Gravity.START
                if(message.senderId != nextSender?.senderId){
                    Glide.with(holder.itemView.context).load(message.senderAvatar).into(imgAvatar)
                }

            }

            image.setOnClickListener {
                StfalconImageViewer.Builder<String>(holder.itemView.context, message.fileUrl) { view, imageUrl ->
                    Glide.with(holder.itemView.context).load(imageUrl).into(view)
                }.show()
            }
        }
    }

    private fun handleTextMessage(holder: ItemViewHolder, message: MessageEntity){
        val view = holder as TextViewHolder
        with(view.binding){
            tvContent.text = message.content


            val msgTime = message.createdAt?.toInstant()
            val nextMsgTime = nextSender?.createdAt?.toInstant()

            val shouldShowTime = when {
                message.senderId != nextSender?.senderId -> true
                msgTime != null && nextMsgTime != null -> {
                    Duration.between(msgTime, nextMsgTime).toMinutes() > 15
                }
                else -> false
            }

            if (shouldShowTime) {
                tvTime.visibility = View.VISIBLE
                tvTime.text = message.createdAt?.toHourMinute()
            }

            if(message.senderId == currentUserId){
                container.gravity = Gravity.END
                imgAvatar.visibility = View.GONE
            }else{
                container.gravity = Gravity.START
                if(message.senderId != nextSender?.senderId){
                    Glide.with(holder.itemView.context).load(message.senderAvatar).into(imgAvatar)
                }

            }
        }
    }

    private fun handleEmojiMessage(holder: ItemViewHolder, message: MessageEntity){
        val view = holder as TextViewHolder
        with(view.binding){
            tvContent.text = message.content


            val msgTime = message.createdAt?.toInstant()
            val nextMsgTime = nextSender?.createdAt?.toInstant()

            val shouldShowTime = when {
                message.senderId != nextSender?.senderId -> true
                msgTime != null && nextMsgTime != null -> {
                    Duration.between(msgTime, nextMsgTime).toMinutes() > 15
                }
                else -> false
            }

            if (shouldShowTime) {
                tvTime.visibility = View.VISIBLE
                tvTime.text = message.createdAt?.toHourMinute()
            }

            if(message.senderId == currentUserId){
                container.gravity = Gravity.END
                imgAvatar.visibility = View.GONE
            }else{
                container.gravity = Gravity.START
                if(message.senderId != nextSender?.senderId){
                    Glide.with(holder.itemView.context).load(message.senderAvatar).into(imgAvatar)
                }

            }
        }
    }
}

enum class MessageTypeView(val code: Int) {
    TEXT(0),
    IMAGE(1),
    FILE(2),
    EMOJI(3),
    SYSTEM(4), // subContent như "A đã thêm B vào nhóm"
    VIDEO(5),
    UNKNOWN(-1)
}
