package vn.gmi.workzen.core.usecases

import vn.gmi.workzen.data.models.request.auth.LoginRequestModel
import vn.gmi.workzen.data.models.response.auth.LoginResponseModel

abstract class UseCase<R,D>{
   abstract suspend operator fun invoke(request: R): D
}