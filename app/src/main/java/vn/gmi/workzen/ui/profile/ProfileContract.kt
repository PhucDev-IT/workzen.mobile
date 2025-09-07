package vn.gmi.workzen.ui.profile

import vn.gmi.workzen.core.base.BaseContract
import vn.gmi.workzen.domain.entity.attendance.MonthlyWorkOverviewEntity
import vn.gmi.workzen.domain.entity.user.IdentificationEntity
import vn.gmi.workzen.domain.entity.user.ProfileEntity

interface ProfileContract {
    interface View:BaseContract.View{
        fun onMonthlyWorkOverview(entity: MonthlyWorkOverviewEntity)
    }

    interface Presenter:BaseContract.Presenter<View>{
        fun logout()
        fun getMonthlyWorkOverview()
    }
}