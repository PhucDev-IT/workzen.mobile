package vn.gmi.workzen.domain.entity.notification

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import vn.gmi.workzen.data.models.response.notification.NotificationStatus

class Notification : RealmObject{
    @PrimaryKey
    var id:String = ""
    var title:String?=null
    var content:String?=null
    var metaData:String?=null
    var priority:Int?=null
    var isAction: Boolean?=null
    var iconPath:String?=null
    var isGlobal: Boolean?=null
    var status:String = NotificationStatus.UNREAD.name
    var type: String = NotifyType.NOTIFICATION.name
    var userId:String?=null
}