package vn.gmi.workzen.domain.usecase

import vn.gmi.workzen.core.usecases.UseCase
import vn.gmi.workzen.data.models.request.user.OnboardUserReqModel
import vn.gmi.workzen.domain.entity.IdentificationEntity
import vn.gmi.workzen.domain.repository.UserRepository

class UpdateIdentificationUseCase (private val repository: UserRepository) : UseCase<OnboardUserReqModel, IdentificationEntity>() {
    override suspend fun invoke(request: OnboardUserReqModel): IdentificationEntity {
        return repository.updateIdentification(request)
    }
}

class GetIdentificationUseCase(private val repository: UserRepository): UseCase<String, IdentificationEntity?>(){
    override suspend fun invoke(request: String): IdentificationEntity? {
        return repository.getIdentification(request)
    }
}