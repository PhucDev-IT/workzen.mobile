package vn.gmi.workzen.ui.worksheet

import vn.gmi.workzen.core.base.BaseContract

interface WorkSheetContract {
    interface View: BaseContract.View{

    }

    interface Presenter: BaseContract.Presenter<View>{

    }
}