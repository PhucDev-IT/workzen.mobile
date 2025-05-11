package vn.gmi.workzen.base

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

abstract class CoreViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    fun <T : View> findViewById(@IdRes id: Int): T {
        return itemView.findViewById(id)
    }
}