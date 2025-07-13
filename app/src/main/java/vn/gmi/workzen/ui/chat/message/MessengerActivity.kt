package vn.gmi.workzen.ui.chat.message

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.RvMessageAdapter
import vn.gmi.workzen.core.base.BaseActivity
import vn.gmi.workzen.core.constants.AppToast
import vn.gmi.workzen.core.extensions.dpToPx
import vn.gmi.workzen.data.models.request.conversation.ChatMessage
import vn.gmi.workzen.data.models.response.conversation.MessageResponseModel
import vn.gmi.workzen.databinding.ActivityMessengerBinding
import vn.gmi.workzen.domain.entity.conversation.ConversationEntity
import vn.gmi.workzen.domain.entity.conversation.MessageEntity
import vn.gmi.workzen.domain.entity.enums.ConversationType
import vn.gmi.workzen.domain.entity.enums.MessageType
import vn.gmi.workzen.manager.ChatWebsocketManager
import vn.gmi.workzen.manager.SessionManager
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.utils.IntentData
import java.sql.Timestamp
import java.time.Instant
import javax.inject.Inject

@AndroidEntryPoint
class MessengerActivity : BaseActivity<MessageContract.View, MessageContract.Presenter>(),
    MessageContract.View {

    private lateinit var binding: ActivityMessengerBinding

    @Inject
    lateinit var messagePresenter: MessageContract.Presenter
    private lateinit var adapter: RvMessageAdapter
    private lateinit var conversationID: String
    private var mediaPlayer: MediaPlayer? = null


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
            view.setPadding(systemBars.left, systemBars.top, systemBars.right,0) // KHÔNG set bottom cho root

            // padding bottom cho phần input để tránh đè navigation hoặc bàn phím
            val extraPadding = dpToPx(12) // tùy chỉnh cao thêm bao nhiêu
            findViewById<View>(R.id.ll_bottom)
                .updatePadding(bottom = maxOf(systemBars.bottom, ime.bottom) + extraPadding)

            insets
        }

        adapter = RvMessageAdapter()
        binding.rvChat.adapter = adapter
//        binding.rvChat.itemAnimator = null
//        binding.rvChat.setHasFixedSize(true)
//        binding.rvChat.setItemViewCacheSize(20)
        binding.rvChat.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.rvChat.adapter?.registerAdapterDataObserver(object  : RecyclerView.AdapterDataObserver(){
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

        if(intent.hasExtra(IntentData.KEY_DATA_FROM_FCM)){
            try{
                val json = intent.getStringExtra(IntentData.KEY_DATA_FROM_FCM)
                val notificationModel = ApiService.instance.GSON.fromJson<MessageResponseModel>(json,
                    MessageResponseModel::class.java)
                if(notificationModel.conversationId == null){
                    finish()
                    return
                }
                this.conversationID = notificationModel.conversationId!!
            }catch (e: Exception){
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

    }


    override fun setListener() {
        binding.icSend.setOnClickListener(this)
        binding.imgAttachFile.setOnClickListener(this)
        binding.imgMic.setOnClickListener(this)
        binding.tvQuickEmoji.setOnClickListener(this)
        binding.icBack.setOnClickListener(this)

    }

    override fun onSingleClick(v: View?) {
        when (v) {
            binding.icSend -> {
                sendTextMessage()
            }
            binding.icBack-> finish()
            binding.imgAttachFile -> {}
            binding.imgMic -> {}
            binding.tvQuickEmoji -> {
                sendEmojiMessage(binding.tvQuickEmoji.text.toString())
            }
        }
    }

    override fun onError(message: String) {

    }

    override fun onLoadFirstData(items: List<MessageEntity>) {
        adapter.addAll(items)
        Log.d("Phuc phuc","Size: ${items.size}")
        if(items.size > 3){
            binding.llInfo.visibility = View.GONE
        }
    }

    override fun onLoadConversationSuccess(conversation: ConversationEntity?) {
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
        val message = binding.edtMessage.text.toString()
        if (message.isNotEmpty()) {
            playSound()

            ChatWebsocketManager.sendMessage(createMessage(message))
            binding.edtMessage.text = null
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


    private fun createMessage(message: String): ChatMessage {
        val msg = ChatMessage().apply {
            content = message
            this.conversationId = conversationID
            this.senderId = SessionManager.profileState.value?.id
            this.messageType = MessageType.TEXT
            this.createdAt = Instant.now().toString()
        }
        return msg
    }
}