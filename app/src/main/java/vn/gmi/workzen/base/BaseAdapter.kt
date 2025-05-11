package vn.gmi.workzen.base

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<vn.gmi.workzen.base.BaseAdapter.ItemViewHolder>() {
    var list: MutableList<T> = ArrayList()

    open class ItemViewHolder(itemView: View) : CoreViewHolder(itemView)

    abstract fun bindView(holder: ItemViewHolder, item: T)

    fun addAll(itemList: List<T>) {
        this.list.addAll(itemList)
        notifyItemInserted(itemCount)
    }

    fun addItem(item: T) {
        this.list.add(item)
        notifyItemInserted(itemCount)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(position: Int) {
        try {
            list.removeAt(position)
            notifyDataSetChanged()
        } catch (e: Exception) {
            Log.d("remove:", e.message.toString())
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        this.list.clear()
        notifyDataSetChanged()
    }
}