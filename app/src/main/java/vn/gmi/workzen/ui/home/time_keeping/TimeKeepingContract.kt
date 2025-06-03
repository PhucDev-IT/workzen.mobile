package vn.gmi.workzen.ui.home.time_keeping

import vn.gmi.workzen.core.base.BaseContract
import vn.gmi.workzen.domain.entity.ShiftEntity

interface TimeKeepingContract {
    interface  View: BaseContract.View{
        fun onGetShift(shift: ShiftEntity)
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun getShift()
    }
}