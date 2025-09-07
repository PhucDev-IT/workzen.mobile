package vn.gmi.workzen.ui.chat.details

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import vn.gmi.workzen.R
import vn.gmi.workzen.databinding.ActivityMessageDetailsBinding
import vn.gmi.workzen.domain.entity.conversation.ConversationEntity
import vn.gmi.workzen.domain.entity.conversation.ConversationSerializable
import vn.gmi.workzen.utils.IntentData

class MessageDetailsActivity : AppCompatActivity() {
    private lateinit var conversation: ConversationSerializable
    private lateinit var binding: ActivityMessageDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMessageDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val data = intent.getSerializableExtra(IntentData.KEY_CONVERSATION_ID) as ConversationSerializable?
        if(data == null){
            finish()
        }
        conversation = data!!

        Glide.with(this).load(conversation.avatarUrl).into(binding.imgAvatar)
        binding.tvName.text = conversation.conversationName

    }
}