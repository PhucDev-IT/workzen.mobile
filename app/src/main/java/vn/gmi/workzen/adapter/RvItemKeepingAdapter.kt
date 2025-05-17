package vn.gmi.workzen.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import vn.gmi.workzen.R
import vn.gmi.workzen.core.base.BaseAdapter
import vn.gmi.workzen.core.base.CoreViewHolder
import vn.gmi.workzen.databinding.ItemTimeKeepingBinding
import vn.gmi.workzen.ui.home.models.ItemKeepingModel

class RvItemKeepingAdapter(private val context:Context) : BaseAdapter<ItemKeepingModel>() {

    // Tạo ViewHolder riêng kế thừa từ BaseAdapter.ItemViewHolder
    class KeepingViewHolder(itemView: View) : ItemViewHolder(itemView) {
        val binding: ItemTimeKeepingBinding = ItemTimeKeepingBinding.bind(itemView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_time_keeping, parent, false)
        return KeepingViewHolder(view)
    }


    override fun getItemCount(): Int  = list.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        bindView(holder, list[position])
    }

    override fun bindView(holder: ItemViewHolder, item: ItemKeepingModel) {
        val keepingHolder = holder as KeepingViewHolder
        with(keepingHolder.binding) {
            tvTitle.text = item.title
            tvTime.text = item.time
            tvStatus.text = item.status
            tvReward.text = item.reward
            tvReward.setTextColor(
                ContextCompat.getColor(context, if (item.reward.startsWith("-")) R.color.failed else R.color.success)
            )

            icon.setImageResource(item.icon)
            icon.imageTintList = ColorStateList.valueOf(item.iconColor) // áp dụng tint icon
            containerIcon.setBackgroundColor(item.backgroundIcon) // áp dụng màu có opacity
        }
    }


}