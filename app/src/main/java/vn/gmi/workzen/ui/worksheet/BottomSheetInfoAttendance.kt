package vn.gmi.workzen.ui.worksheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import vn.gmi.workzen.databinding.ViewInfoAttendanceDetailBinding
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetDayEntity
import vn.gmi.workzen.domain.usecase.GetReportWorkSheetTheDayLocalUseCase
import vn.gmi.workzen.domain.usecase.ReportAttendanceByMonthYearLocalUseCase
import vn.gmi.workzen.utils.DateUtils
import vn.gmi.workzen.utils.FormatUtils
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class BottomSheetInfoAttendance : BottomSheetDialogFragment (){

    private var _binding : ViewInfoAttendanceDetailBinding?=null
    private val binding get() = _binding!!
    private lateinit var idAttendance:String
    @Inject lateinit var getReportWorkSheetTheDayLocalUseCase: GetReportWorkSheetTheDayLocalUseCase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ViewInfoAttendanceDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idAttendance = arguments?.getString("id") ?: ""

        getData(idAttendance)
    }



    @SuppressLint("SetTextI18n")
    private fun getData(id:String){
        lifecycleScope.launch {
            try{
                val result = getReportWorkSheetTheDayLocalUseCase.invoke(id)

                binding.tvTime.text =  result?.workDate?.let {

                    "Thời gian: $it"
                }?: ""

                binding.tvTimeCheckin.text = result?.data?.checkIn?.let {
                    FormatUtils.timeFormatter.format(it)
                }?: "N/A"


                binding.tvTimeCheckout.text = result?.data?.checkOut?.let {
                    FormatUtils.timeFormatter.format(it)
                }?: "N/A"
                binding.tvNote.text = result?.note

            }catch (e:Exception){
                e.printStackTrace()
        }
    }
    }



    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    companion object{
        const val TAG = "BottomSheetInfoAttendance"

        fun newInstance(id:String) = BottomSheetInfoAttendance().apply {
            arguments = Bundle().apply {
                putString("id", id)
            }
        }
    }
}