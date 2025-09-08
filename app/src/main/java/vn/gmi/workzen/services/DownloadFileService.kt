package vn.gmi.workzen.services

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Environment
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vn.gmi.workzen.MyApplication
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL


class DownloadFileService : Service() {

    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): DownloadFileService{
            return this@DownloadFileService
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    @SuppressLint("ForegroundServiceType")
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val fileUrl = intent?.getStringExtra("fileUrl")
        val fileName = intent?.getStringExtra("fileName") ?: "unknown"

        // Hiển thị notification ban đầu
        startForeground(1, buildNotification("Đang tải $fileName", 0))

        // Thực hiện tải file trong coroutine / thread riêng
        fileUrl?.let {
            downloadFile(it, fileName)
        }

        // START_STICKY để service không bị hủy ngang chừng
        return START_STICKY
    }


    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun updateProgress(fileName: String, progress: Int) {
        val notification = buildNotification("Đang tải $fileName", progress)
        NotificationManagerCompat.from(this).notify(1, notification)
    }
    private fun buildNotification(content: String, progress: Int): Notification {
        return NotificationCompat.Builder(this, MyApplication.CHANNEL_NORMAL)
            .setContentTitle("Tải xuống")
            .setContentText(content)
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setProgress(100, progress, false)
            .setOngoing(true)
            .build()
    }
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun downloadFile(url: String, fileName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val urlConnection = URL(url).openConnection() as HttpURLConnection
                val totalSize = urlConnection.contentLength
                val inputStream = urlConnection.inputStream

                val file = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName)
                val outputStream = FileOutputStream(file)

                val buffer = ByteArray(4096)
                var bytesRead: Int
                var downloaded = 0

                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                    downloaded += bytesRead
                    val progress = (downloaded * 100) / totalSize
                    updateProgress(fileName, progress)
                }

                outputStream.close()
                inputStream.close()

                // Tải xong → thông báo hoàn tất
                val notification = NotificationCompat.Builder(this@DownloadFileService,
                    MyApplication.CHANNEL_NORMAL)
                    .setContentTitle("Tải xuống hoàn tất")
                    .setContentText(fileName)
                    .setSmallIcon(android.R.drawable.stat_sys_download_done)
                    .build()

                NotificationManagerCompat.from(this@DownloadFileService).notify(1, notification)

                stopForeground(false)
                stopSelf()

            } catch (e: Exception) {
                e.printStackTrace()
                stopSelf()
            }
        }
    }
}