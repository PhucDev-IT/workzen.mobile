package vn.gmi.workzen.domain.entity.notification

class Notification {
    var id:String?=null
    var title:String?=null
    var content:String?=null
    var metaData:String?=null
    var priority:Int?=null
    var isAction: Boolean?=null
    var iconPath:String?=null
    var isGlobal: Boolean?=null
    var type: NotifyType?=null
}