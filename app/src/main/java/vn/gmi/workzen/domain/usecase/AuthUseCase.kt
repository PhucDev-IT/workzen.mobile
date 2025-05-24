package vn.gmi.workzen.domain.usecase

import vn.gmi.workzen.data.models.auth.LoginRequestModel
import vn.gmi.workzen.domain.repository.AuthRepository
import vn.gmi.workzen.data.models.auth.AuthResponse

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(request: LoginRequestModel): AuthResponse {
        return repository.login(request)
    }
}


class LogoutUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke() {
        repository.logout()
    }

}

class RefreshTokenUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(token:String) {
        repository.refreshToken(token)
    }
}
