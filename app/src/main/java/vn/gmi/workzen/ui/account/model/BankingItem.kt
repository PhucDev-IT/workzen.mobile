package vn.gmi.workzen.ui.account.model

import android.graphics.drawable.Drawable
import java.io.Serializable

data class BankingItem (
    val id:String,
    val logo: Int,
    val name:String,
    val shortName:String
): Serializable