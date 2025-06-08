package vn.gmi.workzen.ui.home.time_keeping

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.RvItemKeepingAdapter
import vn.gmi.workzen.core.base.BaseFragment
import vn.gmi.workzen.core.constants.AppToast
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.core.ui.BottomSheetRequestPermissionFragment
import vn.gmi.workzen.data.models.response.attendance.AttendanceResModel
import vn.gmi.workzen.databinding.FragmentHomeBinding
import vn.gmi.workzen.databinding.FragmentTimeKeepingBinding
import vn.gmi.workzen.domain.entity.shift.ShiftEntity
import vn.gmi.workzen.ui.home.models.EAttendanceType
import vn.gmi.workzen.ui.home.models.ItemKeepingModel
import vn.gmi.workzen.utils.MySharedPreferences
import java.sql.Time
import java.util.function.Consumer
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

    private val onListenerClickAdapter = object : androidx.core.util.Consumer<EAttendanceType> {
        override fun accept(t: EAttendanceType) {
            val isVerify = MySharedPreferences.getBooleanValue(SharedPreferenceKey.KEY_IS_ONBOARD)
            if(!isVerify){
                AppToast.showError(requireContext(),"Cần phải onboard trước khi tiếp tục")
            }
           if(!checkPermission()){
               val bottomSheet = BottomSheetRequestPermissionFragment()
               bottomSheet.show(parentFragmentManager, BottomSheetRequestPermissionFragment::class.java.simpleName)
           }
            when (t) {
                EAttendanceType.SHIFT_START -> {
                    presenter.checkIn()
                }

                EAttendanceType.SHIFT_END -> {
                    presenter.checkOut()
                }
                else ->{}
            }
        }
    }

    override fun onSingleClick(v: View?) {
    }

    override fun initView() {
        presenter.attachView(this)
        adapter = RvItemKeepingAdapter(requireContext(),onListenerClickAdapter)
        val list = listOf(
            ItemKeepingModel(
                "Giờ vào",
                R.drawable.ic_login,
                "08:30 am",
                "Đi muộn",
                "-150K",
                iconColor = ContextCompat.getColor(context, R.color.green),
                attendanceType = EAttendanceType.SHIFT_START
            ),
            ItemKeepingModel(
                "Giờ về",
                R.drawable.ic_logout,
                "05:10 pm",
                "Đúng giờ",
                "",
                iconColor = ContextCompat.getColor(context, R.color.pinkColor),
                attendanceType = EAttendanceType.SHIFT_END
            ),
            ItemKeepingModel(
                "Tăng ca",
                R.drawable.schedule,
                "06:01 pm",
                "Project revision from ...",
                "",
                iconColor = ContextCompat.getColor(context, R.color.purple),
                attendanceType = EAttendanceType.OVERTIME_START
            ),
            ItemKeepingModel(
                "Kết thúc",
                R.drawable.ic_timelapse,
                "11:10 pm",
                "5h 00m",
                "+150K",
                iconColor = ContextCompat.getColor(context, R.color.orange),
                attendanceType = EAttendanceType.OVERTIME_END
            )
        )
        adapter.addAll(list)
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

    override fun onGetInfoAttendance(attendance: AttendanceResModel) {
        adapter.setAttendance(attendance)
    }

    override fun onAttendanceSuccess(attendance: AttendanceResModel) {
        adapter.setAttendance(attendance)
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

}