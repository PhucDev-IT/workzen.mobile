package vn.gmi.workzen.data.models.response.attendance

import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.domain.entity.attendance.MonthlyWorkOverviewEntity
import vn.gmi.workzen.utils.DateUtils
import vn.gmi.workzen.utils.MySharedPreferences
import java.time.LocalDate

class MonthlyWorkOverviewRes : DataMapper<MonthlyWorkOverviewEntity>() {
    var month:Int?=null
    var year:Int?=null
    var hourOvertime: Double?=null
    var totalAttendance:Double?=null
    var totalDayOff:Double?=null

    override fun mapToEntity(): MonthlyWorkOverviewEntity {
        val userId = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_USER_ID)
        val now = LocalDate.now().toString()

        return MonthlyWorkOverviewEntity().apply {
            this.userId = userId
            this.month = this@MonthlyWorkOverviewRes.month
            this.year = this@MonthlyWorkOverviewRes.year
            this.hourOvertime = this@MonthlyWorkOverviewRes.hourOvertime
            this.totalAttendance = this@MonthlyWorkOverviewRes.totalAttendance
            this.totalDayOff = this@MonthlyWorkOverviewRes.totalDayOff
            this.updatedAt = now
        }
    }
}