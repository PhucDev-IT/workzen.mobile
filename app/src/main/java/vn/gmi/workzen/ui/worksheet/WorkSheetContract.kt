package vn.gmi.workzen.ui.worksheet

import vn.gmi.workzen.base.BaseContract

interface WorkSheetContract {
    interface View:BaseContract.View{

    }

    interface Presenter:BaseContract.Presenter<View>{

    }
}