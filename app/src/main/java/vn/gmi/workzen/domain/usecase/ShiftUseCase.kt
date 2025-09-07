package vn.gmi.workzen.domain.usecase


import vn.gmi.workzen.core.usecases.BaseUseCase
import vn.gmi.workzen.data.models.response.shift.ShiftResponseModel
import vn.gmi.workzen.domain.entity.shift.ShiftEntity
import vn.gmi.workzen.domain.repository.ShiftRepository

class GetShiftByUserUseCase(private val shiftRepository: ShiftRepository): BaseUseCase<String, ShiftEntity?>() {
    override suspend fun invoke(params: String): ShiftEntity? {
        return shiftRepository.getShiftByUser(params)
    }
}