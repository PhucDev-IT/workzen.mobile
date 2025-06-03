package vn.gmi.workzen.domain.repository

import vn.gmi.workzen.data.models.response.shift.ShiftResponseModel
import vn.gmi.workzen.domain.entity.ShiftEntity

interface ShiftRepository {
    suspend fun getShiftByUser(userId: String): ShiftEntity?

}