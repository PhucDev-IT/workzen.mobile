package vn.gmi.workzen.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import vn.gmi.workzen.ui.profile.details.CurriculumVitaeFragment
import vn.gmi.workzen.ui.profile.details.UserInfoFragment
import vn.gmi.workzen.ui.profile.details.WorkContractFragment

class ProfileTabPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserInfoFragment()
            1 -> CurriculumVitaeFragment()
            2 -> WorkContractFragment()
            else -> throw IllegalArgumentException("Invalid tab")
        }
    }
}
