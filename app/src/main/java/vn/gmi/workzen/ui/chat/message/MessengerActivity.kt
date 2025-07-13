package vn.gmi.workzen.ui.chat.message

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import vn.gmi.workzen.R
import vn.gmi.workzen.core.base.BaseActivity
import vn.gmi.workzen.core.constants.AppToast
import vn.gmi.workzen.databinding.ActivityMessengerBinding
import vn.gmi.workzen.domain.entity.conversation.MessageEntity
import vn.gmi.workzen.utils.IntentData
import javax.inject.Inject

@AndroidEntryPoint
class MessengerActivity : BaseActivity<MessageContract.View, MessageContract.Presenter>(), MessageContract.View {

    private lateinit var binding: ActivityMessengerBinding
    @Inject lateinit var messagePresenter: MessageContract.Presenter

    override val layoutView: View
        get(){
            binding = ActivityMessengerBinding.inflate(layoutInflater)
            return binding.root
        }

    override fun initPresenter(): MessageContract.Presenter {
      return messagePresenter
    }

    override fun initViews() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        val conversationId = intent.getStringExtra(IntentData.KEY_CONVERSATION_ID)
        if(conversationId == null){
            AppToast.showError(this,"Có lỗi xảy ra")
            finish()
        }
        presenter.requestLoadMessages(conversationId!!)

    }

    override fun setListener() {

    }

    override fun onSingleClick(v: View?) {

    }

    override fun onError(message: String) {

    }

    override fun onLoadFirstData(items: List<MessageEntity>) {

    }
}