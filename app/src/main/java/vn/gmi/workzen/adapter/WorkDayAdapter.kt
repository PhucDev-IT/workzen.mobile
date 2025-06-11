package vn.gmi.workzen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.gmi.workzen.core.base.BaseAdapter
import vn.gmi.workzen.databinding.ItemTimeKeepingBinding
import vn.gmi.workzen.databinding.ItemWorkDayInfoBinding
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetDayEntity
import vn.gmi.workzen.domain.entity.enums.WorkStatus
import vn.gmi.workzen.ui.home.models.EAttendanceType
import vn.gmi.workzen.utils.DateUtils
import java.time.LocalDate

class WorkDayAdapter : BaseAdapter<ReportWorkSheetDayEntity>() {
    val weekdayOrder = mapOf(
        "MONDAY" to 0, "TUESDAY" to 1, "WEDNESDAY" to 2, "THURSDAY" to 3,
        "FRIDAY" to 4, "SATURDAY" to 5, "SUNDAY" to 6
    )

    private val dateNow = LocalDate.now()


    // Tạo ViewHolder riêng kế thừa từ BaseAdapter.ItemViewHolder
    class WorkDayViewHolder(itemView: View) : ItemViewHolder(itemView) {
        val binding: ItemWorkDayInfoBinding = ItemWorkDayInfoBinding.bind(itemView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val view =
            ItemWorkDayInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkDayViewHolder(view.root)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        bindView(holder, list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun bindView(
        holder: ItemViewHolder,
        item: ReportWorkSheetDayEntity
    ) {
        with((holder as WorkDayViewHolder).binding) {
            try {
                val date = DateUtils.stringToLocalDate(item.workDate.toString())
                tvDayName.text = date?.dayOfMonth.toString()
                if (date?.isAfter(dateNow) == true) {
                    tvSalary.text = "x"
                }else{
                    if(item.attendanceStatus == EAttendanceType.ABSENT.name){
                        tvSalary.text = "x"
                    }else{
                        tvSalary.text = item.salary.toString()
                        container.setCardBackgroundColor(vn.gmi.workzen.R.color.success)
                    }
                }

                if (item.status == WorkStatus.HOLIDAY_FULL.name) {
                    container.setCardBackgroundColor(vn.gmi.workzen.R.color.grey)
                    tvSalary.text = ""
                }
            } catch (e: Exception) {

            }

        }
    }
}