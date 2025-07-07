package vn.gmi.workzen.ui.home.time_keeping

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.AndroidEntryPoint
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.RvItemKeepingAdapter
import vn.gmi.workzen.core.base.BaseFragment
import vn.gmi.workzen.core.constants.AppToast
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.core.ui.BottomSheetRequestPermissionFragment
import vn.gmi.workzen.data.models.response.attendance.GetWorkScheduleResModel
import vn.gmi.workzen.data.models.response.attendance.ShiftWorkInfo
import vn.gmi.workzen.databinding.FragmentTimeKeepingBinding
import vn.gmi.workzen.domain.entity.enums.WorkStatus
import vn.gmi.workzen.manager.SessionManager
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
    private val radiusInMeters = 2 * 100 //1 KM = 1000 Meter


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTimeKeepingBinding {
        return FragmentTimeKeepingBinding.inflate(inflater, container, false)
    }

    override fun initBindingData() {
        presenter.getInfoAttendance()
    }


    private val onListenerClickAdapter = object : RvItemKeepingAdapter.RequestAttendanceListener {
        override fun onClick(
            shiftId: String,
            attendanceType: EAttendanceType,
        ) {
            val isVerify = MySharedPreferences.getBooleanValue(SharedPreferenceKey.KEY_IS_ONBOARD)
            if (!isVerify) {
                AppToast.showError(requireContext(), "Cần phải onboard trước khi tiếp tục")
                return
            }
            if (!checkPermission()) {
                val bottomSheet = BottomSheetRequestPermissionFragment()
                bottomSheet.show(
                    parentFragmentManager,
                    BottomSheetRequestPermissionFragment::class.java.simpleName
                )
                return
            }
            val (latCompany, longCompany) = getLatLongCompany()
            Log.d("Company lat: ", "$latCompany, $longCompany")
            checkLocationInCenterRadius { latitude, longitude ->
                val results = FloatArray(1)
                Location.distanceBetween(
                    latCompany,
                    longCompany,
                    latitude,
                    longitude,
                    results
                )

                val distanceInMeters = results[0]
                if (distanceInMeters <= 20f) {
                    Log.d("CHECK", "Trong bán kính 20m: $distanceInMeters m")
                    when (attendanceType) {
                        EAttendanceType.SHIFT_START -> {
                            presenter.checkIn()
                        }

                        EAttendanceType.SHIFT_END -> {
                            presenter.checkOut()
                        }

                        else -> {}
                    }
                } else {
                    Log.d("CHECK", "Ngoài bán kính: $distanceInMeters m")
                    AppToast.showError(
                        requireContext(),
                        "Vui lòng chấm công đúng địa điểm làm của bạn"
                    )
                }

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
        if (attendance.shifts != null) {
            for (item in attendance.shifts) {
                list.addAll(buildAttendance(item, attendance.workStatus?: WorkStatus.OFF))
            }
            adapter.clear()
            adapter.addAll(list)
        }
    }

    override fun onAttendanceSuccess(attendance: GetWorkScheduleResModel) {
        val list = mutableListOf<ItemKeepingModel>()
        if (attendance.shifts != null) {
            for (item in attendance.shifts) {
                list.addAll(buildAttendance(item, attendance.workStatus?: WorkStatus.OFF))
            }
            adapter.clear()
            adapter.addAll(list)
        }
    }


    private fun checkPermission(): Boolean {
        val permissionGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return permissionGranted
    }

    private fun buildAttendance(item: ShiftWorkInfo, workStatus: WorkStatus): List<ItemKeepingModel> {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val list = mutableListOf<ItemKeepingModel>()
        if (item.overTime == false) {
            list.add(
                ItemKeepingModel(
                    shiftId = item.shiftId.toString(),
                    title = "Giờ vào",
                    icon = R.drawable.ic_login,
                    time = item.checkInTime?.let {
                        DateUtils.stringToLocalDateTime(it)
                    },
                    iconColor = ContextCompat.getColor(context, R.color.green),
                    allowAttendance = !item.checkedIn && workStatus == WorkStatus.WORKING,
                    targetTime = LocalTime.parse(item.startTime, formatter),
                    attendanceType = EAttendanceType.SHIFT_START
                )
            )

            list.add(
                ItemKeepingModel(
                    shiftId = item.shiftId.toString(),
                    title = "Giờ về",
                    icon = R.drawable.ic_logout,
                    time = item.checkOutTime?.let {
                        DateUtils.stringToLocalDateTime(it)
                    },

                    iconColor = ContextCompat.getColor(context, R.color.pinkColor),
                    allowAttendance = !item.checkedOut && item.checkedIn && workStatus == WorkStatus.WORKING,
                    targetTime = LocalTime.parse(item.endTime, formatter),
                    attendanceType = EAttendanceType.SHIFT_END
                )
            )
        } else {
            list.add(
                ItemKeepingModel(
                    shiftId = item.shiftId.toString(),
                    title = "Tăng ca",
                    icon = R.drawable.schedule,
                    time = item.checkInTime?.let {
                        DateUtils.stringToLocalDateTime(it)
                    },
                    iconColor = ContextCompat.getColor(context, R.color.purple),
                    allowAttendance = !item.checkedIn && workStatus == WorkStatus.WORKING,
                    targetTime = LocalTime.parse(item.startTime, formatter),
                    attendanceType = EAttendanceType.OVERTIME_START
                )
            )
            list.add(
                ItemKeepingModel(
                    shiftId = item.shiftId.toString(),
                    title = "Kết thúc",
                    icon = R.drawable.ic_timelapse,
                    time = item.checkOutTime?.let {
                        DateUtils.stringToLocalDateTime(it)
                    },
                    iconColor = ContextCompat.getColor(context, R.color.orange),
                    allowAttendance = !item.checkedOut && workStatus == WorkStatus.WORKING,
                    targetTime = LocalTime.parse(item.endTime, formatter),
                    attendanceType = EAttendanceType.OVERTIME_END
                )
            )
        }

        return list
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun checkLocationInCenterRadius(callback: (latitude: Double, longitude: Double) -> Unit) {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        val locationRequest = LocationRequest.create()
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setInterval(1000) // 1s

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    Log.d("Location", "Latitude: $latitude, Longitude: $longitude")

                    // Trả kết quả qua callback
                    callback(latitude, longitude)

                    // Remove location updates
                    fusedLocationProviderClient.removeLocationUpdates(this)
                } else {
                    Log.w("Location", "Failed to get location update")
                }
            }
        }

        // Đăng ký cập nhật vị trí
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun getLatLongCompany() : Pair<Double, Double>{
        val contracts = SessionManager.profile?.contracts
        val company = contracts?.first { it.isActive }?.company
        return Pair(company?.latitude?:0.0, company?.longitude?:0.0)

    }
}