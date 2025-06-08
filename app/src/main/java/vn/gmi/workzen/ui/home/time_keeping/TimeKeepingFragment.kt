package vn.gmi.workzen.ui.home.time_keeping

import android.Manifest
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.RvItemKeepingAdapter
import vn.gmi.workzen.core.base.BaseFragment
import vn.gmi.workzen.core.constants.AppToast
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.core.ui.BottomSheetRequestPermissionFragment
import vn.gmi.workzen.data.models.response.attendance.GetWorkScheduleResModel
import vn.gmi.workzen.databinding.FragmentTimeKeepingBinding
import vn.gmi.workzen.ui.home.models.EAttendanceType
import vn.gmi.workzen.ui.home.models.ItemKeepingModel
import vn.gmi.workzen.utils.DateUtils
import vn.gmi.workzen.utils.MySharedPreferences
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class TimeKeepingFragment : BaseFragment<FragmentTimeKeepingBinding>(), TimeKeepingContract.View {

    @Inject
    lateinit var presenter: TimeKeepingContract.Presenter
    private lateinit var adapter: RvItemKeepingAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTimeKeepingBinding {
        return FragmentTimeKeepingBinding.inflate(inflater, container, false)
    }

    override fun initBindingData() {
        presenter.getInfoAttendance()
    }



    private val onListenerClickAdapter = object : RvItemKeepingAdapter.RequestAttendanceListener{
        override fun onClick(
            shiftId: String,
            attendanceType: EAttendanceType,
            targetTime: LocalTime?
        ) {
//            val isVerify = MySharedPreferences.getBooleanValue(SharedPreferenceKey.KEY_IS_ONBOARD)
//            if (!isVerify) {
//                AppToast.showError(requireContext(), "Cần phải onboard trước khi tiếp tục")
//            }
            if (!checkPermission()) {
                val bottomSheet = BottomSheetRequestPermissionFragment()
                bottomSheet.show(
                    parentFragmentManager,
                    BottomSheetRequestPermissionFragment::class.java.simpleName
                )
                return
            }
            when (attendanceType) {
                EAttendanceType.SHIFT_START -> {
                    presenter.checkIn()
                }

                EAttendanceType.SHIFT_END -> {
                    presenter.checkOut()
                }

                else -> {}
            }
        }
    }

    override fun onSingleClick(v: View?) {
    }

    override fun initView() {
        presenter.attachView(this)
        adapter = RvItemKeepingAdapter(requireContext(), onListenerClickAdapter)

        binding.rvTimeKeeping.adapter = adapter
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun onError(message: String) {

    }

    override fun onGetWorkScheduleSuccess(attendance: GetWorkScheduleResModel) {
        val list = mutableListOf<ItemKeepingModel>()
        if(attendance.shifts!=null){
            for(item in attendance.shifts){
                list.addAll(buildAttendance(item))
            }
            adapter.clear()
            adapter.addAll(list)
        }
    }

    override fun onAttendanceSuccess(attendance: GetWorkScheduleResModel) {

    }


    private fun checkPermission(): Boolean {
        val permissionGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (permissionGranted) {
            Toast.makeText(requireContext(), "Đã có quyền từ trước", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    private fun buildAttendance(item: GetWorkScheduleResModel.ShiftWorkInfo): List<ItemKeepingModel> {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val list = mutableListOf<ItemKeepingModel>()
        if (item.isOverTime == false) {
            list.add(
                ItemKeepingModel(
                    shiftId = item.shiftId.toString(),
                    title = "Giờ vào",
                    icon = R.drawable.ic_login,
                    time = item.checkInTime.toString(),
                    status = "",
                    reward = "",
                    iconColor = ContextCompat.getColor(context, R.color.green),
                    isChecked = item.checkedIn,
                    targetTime = LocalTime.parse(item.startTime, formatter),
                    attendanceType = EAttendanceType.SHIFT_START
                )
            )

            list.add(
                ItemKeepingModel(
                    shiftId = item.shiftId.toString(),
                    title = "Giờ về",
                    icon = R.drawable.ic_logout,
                    time = item.checkOutTime.toString(),
                    status = "",
                    reward = "",
                    iconColor = ContextCompat.getColor(context, R.color.pinkColor),
                    isChecked = item.checkedOut,
                    targetTime = LocalTime.parse(item.endTime, formatter),
                    attendanceType = EAttendanceType.SHIFT_END
                )
            )
        } else {
           list.add( ItemKeepingModel(
               shiftId = item.shiftId.toString(),
               title = "Tăng ca",
               icon = R.drawable.schedule,
               time = item.checkInTime.toString(),
               status = "",
               reward = "",
               iconColor = ContextCompat.getColor(context, R.color.purple),
               isChecked = item.checkedIn,
               targetTime = LocalTime.parse(item.startTime, formatter),
               attendanceType = EAttendanceType.OVERTIME_START
           ))
            list.add(ItemKeepingModel(
                shiftId = item.shiftId.toString(),
                title = "Kết thúc",
                icon = R.drawable.ic_timelapse,
                time = item.checkOutTime.toString(),
                status = "",
                reward = "",
                iconColor = ContextCompat.getColor(context, R.color.orange),
                isChecked = item.checkedOut,
                targetTime = LocalTime.parse(item.endTime, formatter),
                attendanceType = EAttendanceType.OVERTIME_END
            ))
        }

        return list
    }

}