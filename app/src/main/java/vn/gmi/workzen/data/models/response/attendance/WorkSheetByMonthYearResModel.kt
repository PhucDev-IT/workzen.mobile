package vn.gmi.workzen.data.models.response.attendance

import com.google.gson.annotations.SerializedName
import io.realm.kotlin.ext.realmListOf
import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetDayEntity
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetMonthYearEntity

class WorkSheetByMonthYearResModel : DataMapper<ReportWorkSheetMonthYearEntity>(){

    var month:Int?=null
    var year:Int?=null
    @SerializedName("attendances")
    var days: List<WorkSheetDayResModel>?=null


    override fun mapToEntity(): ReportWorkSheetMonthYearEntity {
        val data = realmListOf<ReportWorkSheetDayEntity>().apply {
            this@WorkSheetByMonthYearResModel.days?.forEach {
                add(it.mapToEntity())
            }
        }
        return ReportWorkSheetMonthYearEntity().apply {
            month = this@WorkSheetByMonthYearResModel.month
            year = this@WorkSheetByMonthYearResModel.year
            days = data
        }
    }
}