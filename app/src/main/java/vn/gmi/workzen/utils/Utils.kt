package vn.gmi.workzen.utils

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

object Utils {
    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return "data:image/jpeg;base64,"+ Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}