package vn.gmi.workzen.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import vn.gmi.workzen.R
import vn.gmi.workzen.base.BaseAdapter
import vn.gmi.workzen.data.models.NewspaperModel
import vn.gmi.workzen.databinding.ItemNewspaperBinding


class RvNewspaperAdapter : BaseAdapter<NewspaperModel>() {

    class ViewHolder(itemView: View) : ItemViewHolder(itemView) {
        val binding: ItemNewspaperBinding = ItemNewspaperBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_newspaper, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        bindView(holder, list[position])
    }

    override fun getItemCount(): Int = list.size

    override fun bindView(holder: ItemViewHolder, item: NewspaperModel) {
        val view = holder as ViewHolder
        with(view.binding) {
            Glide.with(view.itemView.context).load(item.imageNetwork).into(img)
            tvTitle.text = item.title
            tvDes.text = item.des
        }
    }
}
