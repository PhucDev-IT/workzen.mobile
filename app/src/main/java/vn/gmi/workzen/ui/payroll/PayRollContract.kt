package vn.gmi.workzen.ui.payroll

import vn.gmi.workzen.core.base.BaseContract
import vn.gmi.workzen.domain.entity.attendance.MonthlyWorkOverviewEntity
import vn.gmi.workzen.domain.entity.attendance.StatisticSalaryOfYearEntity

interface PayRollContract  {
    interface View: BaseContract.View{
        fun onResultBalance(response:  StatisticSalaryOfYearEntity)
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun requestGetBalance()
    }
}