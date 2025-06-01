package vn.gmi.workzen.domain.usecase

import vn.gmi.workzen.core.usecases.UseCase
import vn.gmi.workzen.data.models.request.auth.LoginRequestModel
import vn.gmi.workzen.data.models.response.auth.LoginResponseModel
import vn.gmi.workzen.data.models.response.auth.TokenResponse
import vn.gmi.workzen.domain.repository.AuthRepository


class LoginUseCase(private val repository: AuthRepository): UseCase<LoginRequestModel,LoginResponseModel>() {
    override suspend fun invoke(request: LoginRequestModel): LoginResponseModel {
        return repository.login(request)
    }
}


class LogoutUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke() {
        repository.logout()
    }

}

class RefreshTokenUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(token:String) : TokenResponse{
        return  repository.refreshToken(token)
    }
}

class PhoneExistsUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(phone:String) : Boolean{
        return   repository.checkExistsPhone(phone)
    }
}


class RegisterUseCase(private val repository: AuthRepository){
    suspend operator fun invoke(maps:Map<String,String>) : Boolean{
      return  repository.register(maps)
    }
}