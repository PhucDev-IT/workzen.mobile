package vn.gmi.workzen.ui.payroll

import vn.gmi.workzen.base.BaseContract
import vn.gmi.workzen.data.models.PayRollOfYearModel

interface PayRollContract  {
    interface View:BaseContract.View{
        fun onResultBalance(items:List<PayRollOfYearModel>)
    }

    interface Presenter:BaseContract.Presenter<View>{
        fun requestGetBalance()
    }
}