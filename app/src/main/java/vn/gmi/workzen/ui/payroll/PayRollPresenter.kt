package vn.gmi.workzen.ui.payroll

import android.util.Log
import kotlinx.coroutines.launch
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.domain.usecase.GetReportSalaryOfYearLocalUseCase
import vn.gmi.workzen.domain.usecase.GetReportSalaryOfYearRemoteUseCase
import java.time.LocalDate
import java.util.Random
import javax.inject.Inject


class PayRollPresenter @Inject constructor(
    private val getReportSalaryOfYearRemoteUseCase: GetReportSalaryOfYearRemoteUseCase,
    private val getReportSalaryOfYearLocalUseCase: GetReportSalaryOfYearLocalUseCase
): BasePresenter<PayRollContract.View>(), PayRollContract.Presenter {

    override fun requestGetBalance() {
       scope.launch {
           try{
               val now = LocalDate.now()
               val local = getReportSalaryOfYearRemoteUseCase.invoke(now.year)
               local?.let { getView()?.onResultBalance(it) }

               val remote = getReportSalaryOfYearLocalUseCase.invoke(now.year)
               remote?.let { getView()?.onResultBalance(it) }
           }catch (e: Exception){
               Log.e("PayRollPresenter", "requestGetBalance: ${e.message}")
           }
       }
    }


}