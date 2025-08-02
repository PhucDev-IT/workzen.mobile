package vn.gmi.workzen.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.util.Consumer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.gmi.workzen.core.base.BaseAdapter
import vn.gmi.workzen.databinding.ItemSelectWalletViewBinding
import vn.gmi.workzen.databinding.ItemSelectorBankingViewBinding
import vn.gmi.workzen.ui.account.model.BankingItem
import vn.gmi.workzen.R
import vn.gmi.workzen.domain.entity.wallet.WalletEntity

class RvSelectorBankingAdapter(private val onClick: Consumer<WalletEntity>) : BaseAdapter<WalletEntity>() {


    class BankingViewHolder( val binding: ItemSelectorBankingViewBinding): ItemViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BankingViewHolder(ItemSelectorBankingViewBinding.inflate(inflater, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun bindView(
        holder: ItemViewHolder,
        item: WalletEntity
    ) {
       with(holder as BankingViewHolder){
           Glide.with(holder.itemView.context).load(item.logo).into(binding.imgLogo)
           binding.tvNameBanking.text = "${item.shortName} - ${item.name}"

           binding.root.setOnClickListener {
               onClick.accept(item)
           }
       }
    }
}