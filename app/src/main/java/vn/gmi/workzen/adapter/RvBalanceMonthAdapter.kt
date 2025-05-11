package vn.gmi.workzen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.gmi.workzen.R
import vn.gmi.workzen.base.BaseAdapter
import vn.gmi.workzen.data.models.PayRollOfYearModel
import vn.gmi.workzen.databinding.ItemBalanceMonthInfoBinding
import vn.gmi.workzen.utils.FormatCurrency

class RvBalanceMonthAdapter : BaseAdapter<PayRollOfYearModel>() {

    class ViewHolder(itemView:View):ItemViewHolder(itemView){
        val binding = ItemBalanceMonthInfoBinding.bind(itemView)
    }

    override fun bindView(holder: ItemViewHolder, item: PayRollOfYearModel) {
        val view = holder as ViewHolder
        with(view.binding){
            tvMonth.text = "Tháng ${item.month}"
            tvSalary.text =FormatCurrency.numberFormat.format(item.salary)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_balance_month_info,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        bindView(holder, list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}