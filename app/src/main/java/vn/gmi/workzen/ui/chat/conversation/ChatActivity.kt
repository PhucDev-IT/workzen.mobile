package vn.gmi.workzen.ui.chat.conversation

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
import androidx.core.graphics.toColorInt

@AndroidEntryPoint
class ChatActivity : BaseActivity<ConversationContract.View, ConversationContract.Presenter>(), ConversationContract.View {

    @Inject lateinit var conversationPresenter: ConversationContract.Presenter
    private lateinit var adapter: RvConversationAdapter

    private lateinit var binding: ActivityChatBinding
    var tabs = listOf<TextView>()



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

        tabs = listOf(binding.tvAll,binding.tvGroup, binding.tvUnread)
        setListenerTab(tabs[0])
    }

    override fun setListener() {
        tabs.forEachIndexed { index,tab ->
            tab.setOnClickListener {
                setListenerTab(tab)
                if(index == 0){
                    presenter.requestLoadConversations()
                }else if(index == 1){
                    presenter.requestGetConversationsTypeGroup()
                }else{
                    presenter.requestGetConversationsUnRead()
                }
            }
        }
    }

    private fun setListenerTab(tv: TextView) {
        val selectedColor = "#F0F0F0".toColorInt()
        val transparent = ContextCompat.getColor(this, android.R.color.transparent)

        tabs.forEach { tab ->
            val wasSelected = tab.isSelected
            val nowSelected = (tab == tv)

            if (wasSelected != nowSelected) {
                val from = if (wasSelected) selectedColor else transparent
                val to = if (nowSelected) selectedColor else transparent
                animateBackground(tab, from, to)
            }

            tab.isSelected = nowSelected
        }
    }


    override fun onSingleClick(v: View?) {

    }

    override fun onError(message: String) {

    }

    override fun onShowConversations(conversations: List<ConversationEntity>) {
        adapter.resetAndAddAll(conversations)
    }


    fun animateBackground(view: TextView, fromColor: Int, toColor: Int) {
        val background = view.background
        if (background is GradientDrawable) {
            val colorAnimation = ValueAnimator.ofArgb(fromColor, toColor)
            colorAnimation.duration = 300
            colorAnimation.addUpdateListener { animator ->
                background.setColor(animator.animatedValue as Int)
            }
            colorAnimation.start()
        }
    }


    override fun onLoading() {
        Log.d("Phuc", "onLoading loading")
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onHideLoadingChat() {
        Log.d("Phuc", "hidden loading")
        binding.progressBar.visibility = View.GONE
    }
}