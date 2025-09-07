package vn.gmi.workzen.ui.main

import vn.gmi.workzen.core.base.BaseContract
import vn.gmi.workzen.domain.entity.contract.ContractEntity

interface MainContract {
    interface View: BaseContract.View{

    }

    interface Presenter: BaseContract.Presenter<View>{
        fun observeProfile()
    }
}