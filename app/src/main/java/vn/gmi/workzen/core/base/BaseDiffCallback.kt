package vn.gmi.workzen.core.base

import androidx.recyclerview.widget.DiffUtil

class BaseDiffCallback<T>(
    private val oldList: List<T>,
    private val newList: List<T>,
    private val areItemsSame: (T, T) -> Boolean,
    private val areContentsSame: (T, T) -> Boolean = { o, n -> o == n }
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        areItemsSame(oldList[oldItemPosition], newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        areContentsSame(oldList[oldItemPosition], newList[newItemPosition])
}
