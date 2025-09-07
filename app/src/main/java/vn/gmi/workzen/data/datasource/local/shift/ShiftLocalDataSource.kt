package vn.gmi.workzen.data.datasource.local.shift

import vn.gmi.workzen.domain.entity.shift.ShiftEntity

interface ShiftLocalDataSource {
   suspend fun getShift(): ShiftEntity?
   suspend fun saveShift(shift: ShiftEntity)
}