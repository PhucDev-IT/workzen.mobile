package vn.gmi.workzen.utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream
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
        Log.d("Phuc","base64Str: $pureBase64")
        val decodedBytes = Base64.decode(pureBase64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }


}