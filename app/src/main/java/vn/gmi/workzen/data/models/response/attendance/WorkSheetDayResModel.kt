package vn.gmi.workzen.data.models.response.attendance

import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.domain.entity.attendance.AttendanceDataEntity
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetDayEntity
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetMonthYearEntity
import vn.gmi.workzen.manager.SessionManager

class WorkSheetDayResModel : DataMapper<ReportWorkSheetDayEntity>(){
    var id:String ?=null
    var workDate:String?=null
    var note:String?=null
    var status:String?=null
    var salary: Double?=null
    var attendanceStatus:String?=null
    var data: AttendanceData?=null

    class AttendanceData : DataMapper<AttendanceDataEntity>(){
        var id:String?=null
        var checkIn:String?=null
        var checkOut:String?=null
        var note: String? = null
        var shiftId:String?=null
        var shiftName:String?=null

        override fun mapToEntity(): AttendanceDataEntity {
            return AttendanceDataEntity().apply {
                id = this@AttendanceData.id?:""
                checkIn = this@AttendanceData.checkIn
                checkOut = this@AttendanceData.checkOut
                note = this@AttendanceData.note
                shiftId = this@AttendanceData.shiftId
                shiftName = this@AttendanceData.shiftName
            }
        }
    }

    override fun mapToEntity(): ReportWorkSheetDayEntity {
        return ReportWorkSheetDayEntity().apply {
            id = this@WorkSheetDayResModel.id?:""
            workDate = this@WorkSheetDayResModel.workDate
            note = this@WorkSheetDayResModel.note
            status = this@WorkSheetDayResModel.status
            data = this@WorkSheetDayResModel.data?.mapToEntity()
            attendanceStatus = this@WorkSheetDayResModel.attendanceStatus
            salary = this@WorkSheetDayResModel.salary

        }
    }
}