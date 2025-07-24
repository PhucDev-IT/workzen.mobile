package vn.gmi.workzen.ui.chat.message

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import vn.gmi.workzen.R
import vn.gmi.workzen.databinding.ActivityVideoPlayerBinding
import androidx.core.net.toUri

class VideoPlayerActivity : AppCompatActivity() {
    private var player: ExoPlayer? = null

    private lateinit var binding: ActivityVideoPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val videoUrl = intent.getStringExtra("video_url") ?: return
        Log.d("VideoPlayerActivity", "Video URL: $videoUrl")
        player = ExoPlayer.Builder(this).build().also {
            binding.playerView.player = it
            val mediaItem = MediaItem.fromUri(videoUrl.toUri())
            it.setMediaItem(mediaItem)
            it.prepare()
            it.playWhenReady = true
        }
    }
    override fun onStop() {
        super.onStop()
        player?.release()
        player = null
    }
}