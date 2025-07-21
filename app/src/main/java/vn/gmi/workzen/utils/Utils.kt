package vn.gmi.workzen.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Base64
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import kotlin.random.Random

object Utils {
    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    @SuppressLint("DefaultLocale")
    fun generateOTP():String{
        val otp = Random.nextInt(0,1000000)
        return String.format("%06d", otp)
    }
    fun base64ToBitmap(base64Str: String): Bitmap {
        val pureBase64 = base64Str.substringAfter("base64,")
        val decodedBytes = Base64.decode(pureBase64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }


    fun parseTime(date: Date): Triple<Int, Int, Int> {
        val calendar = Calendar.getInstance()
        calendar.time = date

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        return Triple(hour, minute, second)
    }

    fun uriToBase64(context: Context, uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes()
            inputStream?.close()
            if (bytes != null) Base64.encodeToString(bytes, Base64.NO_WRAP) else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun uriToFile(context: Context, uri: Uri): File? {
        return try {

            val contentResolver = context.contentResolver
            val mimeType = contentResolver.getType(uri) ?: "application/octet-stream"

            val inputStream = contentResolver.openInputStream(uri) ?: return null

            val fileName =  "upload_${System.currentTimeMillis()}.${MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "bin"}"
            val tempFile = File(context.cacheDir, fileName)
            tempFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            Log.d("uriToFile", "uriToFile: ${tempFile.name}")
            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}