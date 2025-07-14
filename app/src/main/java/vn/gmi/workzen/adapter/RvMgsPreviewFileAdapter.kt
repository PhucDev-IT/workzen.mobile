package vn.gmi.workzen.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import vn.gmi.workzen.core.base.BaseAdapter
import vn.gmi.workzen.data.models.request.conversation.ChatMessage
import vn.gmi.workzen.databinding.ItemMsgPreviewImgBinding
import vn.gmi.workzen.domain.entity.enums.MessageType

class RvMgsPreviewFileAdapter(private val listener:OnDataChange?) : BaseAdapter<ChatMessage.FileMsgInfo>() {


    class ImageViewHolder(val binding : ItemMsgPreviewImgBinding): ItemViewHolder(binding.root)

    override fun bindView(
        holder: ItemViewHolder,
        item: ChatMessage.FileMsgInfo
    ) {
        handleImageView(holder, item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            MessageTypeView.IMAGE.code,  MessageTypeView.VIDEO.code -> ImageViewHolder(
                ItemMsgPreviewImgBinding.inflate(inflater, parent, false)
            )

            else -> throw IllegalArgumentException("Invalid viewType: $viewType")
        }

    }

    override fun getItemViewType(position: Int): Int {
        val item = list[position]
        val extension = item.fileName?.substringAfterLast('.', "")?.lowercase() ?: ""

        return when {
            extension in listOf("jpg", "jpeg", "png", "gif", "webp") -> MessageTypeView.IMAGE.code
            extension in listOf("mp4", "mov", "avi", "mkv") -> MessageTypeView.VIDEO.code
            extension in listOf("pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt") -> MessageTypeView.FILE.code
            else -> MessageTypeView.UNKNOWN.code
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun handleImageView(holder: ItemViewHolder,
                                item: ChatMessage.FileMsgInfo){
        val view = holder as ImageViewHolder
       with(view.binding) {
           Glide.with(view.itemView.context).load(item.file).into(img)

           icRemove.setOnClickListener {
               list.remove(item)
               notifyDataSetChanged()
               listener?.onRemoved(item)
           }
       }
    }



    interface OnDataChange {
        fun onRemoved(item: ChatMessage.FileMsgInfo)
    }
}