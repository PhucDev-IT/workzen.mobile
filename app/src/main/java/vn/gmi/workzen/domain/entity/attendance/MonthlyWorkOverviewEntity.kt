package vn.gmi.workzen.domain.entity.attendance

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.UUID

class MonthlyWorkOverviewEntity : RealmObject{

    @PrimaryKey
    var id = UUID.randomUUID().toString()
    var month:Int?=null
    var year:Int?=null
    var hourOvertime: Double?=null
    var totalAttendance:Double?=null
    var totalDayOff:Double?=null

    var userId:String?=null
    var updatedAt:String?=null
    override fun toString(): String {
        return "MonthlyWorkOverviewEntity(id='$id', month=$month, year=$year, hourOvertime=$hourOvertime, totalAttendance=$totalAttendance, totalDayOff=$totalDayOff, userId=$userId, updatedAt=$updatedAt)"
    }


}