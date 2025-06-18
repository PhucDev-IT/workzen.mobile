package vn.gmi.workzen.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.util.Consumer
import vn.gmi.workzen.R
import vn.gmi.workzen.core.base.BaseAdapter
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.data.models.response.attendance.GetWorkScheduleResModel
import vn.gmi.workzen.databinding.ItemTimeKeepingBinding
import vn.gmi.workzen.manager.SessionManager
import vn.gmi.workzen.ui.home.models.EAttendanceType
import vn.gmi.workzen.ui.home.models.ItemKeepingModel
import vn.gmi.workzen.utils.MySharedPreferences
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlin.math.min

class RvItemKeepingAdapter(private val context: Context, private val onItemClick: RequestAttendanceListener)  : BaseAdapter<ItemKeepingModel>() {

    private var startTime: LocalTime?=null
    private var endTime: LocalTime?=null
    private var baseSalary: Double?=null



    init {
        baseSalary = SessionManager.profile?.contracts?.firstOrNull { it.isActive }?.baseSalary
        getTimeWorking()
    }



    private fun getTimeWorking(){
        val startTimeLocal = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_SHIFT_START)
        val endTimeLocal = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_SHIFT_END)
        if (startTimeLocal != null && endTimeLocal != null) {
            startTime = LocalTime.parse(startTimeLocal)
            endTime = LocalTime.parse(endTimeLocal)
        }
    }

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

    @SuppressLint("SetTextI18n")
    override fun bindView(holder: ItemViewHolder, item: ItemKeepingModel) {
        val keepingHolder = holder as KeepingViewHolder
        var shouldShowButton = item.allowAttendance
        val currentTime = LocalTime.now()
        if(item.time!=null){
            val minutesLate = ChronoUnit.MINUTES.between(item.targetTime,  item.time?.toLocalTime()?:currentTime)
            if(item.attendanceType == EAttendanceType.SHIFT_START || item.attendanceType == EAttendanceType.OVERTIME_START){
                handleCheckIn(keepingHolder.binding,minutesLate)
            }else{
                handleCheckOut(keepingHolder.binding,minutesLate)
            }
        }




        with(keepingHolder.binding) {
            tvTitle.text = item.title
            tvTime.text = item.time?.toLocalTime().toString().replace("null","")
            if(shouldShowButton){
                llChamCong.visibility = View.VISIBLE
                tvTime.visibility = View.GONE
            }else{
                llChamCong.visibility = View.GONE
                tvTime.visibility = View.VISIBLE
            }

            icon.setImageResource(item.icon)
            icon.imageTintList = ColorStateList.valueOf(item.iconColor) // áp dụng tint icon
            containerIcon.setBackgroundColor(item.backgroundIcon) // áp dụng màu có opacity


        }
        keepingHolder.binding.llChamCong.setOnClickListener {
            onItemClick.onClick(item.shiftId, item.attendanceType)
        }
    }


    private fun handleCheckIn(binding: ItemTimeKeepingBinding, minutesLate: Long){
        if(minutesLate >= 0 && minutesLate <= 15){
            binding.tvStatus.text = "Đúng giờ"
        }else if(minutesLate > 15 && minutesLate <= 120){
            binding.tvStatus.text = "Muộn giờ"
        }else{
            binding.tvStatus.text = "Không thể chấm công"
            binding.llChamCong.visibility = View.GONE

        }

    }

    private fun handleCheckOut(binding: ItemTimeKeepingBinding, minutesLate: Long){
        if(minutesLate<0){
            binding.tvStatus.text = "Về sớm"
        }else
        if(minutesLate >= 0 && minutesLate <= 15){
            binding.tvStatus.text = "Đúng giờ"
        }else if(minutesLate > 15 && minutesLate <= 120){
            binding.tvStatus.text = "Quá giờ"
        }else{
            binding.tvStatus.text = "Không thể chấm công"
            binding.llChamCong.visibility = View.GONE
        }
    }


    interface RequestAttendanceListener{
        fun onClick(shiftId:String, attendanceType: EAttendanceType)

    }

}