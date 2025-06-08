package vn.gmi.workzen.data.datasource.local.shift

import vn.gmi.workzen.domain.entity.shift.ShiftEntity

interface ShiftLocalDataSource {
    fun getShift(): ShiftEntity?
    fun saveShift(shift: ShiftEntity)
}