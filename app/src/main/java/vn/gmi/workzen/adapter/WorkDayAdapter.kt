package vn.gmi.workzen.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import vn.gmi.workzen.R
import vn.gmi.workzen.core.base.BaseAdapter
import vn.gmi.workzen.databinding.ItemTimeKeepingBinding
import vn.gmi.workzen.databinding.ItemWorkDayInfoBinding
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetDayEntity
import vn.gmi.workzen.domain.entity.attendance.WorkDayItem
import vn.gmi.workzen.domain.entity.enums.WorkStatus
import vn.gmi.workzen.ui.home.models.EAttendanceType
import vn.gmi.workzen.utils.DateUtils
import java.time.LocalDate

class WorkDayAdapter(private val recyclerView: RecyclerView) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<WorkDayItem>()
    private val dateNow = LocalDate.now()

    companion object {
        private const val TYPE_EMPTY = 0
        private const val TYPE_DAY = 1
    }

    class WorkDayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemWorkDayInfoBinding = ItemWorkDayInfoBinding.bind(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFullData(fullList: List<WorkDayItem>) {
        items.clear()
        items.addAll(fullList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is WorkDayItem.EmptyDay -> TYPE_EMPTY
            is WorkDayItem.WorkDay -> TYPE_DAY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val heightPerRow = recyclerView.measuredHeight / 6

        return if (viewType == TYPE_EMPTY) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_empty_day, parent, false)
            view.layoutParams.height = heightPerRow
            object : RecyclerView.ViewHolder(view) {}
        } else {
            val binding = ItemWorkDayInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            binding.root.layoutParams.height = heightPerRow
            WorkDayViewHolder(binding.root)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Set chiều cao mỗi lần bind để đảm bảo tái sử dụng vẫn đúng
        val heightPerRow = recyclerView.measuredHeight / 6
        holder.itemView.layoutParams.height = heightPerRow
        holder.itemView.requestLayout()

        if (holder is WorkDayViewHolder && items[position] is WorkDayItem.WorkDay) {
            val item = (items[position] as WorkDayItem.WorkDay).data
            bindView(holder, item)
        }
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("SetTextI18n")
    private fun bindView(holder: WorkDayViewHolder, item: ReportWorkSheetDayEntity) {
        val binding = holder.binding
        val context = holder.itemView.context
        val date = DateUtils.stringToLocalDate(item.workDate ?: "")
        binding.tvDayName.text = date?.dayOfMonth?.toString() ?: ""

        when {
            item.status == WorkStatus.OFF.name -> {
                binding.tvSalary.text = ""
                binding.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_light))
            }
            else -> {
                when (item.attendanceStatus) {
                    //ĐI LÀM
                    EAttendanceType.WORKED.name,
                    EAttendanceType.BONUS.name -> {
                        binding.tvSalary.text = item.salary.toString()
                        binding.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.success))
                    }

                    //NGHỈ LÀM
                    EAttendanceType.ABSENT.name -> {
                        binding.tvSalary.text = "x"
                        binding.tvSalary.setTextColor(ContextCompat.getColor(context, R.color.failed))
                    }

                    else -> {
//                        binding.tvSalary.text = item.salary.toString()
//                        binding.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange))
                    }
                }
            }
        }

        if(date == dateNow){
            binding.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green_light))
        }
    }
}
