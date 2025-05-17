package vn.gmi.workzen.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.RvNewspaperAdapter
import vn.gmi.workzen.core.base.BaseFragment
import vn.gmi.workzen.data.models.NewspaperModel
import vn.gmi.workzen.databinding.FragmentHomeBinding
import vn.gmi.workzen.ui.home.time_keeping.TimeKeepingFragment


class HomeFragment : BaseFragment<FragmentHomeBinding>(),HomeContract.View {

    private lateinit var presenter: HomeContract.Presenter
    private lateinit var adapter:RvNewspaperAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater,container,false)
    }

    override fun initBindingData() {
    }

    override fun onSingleClick(v: View?) {

    }

    @SuppressLint("CommitTransaction")
    override fun initView() {
        childFragmentManager.beginTransaction().replace(R.id.timeKeepingFragment, TimeKeepingFragment()).commit()
        adapter = RvNewspaperAdapter()
        binding.rvNewspaper.adapter = adapter
        binding.rvNewspaper.layoutManager = LinearLayoutManager(requireContext())

        presenter = HomePresenter()
        presenter.attachView(this)

        presenter.getNotificationAndEvent()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    //================= PRESENTER========================================
    override fun onResultNotificationAndEvents(items: List<NewspaperModel>) {
        adapter.addAll(items)
    }
}