package vn.gmi.workzen.data.models.response.notification

import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.domain.entity.notification.Notification
import vn.gmi.workzen.domain.entity.notification.NotifyType
import vn.gmi.workzen.utils.MySharedPreferences
import java.util.UUID


class NotificationResponseModel : DataMapper<Notification>() {
    var id: String? = null
    var status: NotificationStatus = NotificationStatus.UNREAD
    var readAt: String? = null
    var notification: Data?=null

    class Data {
        var id: String? = null
        var title: String? = null
        var content: String? = null //Nội dung thông báo
        var metaData: String? = null //Json/Blob dữ liệu bổ sung cho thông báo

        var priority = 0 //Độ ưu tiên
        var isAction = false
        var iconPath: String? = null
        var type: NotifyType? = null
        var isGlobal = false

    }


    override fun mapToEntity(): Notification {
        return Notification().apply {
            id = this@NotificationResponseModel.id?: UUID.randomUUID().toString()
            title = this@NotificationResponseModel.notification?.title.toString()
            content = this@NotificationResponseModel.notification?.content
            metaData = this@NotificationResponseModel.notification?.metaData
            priority = this@NotificationResponseModel.notification?.priority
            isAction = this@NotificationResponseModel.notification?.isAction
            iconPath = this@NotificationResponseModel.notification?.iconPath
            type = this@NotificationResponseModel.notification?.type?.name?: NotifyType.NOTIFICATION.name
            status = this@NotificationResponseModel.status.name
            isGlobal = this@NotificationResponseModel.notification?.isGlobal
            userId = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_USER_ID)

        }
    }
}