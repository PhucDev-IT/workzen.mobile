package vn.gmi.workzen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.gmi.workzen.core.base.BaseAdapter
import vn.gmi.workzen.databinding.ItemSelectWalletViewBinding
import vn.gmi.workzen.domain.entity.enums.WalletType
import vn.gmi.workzen.domain.entity.wallet.LinkedWalletEntity

class RvCheckBoxWalletLinkedAdapter : BaseAdapter<LinkedWalletEntity>() {
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    class LinkedWalletViewHolder(val binding: ItemSelectWalletViewBinding): ItemViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
       return LinkedWalletViewHolder(ItemSelectWalletViewBinding.inflate(inflater, parent, false))
    }

    override fun bindView(
        holder: ItemViewHolder,
        item: LinkedWalletEntity
    ) {
        val viewHolder = holder as LinkedWalletViewHolder
        with(viewHolder.binding) {
            tvTitle.text = item.walletInfo?.name
            Glide.with(viewHolder.itemView.context)
                .load(item.walletInfo?.logo)
                .into(imgLogo)

            if(item.walletInfo?.type == WalletType.SYSTEM_WALLET.name){
                checkbox.visibility = View.INVISIBLE
            }

            // Gán trạng thái checkbox
            checkbox.isChecked = holder.bindingAdapterPosition == selectedPosition

            checkbox.setOnClickListener {
                val oldPosition = selectedPosition
                selectedPosition = holder.bindingAdapterPosition

                // Cập nhật lại cả item cũ và mới
                if (oldPosition != RecyclerView.NO_POSITION && oldPosition != selectedPosition) {
                    notifyItemChanged(oldPosition)
                }
                notifyItemChanged(selectedPosition)
            }
        }
    }


}