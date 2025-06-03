package vn.gmi.workzen.domain.usecase

import vn.gmi.workzen.core.usecases.BaseUseCase
import vn.gmi.workzen.core.usecases.NoParams

import vn.gmi.workzen.data.models.request.user.OnboardUserReqModel
import vn.gmi.workzen.domain.entity.IdentificationEntity
import vn.gmi.workzen.domain.entity.ProfileEntity
import vn.gmi.workzen.domain.repository.UserRepository

class UpdateIdentificationUseCase (private val repository: UserRepository) : BaseUseCase<OnboardUserReqModel, IdentificationEntity>() {
    override suspend fun invoke(request: OnboardUserReqModel): IdentificationEntity {
        return repository.updateIdentification(request)
    }
}

class GetIdentificationUseCase(private val repository: UserRepository): BaseUseCase<String, IdentificationEntity?>(){
    override suspend fun invoke(params: String): IdentificationEntity? {
        return repository.getIdentification(params)
    }
}

class GetProfileUseCase(private val repository: UserRepository): BaseUseCase<String, ProfileEntity?>(){
    override suspend fun invoke(params: String): ProfileEntity? {
        return repository.getProfile(params)
    }
}

