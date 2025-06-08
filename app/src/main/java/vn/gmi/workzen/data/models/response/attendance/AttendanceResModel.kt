package vn.gmi.workzen.data.models.response.attendance

import vn.gmi.workzen.data.models.response.shift.ShiftResponseModel

class AttendanceResModel {
    var id:String?=null
    var workDate:String?=null
    var checkIn:String?=null
    var checkOut:String?=null
    var note:String?=null
    var shift: ShiftResponseModel?=null
}