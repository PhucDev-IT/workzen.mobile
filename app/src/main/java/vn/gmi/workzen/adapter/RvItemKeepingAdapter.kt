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
import vn.gmi.workzen.core.base.CoreViewHolder
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.data.models.response.attendance.AttendanceResModel
import vn.gmi.workzen.databinding.ItemTimeKeepingBinding
import vn.gmi.workzen.ui.home.models.EAttendanceType
import vn.gmi.workzen.ui.home.models.ItemKeepingModel
import vn.gmi.workzen.utils.MySharedPreferences
import java.time.LocalTime
import java.util.Calendar

class RvItemKeepingAdapter(private val context:Context, private val onItemClick: Consumer<EAttendanceType>) : BaseAdapter<ItemKeepingModel>() {

    private var startTime: LocalTime?=null
    private var endTime: LocalTime?=null
    private var attendance: AttendanceResModel?=null

    init {
        getTimeWorking()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTimeWorking(){
        getTimeWorking()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAttendance(attendance: AttendanceResModel){
        this.attendance = attendance
        notifyDataSetChanged()
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

    override fun bindView(holder: ItemViewHolder, item: ItemKeepingModel) {
        val keepingHolder = holder as KeepingViewHolder
        val shouldShowButton = shouldShowButton(item.attendanceType)
        with(keepingHolder.binding) {
            tvTitle.text = item.title
            tvTime.text = item.time
            tvStatus.text = item.status
            tvReward.text = item.reward
            tvReward.setTextColor(
                ContextCompat.getColor(context, if (item.reward.startsWith("-")) R.color.failed else R.color.success)
            )
            llChamCong.visibility = if (shouldShowButton) View.VISIBLE else View.GONE
            icon.setImageResource(item.icon)
            icon.imageTintList = ColorStateList.valueOf(item.iconColor) // áp dụng tint icon
            containerIcon.setBackgroundColor(item.backgroundIcon) // áp dụng màu có opacity
        }
        keepingHolder.binding.llChamCong.setOnClickListener {
            onItemClick.accept(item.attendanceType)
        }
    }

    private fun shouldShowButton(type: EAttendanceType): Boolean {
        val now = LocalTime.now()
        return when(type){
            EAttendanceType.SHIFT_START -> {
                startTime != null &&
                        (now.isAfter(startTime) || now == startTime) && attendance?.checkIn == null && now.isBefore(endTime)
            }
            EAttendanceType.SHIFT_END ->{
                endTime != null &&
                        (now.isAfter(endTime) || now == endTime)
            }
            else -> false

        }
    }


}