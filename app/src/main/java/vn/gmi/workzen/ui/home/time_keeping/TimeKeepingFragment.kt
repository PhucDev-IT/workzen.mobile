package vn.gmi.workzen.ui.home.time_keeping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.RvItemKeepingAdapter
import vn.gmi.workzen.core.base.BaseFragment
import vn.gmi.workzen.databinding.FragmentHomeBinding
import vn.gmi.workzen.databinding.FragmentTimeKeepingBinding
import vn.gmi.workzen.domain.entity.ShiftEntity
import vn.gmi.workzen.ui.home.models.ItemKeepingModel
import java.sql.Time
import javax.inject.Inject

@AndroidEntryPoint
class TimeKeepingFragment : BaseFragment<FragmentTimeKeepingBinding>(), TimeKeepingContract.View {

    @Inject lateinit var presenter: TimeKeepingContract.Presenter
    private lateinit var adapter:RvItemKeepingAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTimeKeepingBinding {
       return FragmentTimeKeepingBinding.inflate(inflater,container,false)
    }

    override fun initBindingData() {
        presenter.getShift()
    }

    override fun onSingleClick(v: View?) {
    }

    override fun initView() {
        presenter.attachView(this)
        adapter = RvItemKeepingAdapter(requireContext())
        val list = listOf(
            ItemKeepingModel("Giờ vào",R.drawable.ic_login,"08:30 am","Đi muộn","-150K", iconColor = ContextCompat.getColor(context, R.color.green) ),
            ItemKeepingModel("Giờ về",R.drawable.ic_logout,"05:10 pm","Đúng giờ","", iconColor = ContextCompat.getColor(context, R.color.pinkColor) ),
            ItemKeepingModel("Tăng ca",R.drawable.schedule,"06:01 pm","Project revision from ...","", iconColor = ContextCompat.getColor(context, R.color.purple) ),
            ItemKeepingModel("Kết thúc",R.drawable.ic_timelapse,"11:10 pm","5h 00m","+150K", iconColor = ContextCompat.getColor(context, R.color.orange) )
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

    override fun onGetShift(shift: ShiftEntity) {
        val list = listOf(
            ItemKeepingModel("Giờ vào",R.drawable.ic_login,"08:30 am","Đi muộn","-150K", iconColor = ContextCompat.getColor(context, R.color.green) ),
            ItemKeepingModel("Giờ về",R.drawable.ic_logout,"05:10 pm","Đúng giờ","", iconColor = ContextCompat.getColor(context, R.color.pinkColor) ),
            ItemKeepingModel("Tăng ca",R.drawable.schedule,"06:01 pm","Project revision from ...","", iconColor = ContextCompat.getColor(context, R.color.purple) ),
            ItemKeepingModel("Kết thúc",R.drawable.ic_timelapse,"11:10 pm","5h 00m","+150K", iconColor = ContextCompat.getColor(context, R.color.orange) )
        )
    }
}