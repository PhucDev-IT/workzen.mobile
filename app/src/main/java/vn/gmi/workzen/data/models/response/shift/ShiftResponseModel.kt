package vn.gmi.workzen.data.models.response.shift

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import vn.gmi.workzen.data.mapper.DataMapper
import vn.gmi.workzen.domain.entity.shift.ShiftEntity
import java.time.LocalTime

class ShiftResponseModel : DataMapper<ShiftEntity>() {

    var id: String = ""
    var name: String? = null
    var startTime: String? = null
    var endTime: String? = null
    var isOvertime: Boolean? = false

    override fun mapToEntity(): ShiftEntity {
        return ShiftEntity().apply {
            id = this@ShiftResponseModel.id
            name = this@ShiftResponseModel.name
            startTime = this@ShiftResponseModel.startTime?.toString()
            endTime = this@ShiftResponseModel.endTime.toString()
            isOvertime = this@ShiftResponseModel.isOvertime
        }
    }
}