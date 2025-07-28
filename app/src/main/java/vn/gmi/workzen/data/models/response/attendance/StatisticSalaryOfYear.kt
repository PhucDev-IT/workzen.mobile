package vn.gmi.workzen.data.models.response.attendance

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.domain.entity.attendance.StatisticSalaryOfYearEntity
import vn.gmi.workzen.utils.MySharedPreferences

class StatisticSalaryOfYearRes : DataMapper<StatisticSalaryOfYearEntity>(){
    val year:Int?=null
    var totalSalaryOfYear: Double?=null
    var months: List<StatisticSalaryOfYearData>?=null

    class StatisticSalaryOfYearData : DataMapper<StatisticSalaryOfYearEntity.StatisticSalaryOfYearDataEntity>(){
        var month:Int?=null
        var totalSalary: Double?=null
        var totalWorkingDay: Double?=null
        var totalDayAttendance: Double?=null
        var totalDayOffWork: Double?=null
        var totalHourOvertime: Double?=null

        override fun mapToEntity(): StatisticSalaryOfYearEntity.StatisticSalaryOfYearDataEntity {
            return StatisticSalaryOfYearEntity.StatisticSalaryOfYearDataEntity().apply {
                this.month = this@StatisticSalaryOfYearData.month
                this.totalSalary = this@StatisticSalaryOfYearData.totalSalary ?: 0.0
                this.totalWorkingDay = this@StatisticSalaryOfYearData.totalWorkingDay ?: 0.0
                this.totalDayAttendance = this@StatisticSalaryOfYearData.totalDayAttendance ?: 0.0
                this.totalDayOffWork = this@StatisticSalaryOfYearData.totalDayOffWork ?: 0.0
                this.totalHourOvertime = this@StatisticSalaryOfYearData.totalHourOvertime ?: 0.0
            }
        }
    }

    override fun mapToEntity(): StatisticSalaryOfYearEntity {
        val userId = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_USER_ID)
        val realmList = realmListOf<StatisticSalaryOfYearEntity.StatisticSalaryOfYearDataEntity>().apply {
            this@StatisticSalaryOfYearRes.months?.map { it.mapToEntity() }
        }
        return StatisticSalaryOfYearEntity().apply {
            this.year = this@StatisticSalaryOfYearRes.year
            this.totalSalaryOfYear = this@StatisticSalaryOfYearRes.totalSalaryOfYear
            this.months = realmList
            this.userId = userId
        }
    }
}