package vn.gmi.workzen.ui.chat.message

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.MessageTypeView
import vn.gmi.workzen.adapter.RvMessageAdapter
import vn.gmi.workzen.adapter.RvMgsPreviewFileAdapter
import vn.gmi.workzen.core.base.BaseActivity
import vn.gmi.workzen.core.constants.AppToast
import vn.gmi.workzen.core.extensions.dpToPx
import vn.gmi.workzen.data.models.request.conversation.ChatMessage
import vn.gmi.workzen.data.models.response.conversation.MessageResponseModel
import vn.gmi.workzen.databinding.ActivityMessengerBinding
import vn.gmi.workzen.domain.entity.conversation.ConversationEntity
import vn.gmi.workzen.domain.entity.conversation.ConversationSerializable
import vn.gmi.workzen.domain.entity.conversation.MessageEntity
import vn.gmi.workzen.domain.entity.enums.ConversationType
import vn.gmi.workzen.domain.entity.enums.MessageType
import vn.gmi.workzen.manager.ChatWebsocketManager
import vn.gmi.workzen.manager.SessionManager
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.ui.chat.details.MessageDetailsActivity
import vn.gmi.workzen.utils.IntentData
import vn.gmi.workzen.utils.Utils
import java.sql.Timestamp
import java.time.Instant
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class MessengerActivity : BaseActivity<MessageContract.View, MessageContract.Presenter>(),
    MessageContract.View {

    private lateinit var binding: ActivityMessengerBinding

    @Inject
    lateinit var messagePresenter: MessageContract.Presenter
    private lateinit var adapter: RvMessageAdapter
    private lateinit var adapterPreview: RvMgsPreviewFileAdapter
    private lateinit var conversationID: String
    private var mediaPlayer: MediaPlayer? = null
    private var conversation: ConversationSerializable? = null

    override val layoutView: View
        get() {
            binding = ActivityMessengerBinding.inflate(layoutInflater)
            return binding.root
        }

    override fun initPresenter(): MessageContract.Presenter {
        return messagePresenter
    }


    override fun initViews() {
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // set padding cho các phần cần thiết
            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                0
            ) // KHÔNG set bottom cho root

            // padding bottom cho phần input để tránh đè navigation hoặc bàn phím
            val extraPadding = dpToPx(5) // tùy chỉnh cao thêm bao nhiêu
            findViewById<View>(R.id.ll_bottom)
                .updatePadding(bottom = maxOf(systemBars.bottom, ime.bottom))

            insets
        }


        adapter = RvMessageAdapter()
        binding.rvChat.adapter = adapter
        binding.rvChat.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        adapterPreview = RvMgsPreviewFileAdapter(object : RvMgsPreviewFileAdapter.OnDataChange {
            override fun onRemoved(item: ChatMessage.FileMsgInfo) {
                if (adapterPreview.getSize() <= 0) {
                    binding.llViewMediaPreview.visibility = View.GONE
                    if(binding.edtMessage.text.toString().isEmpty()){
                        binding.icSend.visibility = View.GONE
                        binding.tvQuickEmoji.visibility = View.VISIBLE
                    }
                }

            }
        })
        binding.rvMedia.adapter = adapterPreview
        binding.rvMedia.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        binding.rvChat.adapter?.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)

                val layoutManager = binding.rvChat.layoutManager as LinearLayoutManager
                val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()

                if (lastVisiblePosition == positionStart - 1 || lastVisiblePosition == RecyclerView.NO_POSITION) {
                    binding.rvChat.post {
                        binding.rvChat.scrollToPosition(adapter.itemCount - 1)
                    }
                }
            }

        })
        setup()

        if (intent.hasExtra(IntentData.KEY_DATA_FROM_FCM)) {
            try {
                val json = intent.getStringExtra(IntentData.KEY_DATA_FROM_FCM)
                val notificationModel = ApiService.instance.GSON.fromJson<MessageResponseModel>(
                    json,
                    MessageResponseModel::class.java
                )
                if (notificationModel.conversationId == null) {
                    finish()
                    return
                }
                this.conversationID = notificationModel.conversationId!!
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return
        }
        val conversationId = intent.getStringExtra(IntentData.KEY_CONVERSATION_ID)
        if (conversationId == null) {
            AppToast.showError(this, "Có lỗi xảy ra")
            finish()
        }
        this.conversationID = conversationId!!
        presenter.getInfoConversation(conversationId.toString())
        presenter.requestLoadMessages(conversationId.toString())



        mediaPlayer = MediaPlayer.create(this, R.raw.sound_send_msg)

        Glide.with(this)
            .load("https://i.pinimg.com/736x/28/23/b2/2823b20884245fe8088e30b57a643d75.jpg")
            .into(binding.bgImageView)


    }


    override fun setListener() {
        binding.icSend.setOnClickListener(this)
        binding.imgAttachFile.setOnClickListener(this)
        binding.imgMic.setOnClickListener(this)
        binding.tvQuickEmoji.setOnClickListener(this)
        binding.icBack.setOnClickListener(this)
        binding.llViewInfo.setOnClickListener(this)
        binding.bottomAction.llClose.setOnClickListener(this)
        binding.bottomAction.btnPhoto.setOnClickListener(this)

    }

    override fun onSingleClick(v: View?) {
        when (v) {
            binding.icSend -> {
                sendTextMessage()
            }

            binding.icBack -> finish()
            binding.imgAttachFile -> {
                showSlideBottomAction()
            }

            binding.imgMic -> {}
            binding.tvQuickEmoji -> {
                sendEmojiMessage(binding.tvQuickEmoji.text.toString())
            }

            binding.bottomAction.llClose -> {
                hideSlideView()
            }

            binding.llViewInfo -> {
                val intent = Intent(this, MessageDetailsActivity::class.java)
                intent.putExtra(IntentData.KEY_CONVERSATION_ID, conversation)
                startActivity(intent)
            }

            binding.bottomAction.btnPhoto -> {
                openPicker()
            }
        }
    }

    override fun onError(message: String) {

    }

    override fun onLoadFirstData(items: List<MessageEntity>) {
        adapter.addAll(items)
        if (items.size > 3) {
            binding.llInfo.visibility = View.GONE
        }
    }

    override fun onLoadConversationSuccess(conversation: ConversationEntity?) {
        this.conversation = conversation?.mapToEntity()
        binding.tvHeaderName.text = conversation?.conversationName
        binding.tvNameNone.text = conversation?.conversationName

        Glide.with(this).load(conversation?.avatarUrl).into(binding.imgAvatar)
        Glide.with(this).load(conversation?.avatarUrl).into(binding.imgAvtNone)

        if (conversation?.type == ConversationType.GROUP.name) {
            binding.tvContentNone.text = "Bạn là thành viên của nhóm này"
        } else {
            binding.tvContentNone.text = "Hãy cảnh giác với những người mà bạn không quen biết"

        }
    }

    private fun sendTextMessage() {
        val message = binding.edtMessage.text.toString().trim()
        if (message.isNotEmpty()) {
            playSound()
            ChatWebsocketManager.sendMessage(createMessage(message))
            binding.edtMessage.text = null
        }

        if(adapterPreview.getSize() >0){
            presenter.sendMessage(createMsgWithFile())
            adapterPreview.clear()
            binding.icSend.visibility = View.GONE
            binding.tvQuickEmoji.visibility = View.VISIBLE
        }
    }

    private fun sendEmojiMessage(emoji: String) {
        playSound()
        ChatWebsocketManager.sendMessage(createMessage(emoji))
    }

    private fun playSound() {
        if (mediaPlayer != null && mediaPlayer?.isPlaying == false) {
            mediaPlayer?.start()
        }
    }

    private fun releaseSound() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun setup() {
        binding.edtMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (s.isNullOrEmpty()) {
                    binding.icSend.visibility = View.GONE
                    binding.tvQuickEmoji.visibility = View.VISIBLE
                } else {
                    binding.tvQuickEmoji.visibility = View.GONE
                    binding.icSend.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }


    override fun onDestroy() {
        releaseSound()
        super.onDestroy()
    }


    private fun createMsgWithFile(): List<ChatMessage> {
        val messages = mutableListOf<ChatMessage>()
        adapterPreview.list.forEach {
            val extension = it.fileName?.substringAfterLast('.', "")?.lowercase() ?: ""
            val msg = ChatMessage().apply {
                content = ""
                this.conversationId = conversationID
                this.senderId = SessionManager.profileState.value?.id
                this.messageType = handleExtensionFile(extension)
                this.createdAt = Instant.now().toString()
                this.file = ChatMessage.FileMsgInfo().apply {
                    file = Utils.uriToBase64(this@MessengerActivity,it.file!!.toUri())
                    fileName = it.fileName
                    fileType = it.fileType
                }
            }

            messages.add(msg)
        }
        return messages
    }

    private fun createMessage(message: String): ChatMessage {
        return ChatMessage().apply {
            content = message
            this.conversationId = conversationID
            this.senderId = SessionManager.profileState.value?.id
            this.messageType = MessageType.TEXT
            this.createdAt = Instant.now().toString()
        }
    }

    private fun showSlideBottomAction() {
        binding.llBottomInput.visibility = View.GONE
        val slideView = binding.bottomAction.root
        slideView.visibility = View.VISIBLE
        slideView.translationX = -slideView.width.toFloat() // Bắt đầu từ bên trái màn hình

        slideView.animate()
            .translationX(0f) // Trượt vào vị trí ban đầu
            .setDuration(300)
            .setInterpolator(DecelerateInterpolator())
            .start()
    }

    private fun hideSlideView() {
        val slideView = binding.bottomAction.root

        slideView.animate()
            .translationX(-slideView.width.toFloat())
            .setDuration(300)
            .setInterpolator(AccelerateInterpolator())
            .withEndAction {
                slideView.visibility = View.GONE
                binding.llBottomInput.visibility = View.VISIBLE
            }
            .start()
    }


    //============================== HANDLE ACTION =====================================

    private fun displayPreview(uris: List<Uri>) {
        val list = mutableListOf<ChatMessage.FileMsgInfo>()
        uris.forEach {
            val (name, type, size) = getFileInfo(this, it)
            list.add(ChatMessage.FileMsgInfo().apply {
                id = UUID.randomUUID().toString()
                file = it.toString()
                fileName = name
                fileType = type
            })

            Log.d("TAG", "displayPreview: $name - type: $type - size: $size")
        }
        binding.llViewMediaPreview.visibility = View.VISIBLE
        adapterPreview.addAll(list)

    }

    fun openPicker() {
        pickMultipleMedia.launch(arrayOf("image/*", "video/*"))
        hideSlideView()
    }

    val pickMultipleMedia = registerForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) { uris ->
        // uris: List<Uri>
        displayPreview(uris)
        if(!binding.icSend.isVisible){
            binding.icSend.visibility = View.VISIBLE
            binding.tvQuickEmoji.visibility = View.GONE
        }
    }


    fun getFileInfo(context: Context, uri: Uri): Triple<String?, String?, Long?> {
        var name: String? = null
        var type: String? = null
        var size: Long? = null

        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)

            if (cursor.moveToFirst()) {
                name = if (nameIndex != -1) cursor.getString(nameIndex) else null
                size = if (sizeIndex != -1) cursor.getLong(sizeIndex) else null
            }
        }

        type = context.contentResolver.getType(uri)

        return Triple(name, type, size)
    }

    private fun handleExtensionFile(extension:String): MessageType{
        Log.d("Phuc","Type = $extension")
        return when (extension) {
            in listOf("jpg", "jpeg", "png", "gif", "webp") -> MessageType.IMAGE
            in listOf("mp4", "mov", "avi", "mkv") -> MessageType.VIDEO
            in listOf("pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt") -> MessageType.FILE
            else -> MessageType.UNKNOWN
        }
    }
}