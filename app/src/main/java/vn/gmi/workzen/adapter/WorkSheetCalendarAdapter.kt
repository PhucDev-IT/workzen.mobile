package vn.gmi.workzen.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import vn.gmi.workzen.databinding.ItemWorkDayInfoBinding
import vn.gmi.workzen.domain.entity.attendance.WorkDayItem
import vn.gmi.workzen.R
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetDayEntity
import vn.gmi.workzen.domain.entity.enums.WorkStatus
import vn.gmi.workzen.ui.home.models.EAttendanceType
import vn.gmi.workzen.utils.DateUtils
import java.time.LocalDate

class WorkSheetCalendarAdapter : RecyclerView.Adapter<WorkSheetCalendarAdapter.WorkDayViewHolder>() {
    private var list: List<ReportWorkSheetDayEntity> = listOf()


    @SuppressLint("NotifyDataSetChanged")
    fun setFullData(fullList: List<ReportWorkSheetDayEntity>) {
        list = fullList
        notifyDataSetChanged()
    }

    class WorkDayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemWorkDayInfoBinding.bind(view)
        private val dateNow = LocalDate.now()

        fun bind(item: ReportWorkSheetDayEntity) {
            val context = binding.root.context
            val date = DateUtils.stringToLocalDate(item.workDate ?: "")
            binding.tvDayName.text = date?.dayOfMonth?.toString() ?: ""

            // Reset mặc định trước
            binding.tvSalary.text = ""
            binding.tvSalary.setTextColor(ContextCompat.getColor(context, R.color.black))
            binding.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white)) // mặc định

            if(item.status == null || item.status ==  WorkStatus.OFF.name){
                binding.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_light))
            } else {
                when (item.attendanceStatus) {
                    EAttendanceType.WORKED.name,
                    EAttendanceType.BONUS.name -> {
                        binding.tvSalary.text = item.salary.toString()
                        binding.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.success))
                    }

                    EAttendanceType.ABSENT.name -> {
                        binding.tvSalary.text = "x"
                        binding.tvSalary.setTextColor(ContextCompat.getColor(context, R.color.failed))
                        binding.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
                    }

                    else -> {
                        // Nếu muốn: hiện màu riêng cho trạng thái khác
                        binding.tvSalary.text = item.salary.toString()
                        binding.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.red_very_light))
                    }
                }
            }

            if(date == dateNow){
                binding.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange))
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WorkDayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_work_day_info, parent, false)
        return WorkDayViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: WorkDayViewHolder,
        position: Int
    ) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}