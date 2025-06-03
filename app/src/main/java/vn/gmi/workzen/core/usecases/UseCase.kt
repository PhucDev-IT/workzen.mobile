package vn.gmi.workzen.core.usecases

import vn.gmi.workzen.data.models.request.auth.LoginRequestModel
import vn.gmi.workzen.data.models.response.auth.LoginResponseModel

abstract class BaseUseCase<in R, out D> {
   abstract suspend operator fun invoke(params: R): D
}

typealias NoParams = Unit
