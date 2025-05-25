package vn.gmi.workzen.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import vn.gmi.workzen.R
import vn.gmi.workzen.core.base.BaseFragment
import vn.gmi.workzen.databinding.FragmentProfileBinding


class ProfileFragment : BaseFragment<FragmentProfileBinding>(),ProfileContract.View {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater,container,false)
    }

    override fun initBindingData() {

    }

    override fun onSingleClick(v: View?) {

    }

    override fun onError(message: String) {

    }

    override fun initView() {
        Glide.with(this).load("https://img.freepik.com/free-photo/emotions-people-concept-headshot-serious-looking-handsome-man-with-beard-looking-confident-determined_1258-26730.jpg?size=626&ext=jpg&uid=R118572234&ga=GA1.1.1965375583.1709184711&semt=ais_user").into(binding.imgAvatar)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}