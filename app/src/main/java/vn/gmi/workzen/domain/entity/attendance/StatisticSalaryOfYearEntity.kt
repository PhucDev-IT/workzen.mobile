package vn.gmi.workzen.domain.entity.attendance

import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

class StatisticSalaryOfYearEntity : RealmObject {
    var year:Int?=null
    var totalSalaryOfYear: Double?=null
    var months: RealmList<StatisticSalaryOfYearDataEntity>?=null
    var userId:String?=null

    class StatisticSalaryOfYearDataEntity : RealmObject{
        var month:Int?=null
        var totalSalary: Double = 0.0

        var totalWorkingDay: Double = 0.0
        var totalDayAttendance: Double= 0.0
        var totalDayOffWork: Double= 0.0
        var totalHourOvertime: Double= 0.0



        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as StatisticSalaryOfYearDataEntity

            if (month != other.month) return false
            if (totalSalary != other.totalSalary) return false
            if (totalWorkingDay != other.totalWorkingDay) return false
            if (totalDayAttendance != other.totalDayAttendance) return false
            if (totalDayOffWork != other.totalDayOffWork) return false
            if (totalHourOvertime != other.totalHourOvertime) return false

            return true
        }

        override fun hashCode(): Int {
            var result = month ?: 0
            result = 31 * result + (totalSalary?.hashCode() ?: 0)
            result = 31 * result + (totalWorkingDay?.hashCode() ?: 0)
            result = 31 * result + (totalDayAttendance?.hashCode() ?: 0)
            result = 31 * result + (totalDayOffWork?.hashCode() ?: 0)
            result = 31 * result + (totalHourOvertime?.hashCode() ?: 0)
            return result
        }

        override fun toString(): String {
            return "StatisticSalaryOfYearDataEntity(month=$month, totalSalary=$totalSalary, totalWorkingDay=$totalWorkingDay, totalDayAttendance=$totalDayAttendance, totalDayOffWork=$totalDayOffWork, totalHourOvertime=$totalHourOvertime)"
        }


    }
}