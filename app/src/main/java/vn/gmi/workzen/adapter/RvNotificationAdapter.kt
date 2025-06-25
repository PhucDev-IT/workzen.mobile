package vn.gmi.workzen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.RvNewspaperAdapter.ViewHolder
import vn.gmi.workzen.core.base.BaseAdapter
import vn.gmi.workzen.databinding.ItemBalanceMonthInfoBinding
import vn.gmi.workzen.databinding.ItemNotificationBinding
import vn.gmi.workzen.domain.entity.notification.Notification
import vn.gmi.workzen.utils.FormatUtils

class RvNotificationAdapter : BaseAdapter<Notification>() {


    class ViewHolder(itemView: View) : ItemViewHolder(itemView) {
        val binding = ItemNotificationBinding.bind(itemView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        bindView(holder, list[position])
    }

    override fun bindView(
        holder: ItemViewHolder,
        item: Notification
    ) {
        val view = holder as ViewHolder
        with(view.binding) {
            Glide.with(holder.itemView.context).load(item.iconPath).into(icon)
            tvTitle.text = item.title
            tvDes.text = item.content
            //tvTime.text = FormatUtils.dateTimeFormat.format(item.)
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }
}