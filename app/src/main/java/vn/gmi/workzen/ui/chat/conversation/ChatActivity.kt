package vn.gmi.workzen.ui.chat.conversation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.RvConversationAdapter
import vn.gmi.workzen.core.base.BaseActivity
import vn.gmi.workzen.databinding.ActivityChatBinding
import vn.gmi.workzen.domain.entity.conversation.ConversationEntity
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.services.socket.AppWebSocketListener
import vn.gmi.workzen.ui.chat.message.MessengerActivity
import vn.gmi.workzen.utils.IntentData
import javax.inject.Inject

@AndroidEntryPoint
class ChatActivity : BaseActivity<ConversationContract.View, ConversationContract.Presenter>(), ConversationContract.View {

    @Inject lateinit var conversationPresenter: ConversationContract.Presenter
    private lateinit var adapter: RvConversationAdapter

    private lateinit var binding: ActivityChatBinding
    var tabs = mutableListOf(
        Triple("All", 6, true),
        Triple("Group", 0, false),
        Triple("Chats", 0, false)
    )



    override val layoutView: View
        get() {
            binding = ActivityChatBinding.inflate(layoutInflater)
            return binding.root
        }

    override fun initPresenter(): ConversationContract.Presenter {
        return conversationPresenter
    }

    override fun initViews() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = RvConversationAdapter{ conversation ->
            val intent = Intent(this, MessengerActivity::class.java)
            intent.putExtra(IntentData.KEY_CONVERSATION_ID, conversation.conversationId)
            startActivity(intent)
        }
        binding.rvChats.adapter = adapter
        binding.rvChats.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        presenter.requestLoadConversations()


        ApiService.instance.connectWebSocket(websocketListener)
    }

    override fun setListener() {

    }

    override fun onSingleClick(v: View?) {

    }

    override fun onError(message: String) {

    }

    private val websocketListener = object : AppWebSocketListener{
        override fun onReceiveMessage(data: String) {
            Log.d("ChatActivity", "onReceiveMessage: $data")
        }
    }

    override fun onShowConversations(conversations: List<ConversationEntity>) {
        adapter.resetAndAddAll(conversations)
    }
}