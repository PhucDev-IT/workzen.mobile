package vn.gmi.workzen.domain.entity.attendance

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class ReportWorkSheetMonthYearEntity : RealmObject{
    @PrimaryKey
    var id:String = ""
    var month:Int?=null
    var year:Int?=null
    var days: RealmList<ReportWorkSheetDayEntity> = realmListOf()
}

class ReportWorkSheetDayEntity : RealmObject{
    @PrimaryKey
    var id:String = ""
    var workDate:String?=null
    var note:String?=null
    var status:String?=null
    var salary: Double?=null
    var attendanceStatus:String?=null
    var data: AttendanceDataEntity?=null
    override fun toString(): String {
        return "ReportWorkSheetDayEntity(id='$id', workDate=$workDate, status=$status)"
    }


}
class AttendanceDataEntity : RealmObject{
    @PrimaryKey
    var id:String = ""
    var checkIn:String?=null
    var checkOut:String?=null
    var note: String? = null
    var shiftId:String?=null
    var shiftName:String?=null
}