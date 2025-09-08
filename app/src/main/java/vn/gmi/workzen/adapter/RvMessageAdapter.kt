package vn.gmi.workzen.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.widget.PopupWindowCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stfalcon.imageviewer.StfalconImageViewer
import vn.gmi.workzen.BuildConfig
import vn.gmi.workzen.core.base.BaseAdapter

import vn.gmi.workzen.core.extensions.toHourMinute
import vn.gmi.workzen.core.extensions.toInstant
import vn.gmi.workzen.databinding.*
import vn.gmi.workzen.domain.entity.conversation.MessageEntity
import vn.gmi.workzen.domain.entity.enums.MessageType
import vn.gmi.workzen.manager.SessionManager
import vn.gmi.workzen.ui.chat.message.VideoPlayerActivity
import java.time.Duration
import vn.gmi.workzen.R
import vn.gmi.workzen.core.extensions.asToList

class RvMessageAdapter(private val onClick: MessageListener) : BaseAdapter<MessageEntity>() {

    private val currentUserId = SessionManager.profileState.value?.id
    private var currentSender: MessageEntity? = null
    private var nextSender: MessageEntity? = null

    class TextViewHolder(val binding: ItemMessageBaseBinding) : ItemViewHolder(binding.root)
    class ImageViewHolder(val binding: ItemMsgImageBinding) : ItemViewHolder(binding.root)
    class FileViewHolder(val binding: ItemMsgFileBinding) : ItemViewHolder(binding.root)
    class EmojiViewHolder(val binding: ItemMgsEmojiBinding) : ItemViewHolder(binding.root)
    class SystemMessageViewHolder(val binding: ItemSystemMessageBinding) : ItemViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            MessageTypeView.TEXT.code -> TextViewHolder(ItemMessageBaseBinding.inflate(inflater, parent, false))
            MessageTypeView.IMAGE.code, MessageTypeView.VIDEO.code -> ImageViewHolder(ItemMsgImageBinding.inflate(inflater, parent, false))
            MessageTypeView.EMOJI.code -> EmojiViewHolder(ItemMgsEmojiBinding.inflate(inflater, parent, false))
            MessageTypeView.SYSTEM.code -> SystemMessageViewHolder(ItemSystemMessageBinding.inflate(inflater, parent, false))
            MessageTypeView.FILE.code -> FileViewHolder(ItemMsgFileBinding.inflate(inflater, parent, false))
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
            is FileViewHolder -> handleFileMessage(holder, item)
            is SystemMessageViewHolder -> holder.binding.tvContent.text = item.subContent
        }
    }

    private fun handleFileMessage(holder: FileViewHolder, message: MessageEntity) {
        with(holder.binding) {
            if(!message.content.isNullOrEmpty()){
                tvContent.text = message.content
                tvContent.visibility = View.VISIBLE
            }

            if(message.senderId == currentUserId){
                container.gravity = Gravity.END
            }

            tvTime.visibility = if (shouldShowTime(message)) View.VISIBLE else View.GONE
            tvTime.text = message.createdAt?.toHourMinute()

            //Get file name
            tvName.text = message.files.asToList().firstOrNull()?.fileName

            // Check file type extension
            val extension = message.files.asToList()
                .firstOrNull()
                ?.fileName
                ?.substringAfterLast('.', "")
                ?.lowercase() ?: ""

            val iconUrl = when (extension) {
                "pdf" -> "https://img.icons8.com/color/96/pdf.png"
                "doc", "docx" -> "https://img.icons8.com/color/96/word.png"
                "xls", "xlsx" -> "https://img.icons8.com/color/96/excel.png"
                "ppt", "pptx" -> "https://img.icons8.com/color/96/powerpoint.png"
                "txt" -> "https://img.icons8.com/color/96/notepad.png"
                "jpg", "jpeg", "png", "gif", "bmp", "webp" -> "https://img.icons8.com/color/96/image.png"
                "zip", "rar", "7z" -> "https://img.icons8.com/color/96/zip.png"
                else -> "https://img.icons8.com/color/96/file.png"
            }

            Glide.with(holder.itemView.context)
                .load(iconUrl)
                .into(image)

            holder.itemView.setOnLongClickListener {view->
                showOptionMessage(holder.itemView.context, message, view)
                true
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleImageMessage(holder: ImageViewHolder, message: MessageEntity) {
        with(holder.binding) {
            val url = message.files.asToList().firstOrNull()?.filePath
            var fullUrl = if(message.isSent == true){
                "${BuildConfig.API_BASE_URL}$url"
            }else{
                url
            }
            Glide.with(holder.itemView.context).load(fullUrl).thumbnail(0.1f).into(image)

            if(message.files.asToList().size > 1){
                tvSizeFile.visibility = View.VISIBLE
                tvSizeFile.text = "+${message.files.asToList().size - 1} ảnh"
            }else{
                tvSizeFile.visibility = View.GONE
            }

            tvTime.text = if (message.isSent == true) message.createdAt?.toHourMinute() else "Đang gửi"
            container.gravity = if (message.senderId == currentUserId) Gravity.END else Gravity.START
            imgAvatar.visibility = if (message.senderId == currentUserId) View.GONE else View.VISIBLE

            if (message.senderId != nextSender?.senderId && message.senderId != currentUserId) {
                Glide.with(holder.itemView.context).load(message.senderAvatar).into(imgAvatar)
            }

            if (message.messageType == MessageType.IMAGE.name) {
                image.setOnClickListener {
                    val paths = message.files.map { it.filePath }
                    StfalconImageViewer.Builder(holder.itemView.context,paths) { view, imageUrl ->
                        var urlImg = if(message.isSent == true){
                            "${BuildConfig.API_BASE_URL}$imageUrl"
                        }else{
                            imageUrl
                        }
                        Glide.with(holder.itemView.context).load(urlImg).into(view)
                    }.show()
                }
            } else if (message.messageType == MessageType.VIDEO.name) {
                icPlayVideo.visibility = View.VISIBLE
                image.setOnClickListener {
                    val intent = Intent(holder.itemView.context, VideoPlayerActivity::class.java)
                    intent.putExtra("video_url", fullUrl)
                    holder.itemView.context.startActivity(intent)
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


    private fun showOptionMessage(context: Context, message: MessageEntity, anchorView: View) {
        val location = IntArray(2)
        anchorView.getLocationOnScreen(location) // [x, y] của view trên màn hình

        val x = location[0]
        val y = location[1]

        val popupView = LayoutInflater.from(context).inflate(R.layout.message_options, null)
        val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.elevation = 10f
        // Hiện popup ngay trên bubble
        popupView.measure(
            View.MeasureSpec.UNSPECIFIED,
            View.MeasureSpec.UNSPECIFIED
        )
        val popupHeight = popupView.measuredHeight

        popupWindow.showAtLocation(
            anchorView,
            Gravity.NO_GRAVITY,
            x + anchorView.width / 2 - popupView.measuredWidth / 2,
            y - popupHeight
        )

        popupView.findViewById<View>(R.id.ll_download).setOnClickListener {
            onClick.onDownload(message)
            popupWindow.dismiss()
        }

    }

    interface MessageListener {
        fun onDownload(message: MessageEntity)
    }
}

enum class MessageTypeView(val code: Int) {
    TEXT(0), IMAGE(1), FILE(2), EMOJI(3), SYSTEM(4), VIDEO(5), UNKNOWN(-1)
}
