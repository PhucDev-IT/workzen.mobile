package vn.gmi.workzen.ui.worksheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.view.marginStart
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import vn.gmi.workzen.databinding.PopupSelectTimeWorkdateBinding
import java.time.LocalDate
import vn.gmi.workzen.R
class BottomSheetSelectTimeReportFragment : BottomSheetDialogFragment() {
    private var _binding: PopupSelectTimeWorkdateBinding? = null
    private val binding get() = _binding!!
    private var year = LocalDate.now().year
    private val radioButtons = mutableListOf<AppCompatRadioButton>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PopupSelectTimeWorkdateBinding.inflate(inflater, container, false)
        buildTimeSelection(LocalDate.now())
        setListener()
        return binding.root
    }


    private fun setListener(){
        binding.icBefore.setOnClickListener {
            val localDate = LocalDate.ofYearDay(year-1,1)
           buildTimeSelection(localDate)
            year --
        }

        binding.icNext.setOnClickListener {
            val localDate = LocalDate.ofYearDay(year+1,1)
            buildTimeSelection(localDate)
            year++
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun buildTimeSelection(now: LocalDate) {
        val year = now.year
        val currentMonth = now.monthValue

        binding.tvYear.text = year.toString()

        for (month in 1..currentMonth) {
            val radioButton = AppCompatRadioButton(requireContext()).apply {
                text = "Tháng $month"

                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    marginEnd = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._8sdp)
                    bottomMargin = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._8sdp)
                }

                setPadding(
                    resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp),
                    resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp),
                    resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp),
                    resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp),
                )

                gravity = Gravity.CENTER
                textSize = 12f
                setTextColor(resources.getColor(R.color.textPrimary, null))
                background = resources.getDrawable(R.drawable.radio_button_selection, null)
                buttonDrawable = null
                isChecked = false
                if(month == currentMonth){
                    isChecked = true
                }

                setOnClickListener {
                    radioButtons.forEach { it.isChecked = false }
                    isChecked = true
                }

            }


            radioButtons.add(radioButton)
            binding.radioGroup.addView(radioButton)
        }
    }
}
