package vn.gmi.workzen.ui.payroll

import vn.gmi.workzen.base.BasePresenter
import vn.gmi.workzen.data.models.PayRollOfYearModel
import java.util.Random


class PayRollPresenter : BasePresenter<PayRollContract.View>(), PayRollContract.Presenter {

    override fun requestGetBalance() {
        getView()?.onResultBalance(randomData())
    }

    private fun randomData(): List<PayRollOfYearModel> {
       val list = arrayListOf<PayRollOfYearModel>()
        val random = Random()
        for (i in 1..12) {
            val value = random.nextInt(9_000_001) + 1_000_000 // từ 1_000_000 đến 10_000_000
            list.add(PayRollOfYearModel(i,value.toDouble()))
        }
        return list
    }

}