package vn.gmi.workzen.core.base

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.BaseAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseAdapter.ItemViewHolder>() {

    private val set = LinkedHashSet<T>()
    var list: MutableList<T> = ArrayList()

    open class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    abstract fun bindView(holder: ItemViewHolder, item: T)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        bindView(holder, list[position])
    }

    override fun getItemCount(): Int = list.size

    fun addItem(item: T) {
        if (set.add(item)) {
            list.add(item)
            notifyItemInserted(list.size - 1)
        }
    }

    fun addAll(items: List<T>) {
        addOrUpdateAll(items)
    }

    fun addOrUpdateAll(items: List<T>) {
        val diffCallback = BaseDiffCallback(
            oldList = list,
            newList = items,
            areItemsSame = { old, new -> old == new }, // hoặc tự override theo ID
            areContentsSame = { old, new -> old == new }
        )

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(items)
        set.clear()
        set.addAll(items)

        diffResult.dispatchUpdatesTo(this)
    }



    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(position: Int) {
        if (position in list.indices) {
            val removed = list.removeAt(position)
            set.remove(removed)
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        list.clear()
        set.clear()
        notifyDataSetChanged()
    }

    fun getSize() = list.size
}
