package vn.gmi.workzen.utils

import android.annotation.SuppressLint
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class FormatUtils {
  companion object{
      private val lc = Locale("vi","VN")
      val numberFormat: NumberFormat = NumberFormat.getCurrencyInstance(lc)

      // Định dạng ngày tháng
      @SuppressLint("ConstantLocale")
      val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ROOT)

      @SuppressLint("ConstantLocale")
      val dateTimeFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ROOT)


      val timeFormatter = SimpleDateFormat("HH:mm:ss",Locale.ROOT)
  }
}